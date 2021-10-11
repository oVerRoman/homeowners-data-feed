package com.simbirsoftintensiv.intensiv.config;

import com.simbirsoftintensiv.intensiv.OtpAuthenticationProvider;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final UserService userService;
    final OtpAuthenticationProvider otpAuthenticationProvider;

    public WebSecurityConfig(OtpAuthenticationProvider otpAuthenticationProvider, UserService userService) {
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                // Доступ только для не зарегистрированных пользователей
                .antMatchers("/onetimecode").not().fullyAuthenticated()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/username").not().fullyAuthenticated()
                .antMatchers("/counters").not().fullyAuthenticated()
                .antMatchers("/addCounter").not().fullyAuthenticated()
                .antMatchers("/saveCounter").not().fullyAuthenticated()
                .antMatchers("/saveCounterValues").not().fullyAuthenticated()
                // Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/news").hasRole("ADMIN")
                .antMatchers("/news").hasRole("USER")
//                .antMatchers("/counters").hasRole("USER")
//                .antMatchers("/add-counter").hasRole("USER")
                // Доступ разрешен всем пользователей
                .antMatchers("/", "/resources/**").permitAll()
                // Все остальные страницы требуют аутентификации
                .anyRequest().authenticated().and()
                // Настройка для входа в систему
                .formLogin()

//                .loginPage("/password")
                .loginPage("/login")

                // Перенаправление на главную страницу после успешного входа
                .defaultSuccessUrl("/").permitAll().and().logout().permitAll().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(otpAuthenticationProvider);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}