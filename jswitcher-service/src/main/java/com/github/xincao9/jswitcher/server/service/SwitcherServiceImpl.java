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
package com.github.xincao9.jswitcher.server.service;

import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import com.github.xincao9.jswitcher.server.Configure;
import com.github.xincao9.jswitcher.server.dao.SwitcherDAO;
import com.github.xincao9.jswitcher.server.exception.KeyNotFoundException;
import com.github.xincao9.jswitcher.server.exception.ParameterInvalidException;
import com.github.xincao9.jswitcher.server.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xincao9@gmail.com
 */
public class SwitcherServiceImpl implements SwitcherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitcherService.class);

    private final Map<String, Switcher> keyAndSwitcher = new ConcurrentHashMap();
    private final SwitcherDAO switcherDAO;
    private final Configure configre;

    public SwitcherServiceImpl(Configure configre, SwitcherDAO switcherDAO) {
        this.configre = configre;
        this.switcherDAO = switcherDAO;
    }

    /**
     * 注册开关 (开发使用)
     *
     * @param key
     * @param open
     * @param describe
     * @param qos
     */
    @Override
    public void register(String key, Boolean open, String describe, QoS qos) {
        String k = String.valueOf(key);
        if (!keyAndSwitcher.containsKey(k)) {
            Switcher switcher = getSwitcherByKey(k);
            if (switcher == null) {
                if (!keyAndSwitcher.containsKey(k)) {
                    switcher = new Switcher();
                    switcher.setKey(k);
                    switcher.setOpen(open);
                    switcher.setDescribe(describe);
                    switcher.setQos(qos);
                    keyAndSwitcher.put(k, switcher);
                    LOGGER.warn("新注册开关 {}", switcher.toString());
                }
            } else {
                keyAndSwitcher.put(k, switcher);
                LOGGER.warn("加载开关信息 {}", switcher.toString());
            }
        }
    }

    /**
     * 是开的状态 (开发使用)
     *
     * @param key
     * @return
     */
    @Override
    public Boolean isOpen(String key) {
        String k = String.valueOf(key);
        if (keyAndSwitcher.containsKey(k)) {
            return keyAndSwitcher.get(k).getOpen();
        }
        LOGGER.warn("应用中没有注册过这个开关 key = {}", k);
        return false;
    }

    /**
     * 是关闭状态 (开发使用)
     *
     * @param key
     * @return
     */
    @Override
    public Boolean isClose(String key) {
        return !isOpen(key);
    }

    /**
     * 检查开关状态 （运维使用）
     *
     * @param key
     * @return
     */
    @Override
    public Boolean check(String key) {
        String k = String.valueOf(key);
        if (StringUtils.isEmpty(k)) {
            throw new ParameterInvalidException("key 不能为空!");
        }
        if (!keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s 不能找到!", k));
        }
        return keyAndSwitcher.get(k).getOpen();
    }

    /**
     * 开启开关 （运维使用）
     *
     * @param key
     */
    @Override
    public void on(String key) {
        String k = String.valueOf(key);
        if (StringUtils.isEmpty(k)) {
            throw new ParameterInvalidException("key 不能为空!");
        }
        if (!keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s 不能找到!", k));
        }
        keyAndSwitcher.get(k).setOpen(true);
    }

    /**
     * 关闭开关 （运维使用）
     *
     * @param key
     */
    @Override
    public void off(String key) {
        String k = String.valueOf(key);
        if (StringUtils.isEmpty(k)) {
            throw new ParameterInvalidException("key 不能为空!");
        }
        if (!keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s 不能找到!", k));
        }
        keyAndSwitcher.get(k).setOpen(false);
    }

    /**
     * 设置开关状态且永久保存 （运维使用）
     *
     * @param key
     * @param open
     */
    @Override
    public void set(String key, Boolean open) {
        String k = String.valueOf(key);
        if (StringUtils.isEmpty(k)) {
            throw new ParameterInvalidException("key 不能为空!");
        }
        if (!keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s 不能找到!", k));
        }
        keyAndSwitcher.get(k).setOpen(open);
        Switcher switcher = getSwitcherByKey(k);
        if (switcher != null) {
            switcherDAO.changeStatusByKey(k, !open, open);
        } else {
            switcherDAO.insert(keyAndSwitcher.get(k)); // 通过key作为唯一索引来解决重复插入的问题
        }
    }

    /**
     * 查看开关列表（运维使用）
     *
     * @return
     */
    @Override
    public List<Switcher> list() {
        List<Switcher> switcheres = new ArrayList(this.keyAndSwitcher.values());
        return switcheres;
    }

    public Switcher getSwitcherByKey(String key) {
        if (StringUtils.isNotEmpty(key)) {
            return switcherDAO.selectByKey(key);
        }
        return null;
    }

}
