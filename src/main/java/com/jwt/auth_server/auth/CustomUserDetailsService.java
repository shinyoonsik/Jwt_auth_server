package com.jwt.auth_server.auth;

import com.jwt.auth_server.entity.Member;
import com.jwt.auth_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsernameg호출: {}", username);
        Member foundMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("no user: " + username));

        // UserDetailsService.loadUserByUsername()호출될 때마다 새로운 UserDetails인스턴스가 반환된다
        return new CustomUserDetails(foundMember);
    }
}
