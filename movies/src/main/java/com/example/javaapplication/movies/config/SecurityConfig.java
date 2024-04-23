package com.example.javaapplication.movies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String USER_AUTHORITY = "USER";
    public static final String ADMIN_AUTHORITY = "ADMIN";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password("$2a$12$b.ZuXGyzo94FDQeUPGdAO.6YVFQrw0ppiyKiZLIy.RwwyNJeZWYXe") // Password1
                .authorities(USER_AUTHORITY)
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$12$wLQTeMHGaTWOyBrAN8I92Or8tXaui5/W/BByryZtXA9xkXZSSWime") // adminPassword
                .authorities(USER_AUTHORITY, ADMIN_AUTHORITY)
                .build();

        UserDetails Administrator = User.builder()
                .username("Administrator")
                .password("$2a$12$wLQTeMHGaTWOyBrAN8I92Or8tXaui5/W/BByryZtXA9xkXZSSWime") // adminPassword
                .authorities(USER_AUTHORITY, ADMIN_AUTHORITY)
                .build();

        return new InMemoryUserDetailsManager(user1, admin, Administrator);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authenticationManager,
                                                   UserDetailsService userDetailsService) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET).hasAuthority(USER_AUTHORITY)
                        .requestMatchers(HttpMethod.POST).hasAuthority(ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.PUT).hasAuthority(ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.DELETE).hasRole(ADMIN_AUTHORITY)
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults()) // user Basic Auth
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        DefaultSecurityFilterChain build = http.build();
        return build;
    }
}