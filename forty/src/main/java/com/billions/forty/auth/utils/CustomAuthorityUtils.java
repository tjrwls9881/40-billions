package com.billions.forty.auth.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthorityUtils {

    private String adminEmail;

    private final List<String> ADMIN_ROLES_STRING= List.of("ROLE_ADMIN", "ROLE_USER");

    private final List<String> USER_ROLES_STRING= List.of("ROLE_USER");

    public List<GrantedAuthority> createAuthorities(List<String> roles){
        List<GrantedAuthority> authorities=
                roles.stream()
                        .map(role ->new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());

        return authorities;
    }

    public List<String> createRoles(String email){
        if(email.equals(adminEmail)){
            return ADMIN_ROLES_STRING;
        }
        return USER_ROLES_STRING;
    }
}