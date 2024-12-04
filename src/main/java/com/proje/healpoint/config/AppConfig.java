package com.proje.healpoint.config;

import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class AppConfig {
    @Autowired
    private PatientRepository patientRepository;
    @Bean
    public UserDetailsService userDetailsService() {
       return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String patientTc) throws UsernameNotFoundException {
                Optional<Patients> optional =patientRepository.findById(patientTc);
                if (optional.isPresent()){
                    return (UserDetails) optional.get();
                }
                return null;
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
