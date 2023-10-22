package com.jwt.auth_server.initializer;

import com.jwt.auth_server.entity.Member;
import com.jwt.auth_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEntityInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) {
        Member user = Member.builder()
                .username("user")
                .roles("USER")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member admin = Member.builder()
                .username("admin")
                .roles("ADMIN")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member superAdmin = Member.builder()
                .username("super_admin")
                .roles("SUPER_ADMIN")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(user);
        memberRepository.save(admin);
        memberRepository.save(superAdmin);
    }
}
