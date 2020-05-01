package com.tenji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import com.tenji.service.UserDetailsServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userService;

    // 暗号化したフォームの値とDBの値を比較（パスワード）
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return bcpe;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")// ログインのビュー
                //.loginProcessingUrl("/login") //認証処理が実行される
                .usernameParameter("username")
                .passwordParameter("password")
                .successForwardUrl("/")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    /*@Bean
    @Override
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("user")
                .passwordEncoder(encoder::encode)
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }*/

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
