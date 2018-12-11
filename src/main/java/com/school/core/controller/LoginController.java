package com.school.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.message.request.LoginForm;
import com.school.core.message.response.JwtResponse;
import com.school.core.model.CustomUserDetails;
import com.school.core.repository.RoleRepository;
import com.school.core.repository.UserRepository;
import com.school.core.security.CustomAuthenticationToken;
import com.school.core.security.jwt.JwtProvider;
import com.school.core.util.TenantContextHolder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        TenantContextHolder.setTenantId(loginRequest.getTenant());
		Authentication authentication = authenticationManager.authenticate(
				new CustomAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getTenant()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getTenant(), userDetails.getAuthorities()));
	}

}
