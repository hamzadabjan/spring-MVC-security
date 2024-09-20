package com.hamza.springboot.training.spring.mvc.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

// add support for JDBC
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id,pw,active from members where user_id=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id,role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leaders").hasRole("MANAGER")
                        .requestMatchers("/system").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form ->
                form
                        .loginPage("/showMyLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
        )
                .logout(logout-> logout.permitAll())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/access-denied"))
        ;

        return http.build();
    }

    // @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        UserDetails hamza = User.builder()
//                .username("hamza")
//                .password("{noop}test123")
//                .roles("ADMIN","MANAGER","EMPLOYEE")
//                .build();
//
//        UserDetails hanan = User.builder()
//                .username("hanan")
//                .password("{noop}test123")
//                .roles("MANAGER","EMPLOYEE")
//                .build();
//
//        UserDetails rayhan = User.builder()
//                .username("rayhan")
//                .password("{noop}test123")
//                .roles("EMPLOYEE")
//                .build();
//
//        return new InMemoryUserDetailsManager(hamza,hanan,rayhan);
//    }

}