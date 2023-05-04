package org.zerock.b01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        log.info("-----------------configure------------------");
        httpSecurity.formLogin();
        return httpSecurity.build();
    }

    // 기존에는 다른 API에 접근하려면 /login 으로 리다이렉션되었다.
    // 하지만 filterChain() 메소드가 동작하면 이전과 달리 '/board/list' 에 바로 접근 가능

    // 앞선 결과로 짐작할 수 있는 것은 CustomSecurityConfig 의 filterChain() 메소드 설정으로 모든 사용자가 모든 경로에 접근할 수 있게 되었다.
    // 그렇다면 앞으로는 filterChain() 메소드를 사용해서 클라이언트가 필요한 자원에 접근을 하는 경우 로그인을 하지 않았다면 자원 접근을 제어해야 한다.

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

        log.info("-----------------web configure-------------");

        return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        );

    }


}
