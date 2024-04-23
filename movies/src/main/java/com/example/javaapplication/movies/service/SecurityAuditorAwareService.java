package com.example.javaapplication.movies.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityAuditorAwareService implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // this line is commented because of using ActiveMQ that doesn't have any authentication level -->
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(securityContext -> securityContext.getAuthentication())
                .map(authentication -> authentication.getPrincipal())
                .filter(principle -> principle instanceof UserDetails)
                .map(principle -> (UserDetails) principle)
                .map(userDetails -> userDetails.getUsername());
    }
}