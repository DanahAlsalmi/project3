package com.example.bankms.Config;

import com.example.bankms.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {


    private final MyUserDetailsService myUserDetailsService;
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/customer/register-customer",
                        "/api/v1/employee/register-employee").permitAll()
                .requestMatchers("/api/v1/customer/update","/api/v1/account/create",
                        "/api/v1/account/get-my").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/employee/update").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/account/details/{accountId}",
                        "/api/v1/account/user-account","/api/v1/account/deposit/",
                        "/api/v1/account/withdraw/",
                        "transfer/from/{fromAccountId}/to/{toAccountId}/amount/{amount}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/del/{customerId}",
                        "/api/v1/customer/get-all","/api/v1/employee/get-all",
                        "/api/v1/employee/del/{employeeId}","/api/account/get-all",
                        "/api/v1/account/activate-account/{accountId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/account/block/").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
