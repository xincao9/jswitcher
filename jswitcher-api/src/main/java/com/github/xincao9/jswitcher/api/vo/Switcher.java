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
package com.github.xincao9.jswitcher.api.vo;

/**
 *
 * @author xincao9@gmail.com
 */
public class Switcher {

    private String key;
    private String describe;
    private Boolean open;
    private QoS qos;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public QoS getQos() {
        return qos;
    }

    public void setQos(QoS qos) {
        this.qos = qos;
    }

    @Override
    public String toString() {
        return "Switcher{" + "key=" + key + ", describe=" + describe + ", open=" + open + ", qos=" + qos + '}';
    }

}
