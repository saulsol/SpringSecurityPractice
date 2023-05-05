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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 스프링 시큐리티는 기본적으로 PasswordEncoder라는 존재를 필요로 한다.
    // 없으면 ID 값이랑 Password 받을 때 오류남 -> 'There is no PasswordEncoder'


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername" + username);


        UserDetails userDetails = User.builder()
                .username("user1")
                .password("1111")
                .authorities("ROLE_USER")
                .build();


        return userDetails;
    }

    // UserDetails 는 사용자 인증(Authentication)과 관련된 정보들을 저장하는 역할을 한다.
    // 스프링 시큐리티는 내부적으로 UserDetails 타입의 객체를 이용해서 패스워드를 검사하고, 사용자 권한을 확인하는 방식으로 동작





}
