package com.school.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.core.model.Role;
import com.school.core.repository.RoleRepository;

/**
 * Implementation of the {@link RoleService} which accesses the {@link Role}
 * entity. This is the recommended way to access the entities through an
 * interface rather than using the corresponding repository. This allows for
 * separation into repository code and the service layer.
 * 
 * @author Satyam Dollani
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

	@Override
	public Role findByRole(String roleName) {
		Role role = roleRepository.findByRole(roleName);
        LOG.info("Role:" + role.getRole() + " found");
        return role;
	}

}
