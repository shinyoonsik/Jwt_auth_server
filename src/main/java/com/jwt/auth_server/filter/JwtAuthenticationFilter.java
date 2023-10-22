package com.jwt.auth_server.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.auth_server.auth.CustomUserDetails;
import com.jwt.auth_server.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // POST "/login"요청을 하면 로그인 시도를 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");

        // 1. username, password 받아서
        ObjectMapper om = new ObjectMapper();
        Member member;
        try {
            member = om.readValue(request.getInputStream(), Member.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2. 정상인지 로그인 시도를 한다
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
        // authenticationManager.authenticate()를 통해 UserDetailsService.loadUserByUsername()호출하여 인증을 시도
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        log.info("정상적으로 로그인됨: {}", authentication.getPrincipal());

        // 3. 로그인인 정상적으로 이루어지면 securityContext에 인증객체를 담는다
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. successfulAuthentication()에서 Jwt토큰 만들어서 리턴

        return authentication;
    }

    // attemptAuthentication()을 통해 인증이 성공하면 successfulAuthentication()이 실행됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("인증 완료!");

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        // HMAC512가 secretkey를 가지고 암호화하는 방식
        String jwtToken = JWT.create()
                .withSubject("My_Token")
                .withExpiresAt(new Date(System.currentTimeMillis() + 10000*6*30))
                .withClaim("id", userDetails.getMember().getId()) // 비공개 클레임(원하는 값넣어주면 된다)
                .withClaim("username", userDetails.getMember().getUsername()) // 비공개 클레임(원하는 값넣어주면 된다)
                .sign(Algorithm.HMAC512("secret_key"));

        response.addHeader("Authorization", "Bearer "+ jwtToken);
    }
}
