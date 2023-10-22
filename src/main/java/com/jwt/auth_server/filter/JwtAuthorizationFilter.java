package com.jwt.auth_server.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.auth_server.auth.CustomUserDetails;
import com.jwt.auth_server.entity.Member;
import com.jwt.auth_server.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

// 권한혹은 인증이 필요한 특정 주소 요청시 시큐리티의 BasicAuthenticationFilter가 실행된다.
// 만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 타지 않는다
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
    }


    // 인증이나 권한이 필요한 주소요청이 있을 때 실행됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // header를 통해 jwt토큰 유무 판단
        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = jwtHeader.replace("Bearer ", "");
        String username = JWT
                .require(Algorithm.HMAC512("secret_key"))
                .build()
                .verify(jwt) // jwt검증
                .getClaim("username")
                .asString();

        // 서명 완료
        if (username != null) {
            Optional<Member> optMember = memberRepository.findByUsername(username);
            optMember.ifPresent((member) -> {
                CustomUserDetails customUserDetails = new CustomUserDetails(member);
                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails.getUsername(), null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }

        chain.doFilter(request, response);
    }
}
