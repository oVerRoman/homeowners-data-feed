package com.simbirsoftintensiv.intensiv.config;

import com.simbirsoftintensiv.intensiv.OtpAuthenticationProvider;
import com.simbirsoftintensiv.intensiv.config.handler.CustomAuthenticationFailureHandler;
import com.simbirsoftintensiv.intensiv.config.handler.MySimpleUrlAuthenticationSuccessHandler;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

                .antMatchers("/rest/allcounters").not().fullyAuthenticated() //fixme delete
                .antMatchers("/rest/counters").not().fullyAuthenticated() //fixme delete
                .antMatchers("/onetimecode").not().fullyAuthenticated()
                .antMatchers("/rest/users").not().fullyAuthenticated()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/username").not().fullyAuthenticated()
                // Доступ только для пользователей с ролью Администратор
                .antMatchers("/rest/admin").hasRole("ADMIN")
                .antMatchers("/rest/admin/**").hasRole("ADMIN")
                //TODO разобраться
//                .antMatchers("/news").hasAnyAuthority("USER", "ADMIN")// почему то так не работает
                .antMatchers("/news").hasRole("USER")
//                .antMatchers("/counters").hasRole("USER")
                .antMatchers("/add-counter").hasRole("USER")
                .antMatchers("/saveCounter").hasRole("USER")
                .antMatchers("/upload").hasRole("USER")
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

                .loginPage("/login")

                // Перенаправление на главную страницу после успешного входа
//                .defaultSuccessUrl("/success").permitAll()
                // обработчик успешного входа
                .successHandler(myAuthenticationSuccessHandler())
                // не успешного входа
                .failureHandler(new CustomAuthenticationFailureHandler())
                .and()
                .logout().permitAll()
                // Перенаправление на главную страницу после успешного выхода
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(otpAuthenticationProvider);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
}