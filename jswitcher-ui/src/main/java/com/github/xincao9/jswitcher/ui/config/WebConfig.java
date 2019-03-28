package com.github.xincao9.jswitcher.ui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author xincao9@gmail.com
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置视图
     * 
     * @param registry 
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login"); // 首页
        registry.addViewController("/desktop").setViewName("desktop"); // 我的桌面
    }
}