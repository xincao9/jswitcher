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
package com.github.xincao9.jswitcher.api.exception;

/**
 * 开关服务器异常
 *
 * @author xincao9@gmail.com
 */
public class SwitcherServerException extends Error {

    /**
     * 构造器
     *
     * @param message 消息
     */
    public SwitcherServerException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param message 消息
     * @param cause 异常
     */
    public SwitcherServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param cause 异常
     */
    public SwitcherServerException(Throwable cause) {
        super(cause);
    }

}
