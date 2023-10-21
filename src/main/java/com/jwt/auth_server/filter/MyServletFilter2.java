package com.jwt.auth_server.filter;


import jakarta.servlet.*;

import java.io.IOException;

public class MyServletFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Java Servlet 필터2");
        chain.doFilter(request, response);
    }
}