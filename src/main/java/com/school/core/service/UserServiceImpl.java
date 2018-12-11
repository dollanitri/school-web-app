package com.school.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.school.core.model.User;
import com.school.core.repository.UserRepository;

/**
 * Implementation of the {@link UserService} which accesses the {@link User}
 * entity. This is the recommended way to access the entities through an
 * interface rather than using the corresponding repository. This allows for
 * separation into repository code and the service layer.
 * 
 * @author Satyam Dollani
 * 
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            String username = ((UserDetails) userDetails).getUsername();
            LOG.info("Logged in username:" + username);
            return username;
        }

        return null;
    }

    @Override
    public User findByUsernameAndTenantname(String username, String tenant) {
        User user = userRepository.findByUsernameAndTenantname(username, tenant);
        LOG.info("Found user with username:" + user.getUsername() + " from tenant:" + user.getTenant());
        return user;
    }

}
