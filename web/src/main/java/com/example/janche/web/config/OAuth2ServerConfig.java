package com.example.janche.web.config;


import com.example.janche.security.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * @author lirong
 * @description SSO服务端
 * @date 2019-3-18 09:04:36
 */
@Configuration
public class OAuth2ServerConfig {

    /**
     * 注册资源服务器，开放给SSO客户端访问的资源
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        private static final String RESOURCE_ID = "sso-oauth2-server";
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // 如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，后续请求不携带 accessToken 也可以正常响应
            resources.resourceId(RESOURCE_ID).stateless(false);
        }

        /**
         * 为oauth2单独创建角色，这些角色只具有访问受限资源的权限，可解决token失效的问题
         * @param http
         * @throws Exception
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // 获取登录用户的 session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    // 资源服务器拦截的路径 注意此路径不要拦截主过滤器放行的URL
                    .requestMatchers().antMatchers("/user/oauth/**");
            http
                    .authorizeRequests()
                    // 配置资源服务器已拦截的路径才有效
                    .antMatchers("/user/oauth/**").authenticated();
            // .access(" #oauth2.hasScope('select') or hasAnyRole('ROLE_超级管理员', 'ROLE_设备管理员')");

            http
                    .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        private DataSource dataSource;
        @Autowired
        SecurityUserService userDetailsService;

        // @Bean
        // public TokenStore tokenStore() {
        //     return new JdbcTokenStore(dataSource);
        // }

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey("sso-oauth2-server");
            return converter;
        }

        /**
         * 密码加密
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        /**
         * 加入对授权码模式的支持
         * @param dataSource
         * @return
         */
        @Bean
        public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
            return new JdbcAuthorizationCodeServices(dataSource);
        }


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 1. 数据库的方式
            clients.jdbc(dataSource);

            // 2. 内存的方式
            // 定义了两个客户端应用的通行证
            // clients.inMemory()
            //         .withClient("moregame")
            //         .secret(new BCryptPasswordEncoder().encode("123456"))
            //         .authorizedGrantTypes("authorization_code", "refresh_token")
            //         .redirectUris("http://www.site2.com/login")
            //         .scopes("all")
            //         .accessTokenValiditySeconds(3600)
            //         .autoApprove(true)
            //
            //         .and()
            //         .withClient("sheep1")
            //         .secret(new BCryptPasswordEncoder().encode("123456"))
            //         .authorizedGrantTypes("authorization_code", "refresh_token")
            //         .redirectUris("http://www.site1.com/login")
            //         .scopes("all")
            //         .accessTokenValiditySeconds(3600)
            //         .autoApprove(true);
        }


        /**
         * 声明授权和token的端点以及token的服务的一些配置信息，
         * 比如采用什么存储方式、token的有效期等
         * @param endpoints
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

            endpoints
                    .tokenStore(jwtTokenStore())
                    .accessTokenConverter(jwtAccessTokenConverter())
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService);


        }

        /**
         * 声明安全约束，哪些允许访问，哪些不允许访问
         * @param security
         */
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) {
            //允许表单认证
            security.allowFormAuthenticationForClients();
            security.passwordEncoder(passwordEncoder());
            // 对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
            security.tokenKeyAccess("isAuthenticated()");
        }
    }

}
