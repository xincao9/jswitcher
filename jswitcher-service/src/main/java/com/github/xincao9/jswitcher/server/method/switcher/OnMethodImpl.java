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
package com.github.xincao9.jswitcher.server.method.switcher;

import com.github.xincao9.jsonrpc.Request;
import com.github.xincao9.jsonrpc.server.SyncMethod;
import com.github.xincao9.jswitcher.api.service.SwitcherService;
import java.util.List;

/**
 *
 * @author xincao9@gmail.com
 */
public class OnMethodImpl implements SyncMethod<Void> {

    private final SwitcherService switcherService;

    /**
     * 
     * @param switcherService 
     */
    public OnMethodImpl(SwitcherService switcherService) {
        this.switcherService = switcherService;
    }

    /**
     * 
     * @param request
     * @return 
     */
    @Override
    public Void exec(Request request) {
        List<Object> params = request.getParams();
        switcherService.on((String) params.get(0));
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getName() {
        return "on";
    }

}
