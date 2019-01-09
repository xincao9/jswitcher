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

import com.github.xincao9.jswitcher.service.exception.FileNotFoundException;
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
    private final int port;
    private final String databaseName;
    private final String databaseUser;
    private final String databasePass;
    private final String databaseHost;
    private final int databasePort;
    private final String databaseOpts;

    /**
     *
     * @param filename
     */
    public Configure(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(Configure.class.getResourceAsStream(filename));
        } catch (IOException ioe) {
            throw new FileNotFoundException(String.format("%s the file cannot be found in the root of the class", filename), ioe);
        }
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
     *
     * @return
     */
    public int getPort() {
        return this.port;
    }

    /**
     *
     * @return
     */
    public String getDatabaseName() {
        return this.databaseName;
    }

    /**
     *
     * @return
     */
    public String getDatabaseUser() {
        return this.databaseUser;
    }

    /**
     *
     * @return
     */
    public String getDatabasePass() {
        return this.databasePass;
    }

    /**
     *
     * @return
     */
    public String getDatabaseHost() {
        return this.databaseHost;
    }

    /**
     *
     * @return
     */
    public int getDatabasePort() {
        return this.databasePort;
    }

    /**
     *
     * @return
     */
    public String getDatabaseOpts() {
        return this.databaseOpts;
    }

}
