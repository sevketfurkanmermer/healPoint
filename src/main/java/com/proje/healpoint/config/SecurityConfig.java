package com.proje.healpoint.config;

import com.proje.healpoint.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String AUTHENTICATE = "api/v1/authenticate";
    private static final String REGISTER_PATIENT = "/api/v1/patients/create";
    private static final String REGISTER_DOCTOR = "/api/v1/doctors/save";
    private static final String LOGIN = "api/v1/login";
    private static final String LOGIN_DOCTOR = "api/v1/login-doctor";
    private static final String LIST_DOCTORS = "/api/v1/doctors/list";
    private static final String LIST_DOCTORS_REVIEWS = "/api/v1/review/list-doctor/{doctorTc}";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(request->request.requestMatchers(AUTHENTICATE,
                                REGISTER_PATIENT,
                                REGISTER_DOCTOR,
                                LOGIN,
                                LOGIN_DOCTOR,
                                LIST_DOCTORS,
                                LIST_DOCTORS_REVIEWS)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
