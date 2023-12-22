package com.grocye.grocyerest.config;

import org.springframework.context.annotation.Bean;
/*
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
*/

//@Configuration
public class SecurityConfig {
    private int saltLength = 16; // salt length in bytes
    private int hashLength = 32; // hash length in bytes
    private int parallelism = 1; // currently not supported by Spring Security
    private int memory = 4096;   // memory costs
    private int iterations = 3;
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }

    */
}
