package com.example.musicdb.config;

import com.example.musicdb.service.impl.MusicDBUserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final MusicDBUserService musicDBUserService;
    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfig(MusicDBUserService musicDBUserService, PasswordEncoder passwordEncoder) {
        this.musicDBUserService = musicDBUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(musicDBUserService).passwordEncoder(passwordEncoder);
    }

    @Override // set access to resources
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll() // access to static from anyone
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/**").authenticated() // protect all other pages
                .and()
                .formLogin() // configure Login with HTML form (build)
                .loginPage("/users/login") // custom login form served by Controller
                .usernameParameter("username") // from <input type="text" name="username" ... />
                .passwordParameter("password") // from <input type="password" name="password" ... />
                .defaultSuccessUrl("/") // after successful Login go to Homepage
                .failureForwardUrl("/users/login-error");
    }
}
