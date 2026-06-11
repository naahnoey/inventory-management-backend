package com.example.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    // view Resolver 경로 설정
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp", ".jsp");
    }

    // 루트 "/" 접속 시 index 페이지로 포워딩
    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    // Web Resource 경로 설정
    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/_resource_/**").addResourceLocations("classpath:/static/_resource_/");
        registry.addResourceHandler("/FrameBase/**").addResourceLocations("classpath:/static/FrameBase/");
        registry.addResourceHandler("/nexacrolib/**").addResourceLocations("classpath:/static/nexacrolib/");
        registry.addResourceHandler("/*.json").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.html").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.js").addResourceLocations("classpath:/static/");
    }

}