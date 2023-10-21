package com.jwt.auth_server.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class MySpringSecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Spring Security에 등록된 MyFilter");
        chain.doFilter(request, response);
    }
}
