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

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 导入注解属性且初始化自动配置类
 * 
 * @author xincao9@gmail.com
 */
public class JswitcherImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 注册组件
     * 
     * @param am 注解元信息
     * @param bdr 组件注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata am, BeanDefinitionRegistry bdr) {
        BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(JswitcherAutoConfiguration.class);
        bdr.registerBeanDefinition(JswitcherAutoConfiguration.class.getName(), bdb.getBeanDefinition());
    }

}
