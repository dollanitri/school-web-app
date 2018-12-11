package com.school.core.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.core.model.CustomUserDetails;
import com.school.core.model.Role;
import com.school.core.model.User;
import com.school.core.service.UserService;

/**
 * {@link CustomUserDetailsService} contract defines a single method called
 * loadUserByUsernameAndTenantname.
 * 
 * The {@link CustomUserDetailsServiceImpl} class simply implements the contract
 * and delegates to {@link UserService} to get the
 * {@link com.example.model.User} from the database so that it can be compared
 * with the {@link org.springframework.security.core.userdetails.User} for
 * authentication. Authentication occurs via the
 * {@link CustomUserDetailsAuthenticationProvider}.
 * 
 * @author Satyam Dollani
 *
 */
@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsernameAndTenantname(String username, String tenant)
            throws UsernameNotFoundException {
        if (StringUtils.isAnyBlank(username, tenant)) {
            throw new UsernameNotFoundException("Username and domain must be provided");
        }
        // Look for the user based on the username and tenant by accessing the
        // UserRepository via the UserService
        User user = userService.findByUsernameAndTenantname(username, tenant);

        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("Username not found for domain, "
                            + "username=%s, tenant=%s", username, tenant));
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        CustomUserDetails customUserDetails = 
                new CustomUserDetails(user.getUsername(), 
                        user.getPassword(), grantedAuthorities, tenant);
        
        return customUserDetails;
    }
}