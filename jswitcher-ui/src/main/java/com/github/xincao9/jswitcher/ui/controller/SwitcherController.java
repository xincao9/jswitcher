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
package com.github.xincao9.jswitcher.ui.controller;

import com.github.xincao9.jsonrpc.core.DiscoveryService;
import com.github.xincao9.jsonrpc.core.protocol.Endpoint;
import com.github.xincao9.jswitcher.api.service.SwitcherService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xincao9@gmail.com
 */
@RestController
@RequestMapping("/switcher")
public class SwitcherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitcherController.class);

    @Autowired
    private DiscoveryService discoveryService;

    /**
     * 端点
     * 
     * @return 
     */
    @GetMapping("endpoints")
    public ResponseEntity<List<Endpoint>> endpoints() {
        try {
            List<Endpoint> endpoints = discoveryService.query(SwitcherService.class.getTypeName());
            if (endpoints == null || endpoints.isEmpty()) {
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.ok(endpoints);
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
        return ResponseEntity.status(500).build();
    }
}
