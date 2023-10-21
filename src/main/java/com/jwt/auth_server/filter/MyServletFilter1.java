package com.jwt.auth_server.filter;


import jakarta.servlet.*;
import java.io.IOException;

public class MyServletFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Java Servlet 필터1");
        chain.doFilter(request, response);
    }
}