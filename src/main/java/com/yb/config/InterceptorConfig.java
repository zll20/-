package com.yb.config;

import com.yb.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorizationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/upload/**", "/code/upload/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");

        registry.addResourceHandler("/upload/**")
                .addResourceLocations("classpath:/static/upload/", 
                                      "file:./src/main/resources/static/upload/",
                                      "file:D:/JAVA/bishe/code/src/main/resources/static/upload/");

        registry.addResourceHandler("/code/upload/**")
                .addResourceLocations("classpath:/static/upload/", 
                                      "file:./src/main/resources/static/upload/",
                                      "file:D:/JAVA/bishe/code/src/main/resources/static/upload/");

        registry.addResourceHandler("/tingli/**")
                .addResourceLocations("classpath:/static/tingli/", "file:./src/main/resources/static/tingli/");

        registry.addResourceHandler("/admin/**")
                .addResourceLocations("classpath:/admin/");

        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");

        registry.addResourceHandler("/front-pc/**")
                .addResourceLocations("classpath:/front-pc/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/admin/dist/index.html");
        registry.addViewController("/code/").setViewName("forward:/admin/dist/index.html");
        registry.addViewController("/admin/").setViewName("forward:/admin/dist/index.html");
        super.addViewControllers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }

}
