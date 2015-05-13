package org.cactus.server.config;

import org.cactus.server.api.AuthApi;
import org.cactus.server.security.CustomUserDetailsService;
import org.cactus.server.security.HttpAuthenticationEntryPoint;
import org.cactus.server.security.handlers.AuthFailureHandler;
import org.cactus.server.security.handlers.AuthSuccessHandler;
import org.cactus.server.security.handlers.HttpLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new ShaPasswordEncoder());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and();

        http = http.authorizeRequests()
                .antMatchers("/**").authenticated()
                .anyRequest().authenticated()
                .and();

        http.formLogin()
                .loginProcessingUrl(AuthApi.LOGIN_URL)
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .usernameParameter(AuthApi.Parameters.USERNAME)
                .passwordParameter(AuthApi.Parameters.PASSWORD)
                .permitAll()
                .and();

        http = http.logout()

                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher(AuthApi.LOGIN_URL, "DELETE"))
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(AuthApi.Parameters.COOKIES)
                .invalidateHttpSession(true)
                .and();

        http.csrf().disable();

    }

}
