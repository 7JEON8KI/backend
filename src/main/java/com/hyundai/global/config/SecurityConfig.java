package com.hyundai.global.config;

import com.hyundai.domain.login.security.CustomAuthenticationEntryPoint;
import com.hyundai.domain.login.security.CustomDeniedHandler;
import com.hyundai.domain.login.security.JwtFilter;
import com.hyundai.domain.login.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * fileName      : SecurityConfig
 * author        : 변형준
 * since         : 2/19/24
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomDeniedHandler customDeniedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_MEMBER > ROLE_GUEST");
        return roleHierarchy;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN") // "ROLE_ADMIN"만 접근 가능
                .antMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGER") // "ROLE_MANAGER" 이상 접근 가능
                .antMatchers("/api/v1/test/member").hasAnyRole("ADMIN", "MANAGER", "MEMBER") // "ROLE_MEMBER" 이상 접근 가능
                .antMatchers(
                        "/api/v1/test/all"
                        ,"/api/v1/auth/login/kakao"
                        ,"/api/v1/auth/save"
                        ,"/api/v1/swagger-ui.html"
                        ,"/api/v1/webjars/**"
                        ,"/api/v1/v2/api-docs"
                        ,"/api/v1/v3/api-docs"
                        ,"/api/v1/swagger-resources/**"
                        ,"/api/v1/reviews/product/{productId}"
                        ,"/api/v1/products/**"
                )
                .permitAll() // 누구나 접근 가능
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
