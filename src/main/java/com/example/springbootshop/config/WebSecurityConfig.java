package com.example.springbootshop.config;

import com.example.springbootshop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().headers().frameOptions().disable();

        http.exceptionHandling()
                .accessDeniedPage("/error/403");

        http.authorizeRequests()
                // == API & PAGE TEST ==
                // .antMatchers("/", "/user/**", "/api/**", "shop/**").permitAll()
                .antMatchers("/", "/user/**").permitAll()
                .antMatchers("/shop/admin").hasRole("EMPLOYEE")
                .antMatchers("/shop/**").hasRole("USER")
                .antMatchers("/api/**").hasRole("USER")
                .and()
                .formLogin()
                .usernameParameter("userId")
                .passwordParameter("password")
                .loginPage("/")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/shop/main")
                .failureUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/").permitAll();



    }

    // == UserDetailsService 연동 ==
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
