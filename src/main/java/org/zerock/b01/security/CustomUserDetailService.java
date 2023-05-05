package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    // final을 붙이지 않으면 의존성 주입이 되지 않음
    // @RequiredArgsConstructor 을 사용해서

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername" + username);


        UserDetails userDetails = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("1111")) // 패스워드 인코더 필요, 즉 유저 디테일 객체는 PasswordEncoder 객채를 필요로 한다
                .authorities("ROLE_USER")
                .build();


        return userDetails;
    }

    // UserDetails 는 사용자 인증(Authentication)과 관련된 정보들을 저장하는 역할을 한다.
    // 스프링 시큐리티는 내부적으로 UserDetails 타입의 객체를 이용해서 패스워드를 검사하고, 사용자 권한을 확인하는 방식으로 동작





}
