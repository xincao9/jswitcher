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
package com.github.xincao9.jswitcher.api.service;

import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import java.util.List;

/**
 *
 * @author xincao9@gmail.com
 */
public interface SwitcherService {

    /**
     *
     * @param key
     * @return
     */
    Boolean check(String key);

    /**
     *
     * @param key
     */
    void on(String key);

    /**
     *
     * @param key
     */
    void off(String key);

    /**
     *
     * @param key
     * @param open
     */
    void set(String key, Boolean open);

    /**
     *
     * @return
     */
    List<Switcher> list();

    /**
     *
     * @param key
     * @param open
     * @param describe
     * @param qos
     */
    void register(String key, Boolean open, String describe, QoS qos);

    /**
     *
     * @param key
     * @return
     */
    Boolean isOpen(String key);

    /**
     *
     * @param key
     * @return
     */
    Boolean isClose(String key);

}
