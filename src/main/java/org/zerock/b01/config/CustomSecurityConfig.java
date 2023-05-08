package org.zerock.b01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.zerock.b01.security.CustomUserDetailService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        
        log.info("-----------------configure------------------");
        // 커스텀 로그인 페이지
        httpSecurity.formLogin().loginPage("/member/login");
        //CSRF 토큰 비활성화
        httpSecurity.csrf().disable();

        httpSecurity.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(customUserDetailService)
                .tokenValiditySeconds(60*60*24*30);

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


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 스프링 시큐리티는 기본적으로 PasswordEncoder라는 존재를 필요로 한다.
    // 없으면 ID 값이랑 Password 받을 때 오류남 -> 'There is no PasswordEncoder'


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }


}
