package com.school.core.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.school.core.model.CustomUserDetails;

public interface CustomUserDetailsService {
    
	CustomUserDetails loadUserByUsernameAndTenantname(String username, String tenantName) throws UsernameNotFoundException;
}
