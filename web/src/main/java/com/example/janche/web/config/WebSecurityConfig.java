package com.example.janche.web.config;

import com.example.janche.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("securityAuthenticationProvider")
    private AuthenticationProvider securityAuthenticationProvider;

    @Autowired
    @Qualifier("userLoginSuccessHandler")
    private AuthenticationSuccessHandler userLoginSuccessHandler;

    @Autowired
    @Qualifier("securityAuthenticationFailureHandler")
    private AuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Autowired
    @Qualifier("securityLogoutSuccessHandler")
    private LogoutSuccessHandler securityLogoutSuccessHandler;

    @Autowired
    @Qualifier("securityAccessDeniedHandler")
    private AccessDeniedHandler securityAccessDeniedHandler;

    @Autowired
    @Qualifier("securityAuthenticationEntryPoint")
    private AuthenticationEntryPoint securityAuthenticationEntryPoint;

    @Autowired
    @Qualifier("urlFilterInvocationSecurityMetadataSource")
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @Autowired
    @Qualifier("urlAccessDecisionManager")
    AccessDecisionManager urlAccessDecisionManager;



    /**
     * 访问静态资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/images/**",
                "/fonts/**",
                "/favicon.ico",
                "/static/**",
                "/resources/**","/error","/status/*", "/swagger-ui.html", "/v2/**", "/webjars/**", "/swagger-resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(securityAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                // .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(urlObjectPostProcessor())
            .and()
                .formLogin()
                .loginPage("/login") //自定义登录页面
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .failureHandler(securityAuthenticationFailureHandler)
                // .successHandler(userLoginSuccessHandler)
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .accessDeniedHandler(securityAccessDeniedHandler)
            .and()
                .logout()
                .deleteCookies("SSO-SESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessHandler(securityLogoutSuccessHandler)
                .permitAll()
            .and()
                .csrf().disable();

        http
                .sessionManagement()
                // 无效session跳转
                .invalidSessionUrl("/login")
                .maximumSessions(2)
                // session过期跳转
                .expiredUrl("/login")
                .sessionRegistry(sessionRegistry());
    }

    /**
     * 解决session失效后 sessionRegistry中session没有同步失效的问题
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    public ObjectPostProcessor urlObjectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        };
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
    }


    /**
     * 设置加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
