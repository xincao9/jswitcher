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
package com.github.xincao9.jswitcher.core.config;

import com.github.xincao9.jswitcher.api.exception.FileNotFoundException;
import com.github.xincao9.jswitcher.core.constant.ConfigConsts;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置类
 * 
 * @author xincao9@gmail.com
 */
public class Configure {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configure.class);
    public static String databaseName;
    public static String databaseUser;
    public static String databasePass;
    public static String databaseHost;
    public static Integer databasePort;
    public static String databaseOpts;
    public static String zookeeper;

    /**
     * 构造器
     * 
     * @param properties 属性
     */
    public Configure(Properties properties) {
        init(properties);
    }

    /**
     * 初始化
     * 
     * @param properties 属性
     */
    public static void init(Properties properties) {
        properties.entrySet().forEach((entry) -> {
            LOGGER.info("configure key = {}, value = {}", entry.getKey(), entry.getValue());
        });
        Configure.databaseName = properties.getProperty(ConfigConsts.JWITCHER_DATABASE_NAME, ConfigConsts.DEFAULT_JWITCHER_DATABASE_NAME);
        Configure.databaseUser = properties.getProperty(ConfigConsts.JWITCHER_DATABASE_USER, ConfigConsts.DEFAULT_JWITCHER_DATABASE_USER);
        Configure.databasePass = properties.getProperty(ConfigConsts.JWITCHER_DATABASE_PASS, ConfigConsts.DEFAULT_JWITCHER_DATABASE_PASS);
        Configure.databaseHost = properties.getProperty(ConfigConsts.JWITCHER_DATABASE_HOST, ConfigConsts.DEFAULT_JWITCHER_DATABASE_HOST);
        Configure.databasePort = Integer.valueOf(properties.getProperty(ConfigConsts.JWITCHER_DATABASE_PORT, ConfigConsts.DEFAULT_JWITCHER_DATABASE_PORT));
        Configure.databaseOpts = properties.getProperty(ConfigConsts.JWITCHER_DATABASE_OPTS, ConfigConsts.DEFAULT_JWITCHER_DATABASE_OPTS);
        Configure.zookeeper = properties.getProperty(ConfigConsts.JWITCHER_DISCOVERY_ZOOKEEPER, ConfigConsts.DEFAULT_JWITCHER_DISCOVERY_ZOOKEEPER);
    }

    /**
     * 初始化
     * 
     * @param filename 文件名
     */
    public static void init (String filename) {
        Properties properties = new Properties();
        try {
            properties.load(Configure.class.getResourceAsStream(filename));
        } catch (IOException ioe) {
            throw new FileNotFoundException(String.format("%s the file cannot be found in the root of the class", filename), ioe);
        }
        init(properties);
    }


}
