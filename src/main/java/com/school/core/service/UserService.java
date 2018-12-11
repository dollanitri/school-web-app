package com.school.core.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.core.model.User;

/**
 * Service definition which accesses the {@link com.example.model.User} entity.
 * This is the recommended way to access the entities through an interface
 * rather than using the corresponding repository directly. This allows for
 * separation into repository code and the service layer.
 * 
 * @author Satyam Dollani
 * 
 */
public interface UserService {

    String findLoggedInUsername();

    @Query("select p from User p where p.username = :username and p.tenant = :tenant")
    User findByUsernameAndTenantname(@Param("username") String username, @Param("tenant") String tenant);
}
