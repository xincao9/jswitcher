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
package com.github.xincao9.jswitcher.spring.boot.starter;

import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.core.SwitcherServer;
import com.github.xincao9.jswitcher.core.constant.ConfigConsts;
import java.util.Properties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 开关自动配置
 * 
 * @author xincao9@gmail.com
 */
public class JswitcherAutoConfiguration implements EnvironmentAware, DisposableBean {

    private Environment environment;
    private SwitcherServer switcherServer;
    private SwitcherService switcherService;

    /**
     * 创建开关服务器
     * 
     * @return 开关服务器
     */
    @Bean
    public SwitcherServer switcherServer () {
        if (switcherServer != null) {
            return switcherServer;
        }
        Properties properties = new Properties();
        properties.put(ConfigConsts.JWITCHER_APPLICATION_NAME, environment.getProperty(ConfigConsts.JWITCHER_APPLICATION_NAME, ConfigConsts.DEFAULT_JWITCHER_APPLICATION_NAME));
        properties.put(ConfigConsts.JWITCHER_SERVER_PORT, environment.getProperty(ConfigConsts.JWITCHER_SERVER_PORT, ConfigConsts.DEFAULT_JWITCHER_SERVER_PORT));
        properties.put(ConfigConsts.JWITCHER_DISCOVERY_ZOOKEEPER, environment.getProperty(ConfigConsts.JWITCHER_DISCOVERY_ZOOKEEPER, ConfigConsts.DEFAULT_JWITCHER_DISCOVERY_ZOOKEEPER));
        properties.put(ConfigConsts.JWITCHER_DATABASE_NAME, environment.getProperty(ConfigConsts.JWITCHER_DATABASE_NAME, ConfigConsts.DEFAULT_JWITCHER_DATABASE_NAME));
        properties.put(ConfigConsts.JWITCHER_DATABASE_USER, environment.getProperty(ConfigConsts.JWITCHER_DATABASE_USER, ConfigConsts.DEFAULT_JWITCHER_DATABASE_USER));
        properties.put(ConfigConsts.JWITCHER_DATABASE_PASS, environment.getProperty(ConfigConsts.JWITCHER_DATABASE_PASS, ConfigConsts.DEFAULT_JWITCHER_DATABASE_PASS));
        properties.put(ConfigConsts.JWITCHER_DATABASE_HOST, environment.getProperty(ConfigConsts.JWITCHER_DATABASE_HOST, ConfigConsts.DEFAULT_JWITCHER_DATABASE_HOST));
        properties.put(ConfigConsts.JWITCHER_DATABASE_PORT, environment.getProperty(ConfigConsts.JWITCHER_DATABASE_PORT, ConfigConsts.DEFAULT_JWITCHER_DATABASE_PORT));
        properties.put(ConfigConsts.JWITCHER_DATABASE_OPTS, environment.getProperty(ConfigConsts.JWITCHER_DATABASE_OPTS, ConfigConsts.DEFAULT_JWITCHER_DATABASE_OPTS));
        switcherServer = new SwitcherServer(properties);
        return switcherServer;
    }

    /**
     * 创建开关服务
     * 
     * @return 开关服务
     */
    @Bean
    public SwitcherService switcherService () {
        if (switcherService != null) {
            return switcherService;
        }
        switcherService = switcherServer().getSwitcherService();
        return switcherService;
    }

    /**
     * 环境
     * 
     * @param environment 环境
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 释放
     * 
     * @throws Exception 异常
     */
    @Override
    public void destroy() throws Exception {
        if (switcherServer != null) {
            switcherServer.close();
        }
    }

}
