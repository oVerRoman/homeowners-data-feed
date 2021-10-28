package com.simbirsoftintensiv.intensiv.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.simbirsoftintensiv.intensiv.OtpAuthenticationProvider;
import com.simbirsoftintensiv.intensiv.config.handler.CustomAuthenticationFailureHandler;
import com.simbirsoftintensiv.intensiv.config.handler.MySimpleUrlAuthenticationSuccessHandler;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final MySimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserService userService;
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    private final ObjectMapper objectMapper;

    public WebSecurityConfig(MySimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler,
                             OtpAuthenticationProvider otpAuthenticationProvider,
                             UserService userService, ObjectMapper objectMapper) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("http://localhost:3000/", "https://localhost:3000/"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Access-Control-Allow-Origin", "Authorization", "Access-Control-Request-Method", "content-type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                // Доступ только для не зарегистрированных пользователей
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**", "/", "/resources/**", "/v3/api-docs/**",
                        "/swagger-ui/**", "/login").permitAll()
//                .antMatchers("/rest/allcounters").not().authenticated() // fixme delete
//                .antMatchers("/rest/counters").not().authenticated() // fixme delete
                .antMatchers("/onetimecode").not().authenticated()
                .antMatchers("/registration-otp").not().authenticated()
                .antMatchers("/rest/users").not().authenticated()
                .antMatchers("/registration").not().authenticated()
                .antMatchers("/username").not().authenticated()
//                .antMatchers("/rest/profile").authenticated()
                .antMatchers("/rest/**").authenticated()
                // Доступ только для пользователей с ролью Администратор
//                .antMatchers("/rest/admin").hasRole("ADMIN")
                .antMatchers("/rest/admin/**").hasRole("ADMIN")
                // TODO разобраться
//                .antMatchers("/news").hasAnyAuthority("USER", "ADMIN")// почему то так не работает
                .antMatchers("/news").hasRole("USER")
//                .antMatchers("/counters").hasRole("USER")
                .antMatchers("/add-counter").hasRole("USER")
                .antMatchers("/saveCounter").hasRole("USER")
                .antMatchers("/upload").hasRole("USER")
                .antMatchers("/saveCounterValues").hasRole("USER")
//                .antMatchers("/rest/counters").authenticated()
                .antMatchers("/request").hasRole("USER")
                .antMatchers("/request/**").hasRole("USER")
                .antMatchers("/files/**").hasRole("USER")
                // Доступ разрешен всем пользователей
                .antMatchers("/version").permitAll()
//                .anyRequest().not().authenticated()
//
                // Все остальные страницы требуют аутентификации

                .and()
                // Настройка для входа в систему
                .formLogin()

                .loginPage("/login")

//                .defaultSuccessUrl("/").permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new CustomAuthenticationFailureHandler())
//
                .and()
                .logout()
                .permitAll().logoutSuccessUrl("/")
                .and()
                .logout().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .csrf().disable()
                .cors()
                      // Перенаправление на главную страницу после успешного выхода
//                .defaultSuccessUrl("/").permitAll()
                .and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .and()

        ;
        ;

    /*    httpSecurity.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));*/
    }

    // Насстройки запросов CORS. Разрешены все запросы с любого адреса
  /*  @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                ;
                *//*
     *//*
     *//*   .allowedOrigins("https://localhost:3000")
//                        .allowedOriginPatterns("*")
                        .allowCredentials(true)
//                        .allowedHeaders("Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers,")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");*//**//*

            }
        };
*//*
            }
        };
    }*/


   /* @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:3000/")
                .allowCredentials(true)
                .allowedHeaders("Access-Control-Allow-Origin", "Authorization", "Access-Control-Request-Method", "content-type")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        ;
    }*/

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