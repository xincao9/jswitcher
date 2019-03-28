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
package com.github.xincao9.jswitcher.core.constant;

/**
 * 系统配置
 * 
 * @author xincao9@gmail.com
 */
public class ConfigConsts {

    public static final String DEFAULT_CONFIG_FILE = "/jswitcher.properties";

    public static final String JWITCHER_SERVER_PORT = "jswitcher.server.port";
    public static final String DEFAULT_JWITCHER_SERVER_PORT = "12306";
    public static final String JWITCHER_DATABASE_NAME = "jswitcher.database.name";
    public static final String DEFAULT_JWITCHER_DATABASE_NAME = "switcher";
    public static final String JWITCHER_DATABASE_USER = "jswitcher.database.user";
    public static final String DEFAULT_JWITCHER_DATABASE_USER = "root";
    public static final String JWITCHER_DATABASE_PASS = "jswitcher.database.pass";
    public static final String DEFAULT_JWITCHER_DATABASE_PASS = "";
    public static final String JWITCHER_DATABASE_HOST = "jswitcher.database.host";
    public static final String DEFAULT_JWITCHER_DATABASE_HOST = "localhost";
    public static final String JWITCHER_DATABASE_PORT = "jswitcher.database.port";
    public static final String DEFAULT_JWITCHER_DATABASE_PORT = "3306";
    public static final String JWITCHER_DATABASE_OPTS = "jswitcher.database.opts";
    public static final String DEFAULT_JWITCHER_DATABASE_OPTS = "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true";
    public static final String JWITCHER_DISCOVERY_ZOOKEEPER = "jswitcher.discovery.zookeeper";
    public static final String DEFAULT_JWITCHER_DISCOVERY_ZOOKEEPER = "localhost:2181";

}