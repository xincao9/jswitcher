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
package com.github.xincao9.jswitcher.sample;

import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.spring.boot.starter.EnableJswitcher;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 服务
 *
 * @author xincao9@gmail.com
 */
@SpringBootApplication
@EnableJswitcher
public class ServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceApplication.class);
    @Autowired
    private SwitcherService switcherService;
    private static final String KEY = ServiceApplication.class.getCanonicalName();

    /**
     * 入口方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);

    }

    /**
     * 启动时钩子
     *
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner() {
        return (String... args) -> {
            switcherService.register(KEY, Boolean.TRUE, "Recording ServiceApplication Logger", QoS.API);
            for (int no = 0; no < 100; no++) {
                if (switcherService.isOpen(KEY)) {
                    LOGGER.info(RandomStringUtils.randomAscii(128));
                }
                TimeUnit.SECONDS.sleep(1);
            }
        };
    }

}
