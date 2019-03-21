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
package com.github.xincao9.jswitcher.api.service;

import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import java.util.List;

/**
 * 开关服务
 *
 * @author xincao9@gmail.com
 */
public interface SwitcherService {

    /**
     * 检查开关状态（操作和维护使用）
     *
     * @param key 键值
     * @return 开关状态
     */
    Boolean check(String key);

    /**
     * 开关（操作和维护使用）
     *
     * @param key 键值
     */
    void on(String key);

    /**
     * 关闭开关（操作和维护使用）
     *
     * @param key 键值
     */
    void off(String key);

    /**
     * 设置开关状态并永久保存（操作和维护）
     *
     * @param key 键值
     * @param open 开关状态
     */
    void set(String key, Boolean open);

    /**
     * 查看开关列表（操作和维护使用）
     *
     * @return 开关列表
     */
    List<Switcher> list();

    /**
     * 注册开关（开发使用）
     *
     * @param key 键值
     * @param open 开关状态
     * @param describe 描述
     * @param qos 服务质量
     */
    void register(String key, Boolean open, String describe, QoS qos);

    /**
     * 是开放状态（开发使用）
     *
     * @param key 键值
     * @return 是/否
     */
    Boolean isOpen(String key);

    /**
     * 关闭状态（开发使用）
     *
     * @param key 键值
     * @return 是/否
     */
    Boolean isClose(String key);

}
