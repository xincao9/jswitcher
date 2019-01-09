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
package com.github.xincao9.jswitcher.service.service;

import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import com.github.xincao9.jswitcher.service.Configure;
import com.github.xincao9.jswitcher.service.dao.SwitcherDAO;
import com.github.xincao9.jswitcher.service.exception.KeyNotFoundException;
import com.github.xincao9.jswitcher.service.exception.ParameterInvalidException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
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
     * registration switch (development use)
     *
     * @param key
     * @param open
     * @param describe
     * @param qos
     */
    @Override
    public void register(String key, Boolean open, String describe, QoS qos) {
        String k = String.valueOf(key);
        if (!this.keyAndSwitcher.containsKey(k)) {
            Switcher switcher = getSwitcherByKey(k);
            if (switcher == null) {
                if (!this.keyAndSwitcher.containsKey(k)) {
                    switcher = new Switcher();
                    switcher.setKey(k);
                    switcher.setOpen(open);
                    switcher.setDescribe(describe);
                    switcher.setQos(qos);
                    this.keyAndSwitcher.put(k, switcher);
                    LOGGER.warn("new registration switch {}", switcher.toString());
                }
            } else {
                this.keyAndSwitcher.put(k, switcher);
                LOGGER.warn("load switch information {}", switcher.toString());
            }
        }
    }

    /**
     * is open state (development use)
     *
     * @param key
     * @return
     */
    @Override
    public Boolean isOpen(String key) {
        String k = String.valueOf(key);
        if (this.keyAndSwitcher.containsKey(k)) {
            return this.keyAndSwitcher.get(k).getOpen();
        }
        LOGGER.warn("this switch has not been registered in the application. key = {}", k);
        return false;
    }

    /**
     * is off state (development use)
     *
     * @param key
     * @return
     */
    @Override
    public Boolean isClose(String key) {
        return !isOpen(key);
    }

    /**
     * check switch status (operation and maintenance use)
     *
     * @param key
     * @return
     */
    @Override
    public Boolean check(String key) {
        String k = String.valueOf(key);
        if (StringUtils.isBlank(k)) {
            throw new ParameterInvalidException("key can not be empty!");
        }
        if (!this.keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s can't find!", k));
        }
        return this.keyAndSwitcher.get(k).getOpen();
    }

    /**
     * open switch (operation and maintenance use)
     *
     * @param key
     */
    @Override
    public void on(String key) {
        String k = String.valueOf(key);
        if (StringUtils.isBlank(k)) {
            throw new ParameterInvalidException("key can not be empty!");
        }
        if (!this.keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s can't find!", k));
        }
        this.keyAndSwitcher.get(k).setOpen(true);
    }

    /**
     * close switch (operation and maintenance use)
     *
     * @param key
     */
    @Override
    public void off(String key) {
        String k = String.valueOf(key);
        if (StringUtils.isBlank(k)) {
            throw new ParameterInvalidException("key can not be empty!");
        }
        if (!this.keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s can't find!", k));
        }
        this.keyAndSwitcher.get(k).setOpen(false);
    }

    /**
     * set the switch state and save it permanently (Operation and maintenance)
     *
     * @param key
     * @param open
     */
    @Override
    public void set(String key, Boolean open) {
        String k = String.valueOf(key);
        if (StringUtils.isBlank(k)) {
            throw new ParameterInvalidException("key can not be empty!");
        }
        if (!this.keyAndSwitcher.containsKey(k)) {
            throw new KeyNotFoundException(String.format("key = %s can't find!", k));
        }
        this.keyAndSwitcher.get(k).setOpen(open);
        Switcher switcher = getSwitcherByKey(k);
        if (switcher != null) {
            this.switcherDAO.changeStatusByKey(k, !open, open);
        } else {
            this.switcherDAO.insert(this.keyAndSwitcher.get(k)); // resolve repeated insertion problems by using key as a unique index
        }
    }

    /**
     * view switch list (operation and maintenance use)
     *
     * @return
     */
    @Override
    public List<Switcher> list() {
        List<Switcher> switcheres = new ArrayList(this.keyAndSwitcher.values());
        return switcheres;
    }

    /**
     *
     * @param key
     * @return
     */
    public Switcher getSwitcherByKey(String key) {
        if (StringUtils.isNotEmpty(key)) {
            return this.switcherDAO.selectByKey(key);
        }
        return null;
    }

}
