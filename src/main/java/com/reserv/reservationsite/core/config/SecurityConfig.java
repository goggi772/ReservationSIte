package com.reserv.reservationsite.core.config;


import com.reserv.reservationsite.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //security filter등록
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final MemberDetailsService memberDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration, AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login/**", "/js/**", "/css/**", "/image/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login/action")   //로그인 실패 시 error, exception 파라미터 전송
                .defaultSuccessUrl("/")
                //.failureHandler(customAuthFailureHandler)// 실패시 요청을 처리할 핸들러
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
                .logoutSuccessUrl("/"); // 성공시 리턴 URL

        return http.build();

    }

    public DaoAuthenticationProvider daoAuthenticationProvider() { //아이디가 맞지 않을 때 UserNotFoundException을 발생하게 하는 메소드
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(memberDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

}
