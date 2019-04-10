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
package com.github.xincao9.jswitcher.spring.boot.starter;

import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.QoS;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Uri拦截器
 * 
 * @author xincao9@gmail.com
 */
public class UriInterceptor implements Filter {

    private final SwitcherService switcherService;

    /**
     * 构造器
     * 
     * @param switcherService 
     */
    public UriInterceptor (SwitcherService switcherService) {
        this.switcherService = switcherService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 过滤器逻辑
     * 
     * @param request 请求体
     * @param response 响应体
     * @param chain 过滤器链路
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest) request;
        String path = hsr.getPathInfo();
        String method = hsr.getMethod();
        String key = String.format("%s:%s", method, path);
        switcherService.register(key, Boolean.TRUE, key, QoS.API);
        if (switcherService.isOpen(key)) {
            chain.doFilter(request, response);
        }
    }

}
