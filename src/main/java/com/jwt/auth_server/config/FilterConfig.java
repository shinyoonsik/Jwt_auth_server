package com.jwt.auth_server.config;

import com.jwt.auth_server.filter.MyServletFilter1;
import com.jwt.auth_server.filter.MyServletFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyServletFilter1> DoMyServletFilter(){
        FilterRegistrationBean<MyServletFilter1> servletFilter = new FilterRegistrationBean<>(new MyServletFilter1());
        servletFilter.addUrlPatterns("/*");
        servletFilter.setOrder(0); // 낮은 번호가 우선순위가 높다

        return servletFilter;
    }

    @Bean
    public FilterRegistrationBean<MyServletFilter2> DoMyServletFilter2(){
        FilterRegistrationBean<MyServletFilter2> servletFilter = new FilterRegistrationBean<>(new MyServletFilter2());
        servletFilter.addUrlPatterns("/*");
        servletFilter.setOrder(1);

        return servletFilter;
    }
}
