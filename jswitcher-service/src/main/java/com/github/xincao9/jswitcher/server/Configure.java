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
package com.github.xincao9.jswitcher.server;

import com.github.xincao9.jswitcher.server.exception.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xincao9@gmail.com
 */
public class Configure {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configure.class);
    private final String listenIp;
    private final int listenPort;
    private final String databaseName;
    private final String databaseUser;
    private final String databasePass;
    private final String databaseHost;
    private final int databasePort;
    private final String databaseOpts;

    public Configure(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(Configure.class.getResourceAsStream(filename));
        } catch (IOException ioe) {
            throw new FileNotFoundException(String.format("%s 文件在类根目录下不能找到", filename), ioe);
        }
        properties.entrySet().forEach((entry) -> {
            LOGGER.info("configure key = {}, value = {}", entry.getKey(), entry.getValue());
        });
        this.listenIp = properties.getProperty("switcher.listen.ip", "127.0.0.1");
        this.listenPort = Integer.valueOf(properties.getProperty("switcher.listen.port", "12306"));
        this.databaseName = properties.getProperty("switcher.database.name", "switcher");
        this.databaseUser = properties.getProperty("switcher.database.user", "root");
        this.databasePass = properties.getProperty("switcher.database.pass", "");
        this.databaseHost = properties.getProperty("switcher.database.host", "127.0.0.1");
        this.databasePort = Integer.valueOf(properties.getProperty("switcher.database.port", "3306"));
        this.databaseOpts = properties.getProperty("switcher.database.opts", "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true");
    }

    public String getListenIp() {
        return listenIp;
    }

    public int getListenPort() {
        return listenPort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePass() {
        return databasePass;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseOpts() {
        return databaseOpts;
    }

}
