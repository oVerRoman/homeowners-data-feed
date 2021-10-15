package com.simbirsoftintensiv.intensiv.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simbirsoftintensiv.intensiv.AuthSuccessHandler;
import com.simbirsoftintensiv.intensiv.OtpAuthenticationProvider;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final AuthSuccessHandler authSuccessHandler;
    final UserService userService;
    final OtpAuthenticationProvider otpAuthenticationProvider;
    private final ObjectMapper objectMapper;

    public WebSecurityConfig(AuthSuccessHandler authSuccessHandler,
                             OtpAuthenticationProvider otpAuthenticationProvider,
                             UserService userService, ObjectMapper objectMapper) {
        this.authSuccessHandler = authSuccessHandler;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                // Доступ только для не зарегистрированных пользователей

                .antMatchers("/rest/allcounters").not().authenticated() //fixme delete
                .antMatchers("/rest/counters").not().authenticated() //fixme delete
                .antMatchers("/onetimecode").not().authenticated()
                .antMatchers("/rest/users").not().authenticated()
                .antMatchers("/registration").not().authenticated()
                .antMatchers("/username").not().authenticated()
                .antMatchers("/rest/profile").authenticated()
                // Доступ только для пользователей с ролью Администратор
                .antMatchers("/rest/admin").hasRole("ADMIN")
                .antMatchers("/rest/admin/**").hasRole("ADMIN")
                .antMatchers("/news").hasRole("ADMIN")
                .antMatchers("/news").hasRole("USER")
//                .antMatchers("/counters").hasRole("USER")
                .antMatchers("/add-counter").hasRole("USER")
                .antMatchers("/saveCounter").hasRole("USER")
                .antMatchers("/saveCounterValues").hasRole("USER")
                .antMatchers("/rest/counters").hasRole("USER")
                .antMatchers("/request").hasRole("USER")
                .antMatchers("/request/**").hasRole("USER")
                // Доступ разрешен всем пользователей
                .antMatchers("/", "/resources/**").permitAll()
                // Все остальные страницы требуют аутентификации
                .anyRequest().authenticated().and()
                // Настройка для входа в систему
                .formLogin()

//                .loginPage("/password")
                .loginPage("/login")

                // Перенаправление на главную страницу после успешного выхода
                .defaultSuccessUrl("/").permitAll().

                successHandler(authSuccessHandler).

                and()
                .logout()
                .permitAll().logoutSuccessUrl("/")
        ;

        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(otpAuthenticationProvider);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @PostConstruct
    void setMapper() {
        JsonUtil.setObjectMapper(objectMapper);
    }
}