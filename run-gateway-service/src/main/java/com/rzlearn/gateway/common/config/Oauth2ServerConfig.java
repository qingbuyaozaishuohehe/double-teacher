package com.rzlearn.gateway.common.config;

import com.rzlearn.user.feign.IUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * OAuth2配置.
 *
 * @author zhangwb
 */
@Configuration
public class Oauth2ServerConfig {

    /**
     * OAuth2资源服务器配置.
     *
     * @author zhangwb
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private CustomerAuthenticationEntryPoint customerAuthenticationEntryPoint;

        @Autowired
        private CustomerLogoutSuccessHandler customerLogoutSuccessHandler;

        @Autowired
        private IUserController userController;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(customerAuthenticationEntryPoint)
                    .and()
                    .logout()
                    .logoutUrl("/oauth/logout")
                    .logoutSuccessHandler(customerLogoutSuccessHandler)
                    .and()
                    .headers()
                    .frameOptions()
                    .disable()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests();

            urlRegistry.antMatchers("/run-user/user/generateCode/*").permitAll();
            urlRegistry.antMatchers("/run-user/user/checkCode").permitAll();
            urlRegistry.antMatchers("/**/public/**").permitAll();
            urlRegistry.anyRequest().authenticated();
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
            resource.authenticationEntryPoint(customerAuthenticationEntryPoint);
        }
    }

    /**
     * OAuth2认证服务器配置.
     *
     * @author zhangwb
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        RedisConnectionFactory redisConnectionFactory;

        @Autowired
        private CustomerProperties customerProperties;

        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

        @Bean
        public TokenStore tokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory().withClient(customerProperties.getSecurity().getAuthentication().getOauth().getClient())
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    .scopes("*")
                    .secret(customerProperties.getSecurity().getAuthentication().getOauth().getSecret())
                    .accessTokenValiditySeconds(customerProperties.getSecurity().getAuthentication().getOauth().getTokenValidityInSeconds())
                    .refreshTokenValiditySeconds(customerProperties.getSecurity().getAuthentication().getOauth().getRefreshTokenValidityInSeconds());
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService);
            endpoints.exceptionTranslator(customWebResponseExceptionTranslator);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //允许表单认证
            oauthServer
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")
                    .allowFormAuthenticationForClients();
        }
    }
}
