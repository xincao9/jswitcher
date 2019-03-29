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
 * 参数异常
 *
 * @author xincao9@gmail.com
 */
public class ParameterInvalidException extends Error {

    private static final long serialVersionUID = 4131666077704060000L;

    /**
     * 参数异常
     *
     * @param message 消息
     */
    public ParameterInvalidException(String message) {
        super(message);
    }

    /**
     * 参数异常
     *
     * @param message 消息
     * @param cause 异常
     */
    public ParameterInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 参数异常
     *
     * @param cause 异常
     */
    public ParameterInvalidException(Throwable cause) {
        super(cause);
    }

}
