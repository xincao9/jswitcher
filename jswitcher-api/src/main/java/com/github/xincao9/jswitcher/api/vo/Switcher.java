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
package com.github.xincao9.jswitcher.api.vo;

/**
 * 开关
 *
 * @author xincao9@gmail.com
 */
public class Switcher {

    private String application; // 应用名
    private String key; // 键值
    private String describe; // 描述
    private Boolean open; // 开关状态
    private QoS qos; // 服务质量

    /**
     * 获取应用名
     * 
     * @return 
     */
    public String getApplication() {
        return application;
    }

    /**
     * 设置应用名
     * 
     * @param application 应用名
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * 获取键值
     *
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置键值
     *
     * @param key 键值
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取描述
     *
     * @return
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * 设置描述
     *
     * @param describe 描述
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * 获取开关状态
     *
     * @return
     */
    public Boolean getOpen() {
        return open;
    }

    /**
     * 开关状态
     *
     * @param open 开关状态
     */
    public void setOpen(Boolean open) {
        this.open = open;
    }

    /**
     * 获取服务质量
     *
     * @return
     */
    public QoS getQos() {
        return qos;
    }

    /**
     * 设置服务质量
     *
     * @param qos 服务质量
     */
    public void setQos(QoS qos) {
        this.qos = qos;
    }

    /**
     * 序列化
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        return "Switcher{" + "key=" + key + ", describe=" + describe + ", open=" + open + ", qos=" + qos + '}';
    }

}
