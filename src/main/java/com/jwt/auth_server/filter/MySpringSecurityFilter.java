package com.jwt.auth_server.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MySpringSecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 1. (id, pw)가 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답해 주면 된다
        // 2. 요청할 때마다 header에 "Authorization=Bearer Token"을 담아 요청
        // 3. header의 Authorization의 값을 가져와 검증

        if(req.getMethod().equals("POST")){
            log.info("MySpringSecurityFilter: Post요청");
            String authHeader = req.getHeader("Authorization");

            if(authHeader.equals("myToken")){
                chain.doFilter(req, res);
            }else{
                PrintWriter writer = res.getWriter();
                writer.println("토큰 인증 안됨!");
            }
        }



        System.out.println("Spring Security에 등록된 MyFilter");
        chain.doFilter(request, response);


    }
}
