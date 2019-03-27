/*
 * Copyright 2018 xincao9@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xincao9.jswitcher.core.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.xincao9.jsonrpc.core.DiscoveryService;
import com.github.xincao9.jsonrpc.core.protocol.Endpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务注册与发现组件
 *
 * @author xincao9@gmail.com
 */
public class ZKDiscoveryServiceImpl implements DiscoveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZKDiscoveryServiceImpl.class);
    private final CuratorFramework client;
    private static final String JSONRPC_ROOT = "/jsonrpc";
    private static final String JSONRPC_SERVICE_PATTERN = "/jsonrpc/%s";
    private static final String JSONRPC_INSTANCE_PATTERN = "/jsonrpc/%s/%s";
    private final Map<String, List<Endpoint>> services = new ConcurrentHashMap();
    private final ExecutorService watcherExecutorService = Executors.newFixedThreadPool(1);

    /**
     * 构造器
     *
     * @param zookeeper zookeeper地址，(格式host1:port1,host2:port2,...)
     */
    public ZKDiscoveryServiceImpl(String zookeeper) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.newClient(zookeeper, retryPolicy);
        this.client.start();
    }

    /**
     * 监控服务
     * 
     * @param service 服务名字（接口名）
     */
    private void watcher(String service) {
        try {
            TreeCache treeCache = TreeCache.newBuilder(client, String.format(JSONRPC_SERVICE_PATTERN, service)).setExecutor(watcherExecutorService).build();
            treeCache.getListenable().addListener((TreeCacheListener) (CuratorFramework cf, TreeCacheEvent tce) -> {
                TreeCacheEvent.Type type = tce.getType();
                if (type == TreeCacheEvent.Type.NODE_ADDED || type == TreeCacheEvent.Type.NODE_REMOVED || type == TreeCacheEvent.Type.NODE_UPDATED) {
                    LOGGER.warn("service = {}, event = {} refresh", service, type);
                    try {
                        services.put(service, queryzookeeper(service));
                    } catch (Throwable e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            });
            treeCache.start();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 注册
     *
     * @param endpoint 服务信息
     */
    @Override
    public void register(Endpoint endpoint) {
        try {
            if (client.checkExists().forPath(JSONRPC_ROOT) == null) {
                client.create().withMode(CreateMode.PERSISTENT).forPath(JSONRPC_ROOT);
            }
            if (client.checkExists().forPath(String.format(JSONRPC_SERVICE_PATTERN, endpoint.getName())) == null) {
                client.create().withMode(CreateMode.PERSISTENT).forPath(String.format(JSONRPC_SERVICE_PATTERN, endpoint.getName()));
            }
            client.create()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(String.format(JSONRPC_INSTANCE_PATTERN, endpoint.getName(), endpoint.getInstanceId()), JSONObject.toJSONBytes(endpoint, SerializerFeature.DisableCircularReferenceDetect));
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 获取注册表
     *
     * @param service 服务名字（接口名）
     * @return 节点列表
     */
    @Override
    public List<Endpoint> query(String service) {
        if (!services.containsKey(service)) {
            try {
                services.put(service, queryzookeeper(service));
                watcher(service);
            } catch (Throwable e) {
                LOGGER.error(e.getMessage());
            }
        }
        return services.get(service);
    }

    /**
     * 获取注册表
     * 
     * @param service 服务名字（接口名）
     * @return 节点列表
     * @throws Throwable 异常
     */
    private List<Endpoint> queryzookeeper(String service) throws Throwable {
        List<String> paths = client.getChildren().forPath(String.format(JSONRPC_SERVICE_PATTERN, service));
        if (paths == null || paths.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<Endpoint> endpoints = new ArrayList();
        for (String path : paths) {
            byte[] data = client.getData().forPath(String.format(JSONRPC_INSTANCE_PATTERN, service, path));
            if (data != null && data.length > 0) {
                endpoints.add(JSONObject.parseObject(data, Endpoint.class));
            }
        }
        return endpoints;
    }

    /**
     * 续约
     *
     * @param instanceId 当前实例的id
     */
    @Override
    public void renew(String instanceId) {
    }

    /**
     * 取消
     *
     * @param instanceId 当前实例的id
     */
    @Override
    public void cancel(String instanceId) {
    }

}
