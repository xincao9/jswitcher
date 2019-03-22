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
package com.github.xincao9.jswitcher.core;

import com.github.xincao9.jswitcher.core.config.Configure;
import com.github.xincao9.jsonrpc.core.server.JsonRPCServer;
import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.core.dao.SwitcherDAO;
import com.github.xincao9.jswitcher.api.exception.SwitcherServerException;
import com.github.xincao9.jswitcher.core.constant.ConfigConsts;
import com.github.xincao9.jswitcher.core.service.SwitcherServiceImpl;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 开关服务器
 * 
 * @author xincao9@gmail.com
 */
public class SwitcherServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitcherServer.class);
    private JsonRPCServer jsonRPCServer;
    private SwitcherServiceImpl switcherService;

    /**
     * 构造器
     * 
     */
    public SwitcherServer() {
        this(ConfigConsts.DEFAULT_CONFIG_FILE);
    }

    /**
     *  构造器
     * 
     * @param properties 配置属性
     */
    public SwitcherServer(Properties properties) {
        LOGGER.warn("switcher-server turning on！");
        Configure.init(properties);
        init();
    }

    /**
     * 构造器
     * 
     * @param filename 配置文件
     */
    public SwitcherServer(String filename) {
        LOGGER.warn("switcher-server turning on！");
        Configure.init(filename);
        init();
    }

    /**
     * 初始化方法
     * 
     */
    private void init() {
        SwitcherDAO switcherDAO = new SwitcherDAO();
        this.switcherService = new SwitcherServiceImpl(switcherDAO);
        try {
            this.jsonRPCServer = JsonRPCServer.defaultJsonRPCServer(ConfigConsts.DEFAULT_CONFIG_FILE);
            this.jsonRPCServer.register(this.switcherService);
            this.jsonRPCServer.start();
        } catch (Throwable e) {
            throw new SwitcherServerException("JsonRPCServer abnormal", e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                close();
            }
        });
    }

    /**
     * 关闭
     * 
     */
    public void close() {
        if (this.jsonRPCServer != null) {
            try {
                LOGGER.warn("switcher-server closing！");
                this.jsonRPCServer.shutdown();
                this.jsonRPCServer = null;
            } catch (Throwable e) {
                throw new SwitcherServerException("JsonRPCServer abnormal", e);
            }
        }
    }

    /**
     * 开关服务
     * 
     * @return
     */
    public SwitcherService getSwitcherService() {
        return this.switcherService;
    }
}
