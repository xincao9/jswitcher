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
package com.github.xincao9.jswitcher.service;

import com.github.xincao9.jswitcher.api.exception.FileNotFoundException;
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
    private int port;
    private String databaseName;
    private String databaseUser;
    private String databasePass;
    private String databaseHost;
    private int databasePort;
    private String databaseOpts;

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
    private void init(Properties properties) {
        properties.entrySet().forEach((entry) -> {
            LOGGER.info("configure key = {}, value = {}", entry.getKey(), entry.getValue());
        });
        this.port = Integer.valueOf(properties.getProperty("switcher.port", "12306"));
        this.databaseName = properties.getProperty("switcher.database.name", "switcher");
        this.databaseUser = properties.getProperty("switcher.database.user", "root");
        this.databasePass = properties.getProperty("switcher.database.pass", "");
        this.databaseHost = properties.getProperty("switcher.database.host", "127.0.0.1");
        this.databasePort = Integer.valueOf(properties.getProperty("switcher.database.port", "3306"));
        this.databaseOpts = properties.getProperty("switcher.database.opts", "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true");
    }

    /**
     * 构造器
     * 
     * @param filename 文件名
     */
    public Configure(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(Configure.class.getResourceAsStream(filename));
        } catch (IOException ioe) {
            throw new FileNotFoundException(String.format("%s the file cannot be found in the root of the class", filename), ioe);
        }
        init(properties);
    }

    /**
     * 获取监听端口
     * 
     * @return 监听端口
     */
    public int getPort() {
        return this.port;
    }

    /**
     * 获取数据库
     * 
     * @return 数据库
     */
    public String getDatabaseName() {
        return this.databaseName;
    }

    /**
     * 获取用户
     * 
     * @return 用户
     */
    public String getDatabaseUser() {
        return this.databaseUser;
    }

    /**
     * 获取密码
     * 
     * @return 密码
     */
    public String getDatabasePass() {
        return this.databasePass;
    }

    /**
     * 获取主机
     * 
     * @return 主机
     */
    public String getDatabaseHost() {
        return this.databaseHost;
    }

    /**
     * 获取端口
     * 
     * @return 端口
     */
    public int getDatabasePort() {
        return this.databasePort;
    }

    /**
     * 获取参数
     * 
     * @return 参数
     */
    public String getDatabaseOpts() {
        return this.databaseOpts;
    }

}
