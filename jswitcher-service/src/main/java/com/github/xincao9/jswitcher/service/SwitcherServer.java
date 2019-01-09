/*
 * Copyright 2019 xingyunzhi.
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
package com.github.xincao9.jswitcher.service;

import com.github.xincao9.jsonrpc.server.JsonRPCServer;
import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.service.dao.SwitcherDAO;
import com.github.xincao9.jswitcher.service.exception.SwitcherServerException;
import com.github.xincao9.jswitcher.service.method.switcher.CheckMethodImpl;
import com.github.xincao9.jswitcher.service.method.switcher.ListMethodImpl;
import com.github.xincao9.jswitcher.service.method.switcher.OffMethodImpl;
import com.github.xincao9.jswitcher.service.method.switcher.OnMethodImpl;
import com.github.xincao9.jswitcher.service.method.switcher.SetMethodImpl;
import com.github.xincao9.jswitcher.service.service.SwitcherServiceImpl;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xincao9@gmail.com
 */
public class SwitcherServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitcherServer.class);
    private static final String DEFAULT_CONFIG_FILE = "/switcher.properties";
    private JsonRPCServer jsonRPCServer;
    private Configure configure;
    private SwitcherServiceImpl switcherService;

    /**
     *
     */
    public SwitcherServer() {
        this(DEFAULT_CONFIG_FILE);
    }

    public SwitcherServer(Properties properties) {
        LOGGER.warn("switcher-server turning on！");
        this.configure = new Configure(properties);
        init();
    }

    /**
     *
     * @param filename
     */
    public SwitcherServer(String filename) {
        LOGGER.warn("switcher-server turning on！");
        this.configure = new Configure(filename);
        init();
    }

    private void init() {
        SwitcherDAO switcherDAO = new SwitcherDAO(this.configure);
        this.switcherService = new SwitcherServiceImpl(this.configure, switcherDAO);
        CheckMethodImpl checkMethodImpl = new CheckMethodImpl(this.switcherService);
        OnMethodImpl onMethodImpl = new OnMethodImpl(this.switcherService);
        OffMethodImpl offMethodImpl = new OffMethodImpl(this.switcherService);
        SetMethodImpl setMethodImpl = new SetMethodImpl(this.switcherService);
        ListMethodImpl listMethodImpl = new ListMethodImpl(this.switcherService);
        try {
            this.jsonRPCServer = JsonRPCServer.defaultJsonRPCServer(this.configure.getPort(), 1, 1);
            this.jsonRPCServer.register(checkMethodImpl);
            this.jsonRPCServer.register(onMethodImpl);
            this.jsonRPCServer.register(offMethodImpl);
            this.jsonRPCServer.register(setMethodImpl);
            this.jsonRPCServer.register(listMethodImpl);
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
     *
     * @return
     */
    public Configure getConfigure() {
        return this.configure;
    }

    /**
     *
     * @return
     */
    public SwitcherService getSwitcherService() {
        return this.switcherService;
    }
}
