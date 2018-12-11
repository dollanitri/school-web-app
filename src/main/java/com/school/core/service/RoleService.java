package com.school.core.service;

import com.school.core.model.Role;

/**
 * Service definition which accesses the {@link Role} entity. This is the
 * recommended way to access the entities through an interface rather than using
 * the corresponding repository. This allows for separation into repository code
 * and the service layer.
 * 
 * @author Satyam Dollani
 * 
 */
public interface RoleService {

    Role findByRole(String role);
}
