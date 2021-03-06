/*
 * Copyright 2019 xincao9@gmail.com.
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
package com.github.xincao9.jswitcher.ui.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.xincao9.jsonrpc.core.DiscoveryService;
import com.github.xincao9.jsonrpc.core.JsonRPCClient;
import com.github.xincao9.jsonrpc.core.constant.ResponseCode;
import com.github.xincao9.jsonrpc.core.protocol.Endpoint;
import com.github.xincao9.jsonrpc.core.protocol.Pair;
import com.github.xincao9.jsonrpc.core.protocol.Request;
import com.github.xincao9.jsonrpc.core.protocol.Response;
import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import com.github.xincao9.jswitcher.ui.core.CustomCacheManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xincao9@gmail.com
 */
@RestController
@RequestMapping("/switcher")
public class SwitcherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitcherController.class);

    @Autowired
    private DiscoveryService discoveryService;
    @Autowired
    private JsonRPCClient jsonRPCClient;
    private static final String ON = "on";
    private static final String OFF = "off";
    private static final String SET = "set";
    private static final String SWITCHER_LIST = "switcher:list";

    @Autowired
    private CustomCacheManager customCacheManager;

    /**
     * 获取开关列表
     *
     * @return 开关列表
     */
    private List<Map<String, Object>> getSwitcheres() {
        try {
            Cache<String, List<Map<String, Object>>> cache = customCacheManager.getDefaultCache();
            List<Map<String, Object>> resp;
            resp = cache.getIfPresent(SWITCHER_LIST);
            if (resp != null) {
                return resp;
            }
            resp = new ArrayList();
            List<Endpoint> endpoints = discoveryService.query(SwitcherService.class.getTypeName());
            if (endpoints == null || endpoints.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
            AtomicInteger no = new AtomicInteger(0);
            for (Endpoint endpoint : endpoints) {
                List<Switcher> switcheres = getKeysByHostAndPort(endpoint.getHost(), endpoint.getPort());
                if (switcheres == null || switcheres.isEmpty()) {
                    continue;
                }
                for (Switcher switcher : switcheres) {
                    JSONObject v0 = JSONObject.parseObject(JSONObject.toJSONString(endpoint));
                    JSONObject v1 = JSONObject.parseObject(JSONObject.toJSONString(switcher));
                    v0.putAll(v1);
                    v0.put("no", no.addAndGet(1));
                    resp.add(v0);
                }
            }
            Collections.sort(resp, (Map<String, Object> o1, Map<String, Object> o2) -> {
                String application1 = String.valueOf(o1.get("application"));
                String application2 = String.valueOf(o2.get("application"));
                if (!application1.equalsIgnoreCase(application2)) {
                    return application1.compareTo(application2);
                }
                String key1 = String.valueOf(o1.get("key"));
                String key2 = String.valueOf(o2.get("key"));
                if (!key1.equalsIgnoreCase(key2)) {
                    return key1.compareTo(key2);
                }
                String host1 = String.valueOf(o1.get("host"));
                String host2 = String.valueOf(o2.get("host"));
                if (!host1.equalsIgnoreCase(host2)) {
                    return host1.compareTo(host2);
                }
                String port1 = String.valueOf(o1.get("port"));
                String port2 = String.valueOf(o2.get("port"));
                if (!port1.equalsIgnoreCase(port2)) {
                    return port1.compareTo(port2);
                }
                return 0;
            });
            if (!resp.isEmpty()) {
                cache.put(SWITCHER_LIST, resp);
            }
            return resp;
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 获得应用列表
     *
     * @return 应用列表
     */
    @GetMapping("applications")
    public ResponseEntity<Set<String>> applications() {
        try {
            List<Map<String, Object>> keys = getSwitcheres();
            if (keys == null || keys.isEmpty()) {
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.ok(keys.stream().map((key) -> String.valueOf(key.get("application"))).collect(Collectors.toSet()));
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * 获得节点列表
     *
     * @param application 应用名
     * @return 节点列表
     */
    @GetMapping("application/{application}/endpoints")
    public ResponseEntity<List<Pair<String, String>>> applicationEndpoints(@PathVariable String application) {
        try {
            List<Map<String, Object>> keys = getSwitcheres();
            if (keys == null || keys.isEmpty()) {
                return ResponseEntity.status(400).build();
            }
            List<Pair<String, String>> pairs = keys.stream()
                    .filter((key) -> StringUtils.equals(String.valueOf(key.get("application")), application))
                    .collect(Collectors.groupingBy((key) -> String.format("%s:%s", String.valueOf(key.get("host")), String.valueOf(key.get("port")))))
                    .keySet().stream().map((str) -> {
                        String[] ss = str.split(":");
                        return new Pair<>(ss[0], ss[1]);
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(pairs);
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * 开关列表
     *
     * @param host 主机
     * @param port 端口
     * @return 开关列表
     */
    @GetMapping("endpoint/keys/{host}/{port}")
    public ResponseEntity<List<Map<String, Object>>> endpointKeys(@PathVariable String host, @PathVariable Integer port) {
        if (StringUtils.isBlank(host) || port == null || port <= 0 || port > 65535) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Map<String, Object>> keys = getSwitcheres();
        if (keys == null || keys.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        keys = keys.stream()
                .filter((key) -> (StringUtils.equals(host, String.valueOf(key.get("host"))) && port.intValue() == (Integer) key.get("port")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(keys);
    }

    /**
     * 开关列表
     *
     * @param host 主机
     * @param port 端口
     * @return 开关列表
     */
    private List<Switcher> getKeysByHostAndPort(String host, Integer port) {
        StringBuilder method = new StringBuilder();
        method.append(SwitcherService.class.getTypeName())
                .append('.')
                .append("list");
        Request request = Request.createRequest(Boolean.TRUE, method.toString());
        request.setDirect(Boolean.TRUE);
        request.setHost(host);
        request.setPort(port);
        try {
            Response response = jsonRPCClient.invoke(request);
            if (Objects.equals(response.getCode(), ResponseCode.OK)) {
                if (response.getData() == null) {
                    return null;
                }
                return JSONArray.parseArray(String.valueOf(response.getData()), Switcher.class);
            }
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }

    /**
     * 开启开关
     *
     * @param host 主机
     * @param port 端口
     * @param key 开关
     * @return
     */
    @GetMapping("key/on/{host}/{port}/{key}")
    public ResponseEntity keyOn(@PathVariable String host, @PathVariable Integer port, @PathVariable String key) {
        return cmd(ON, host, port, key, null);
    }

    /**
     * 关闭开关
     *
     * @param host 主机
     * @param port 端口
     * @param key 开关
     * @return
     */
    @GetMapping("key/off/{host}/{port}/{key}")
    public ResponseEntity keyOff(@PathVariable String host, @PathVariable Integer port, @PathVariable String key) {
        return cmd(OFF, host, port, key, null);
    }

    /**
     * 开关状态固化
     *
     * @param host 主机
     * @param port 端口
     * @param key 开关
     * @param open 状态
     * @return
     */
    @GetMapping("key/set/{host}/{port}/{key}/{open}")
    public ResponseEntity keySet(@PathVariable String host, @PathVariable Integer port, @PathVariable String key, @PathVariable Boolean open) {
        return cmd(SET, host, port, key, open);
    }

    /**
     * 执行命令(on, off, set)
     *
     * @param cmd 命令
     * @param host 主机
     * @param port 端口
     * @param key 开关
     * @param open 状态
     * @return
     */
    private ResponseEntity cmd(String cmd, String host, Integer port, String key, Boolean open) {
        if (StringUtils.isBlank(host) || port == null || port <= 0 || port > 65535 || StringUtils.isBlank(key)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        key = new String(Base64.getDecoder().decode(key));
        StringBuilder method = new StringBuilder();
        method.append(SwitcherService.class.getTypeName())
                .append('.')
                .append(cmd);
        Request request;
        if (SET.equalsIgnoreCase(cmd)) {
            if (open == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            request = Request.createRequest(Boolean.FALSE, method.toString(), key, open);
            request.setParamTypes(new String[]{String.class.getTypeName(), Boolean.class.getTypeName()});
        } else {
            request = Request.createRequest(Boolean.FALSE, method.toString(), key);
            request.setParamTypes(new String[]{String.class.getTypeName()});
        }
        request.setDirect(Boolean.TRUE);
        request.setHost(host);
        request.setPort(port);
        try {
            Response response = jsonRPCClient.invoke(request);
            if (Objects.equals(response.getCode(), ResponseCode.OK)) {
                return ResponseEntity.ok().build();
            }
            throw new RuntimeException(response.getMsg());
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
