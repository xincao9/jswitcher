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

import com.alibaba.fastjson.JSONArray;
import com.github.xincao9.jsonrpc.core.DiscoveryService;
import com.github.xincao9.jsonrpc.core.JsonRPCClient;
import com.github.xincao9.jsonrpc.core.constant.ResponseCode;
import com.github.xincao9.jsonrpc.core.protocol.Endpoint;
import com.github.xincao9.jsonrpc.core.protocol.Request;
import com.github.xincao9.jsonrpc.core.protocol.Response;
import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private JsonRPCClient jsonRPCClient;

    /**
     * 端点
     *
     * @return 端点
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

    /**
     * 开关列表
     * 
     * @param host 主机
     * @param port 端口
     * @return 开关列表
     */
    @GetMapping("switcheres/{host}/{port}")
    public ResponseEntity<List<Switcher>> switcheres(@PathVariable String host, @PathVariable Integer port) {
        if (StringUtils.isBlank(host) || port == null || port <= 0 || port > 65535) {
            return ResponseEntity.status(400).build();
        }
        StringBuilder method = new StringBuilder();
        method.append(SwitcherService.class.getTypeName())
                .append('.')
                .append("list");
        Request request = Request.createRequest(Boolean.TRUE, method.toString());
        request.setDirect(Boolean.TRUE);
        try {
            Response response = jsonRPCClient.invoke(request);
            if (Objects.equals(response.getCode(), ResponseCode.OK)) {
                if (response.getData() == null) {
                    return null;
                }
                return ResponseEntity.ok(JSONArray.parseArray(String.valueOf(response.getData()), Switcher.class));
            }
            throw new RuntimeException(response.getMsg());
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage());
        }
        return ResponseEntity.status(500).build();
    }
}
