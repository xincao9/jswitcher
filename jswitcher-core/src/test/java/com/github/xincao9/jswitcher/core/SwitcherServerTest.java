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
package com.github.xincao9.jswitcher.core;

import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.QoS;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 开关服务器测试用例
 *
 * @author xincao9@gmail.com
 */
public class SwitcherServerTest {

    public SwitcherServerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSwitcherService method, of class SwitcherServer.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testGetSwitcherService() throws IOException {
        SwitcherServer switcherServer = new SwitcherServer();
        SwitcherService switcherService = switcherServer.getSwitcherService();
        switcherService.register("key", Boolean.TRUE, "用于测试", QoS.API);
        if (switcherService.isOpen("key")) {
            System.out.println("key open status");
        } else {
            System.out.println("key closed status");
        }
        switcherService.set("key", Boolean.FALSE);
        switcherServer.close();
    }

}
