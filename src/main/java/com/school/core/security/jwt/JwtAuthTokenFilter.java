package com.school.core.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.school.core.model.CustomUserDetails;
import com.school.core.security.CustomAuthenticationToken;
import com.school.core.security.CustomUserDetailsService;
import com.school.core.util.TenantContextHolder;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

			String jwt = getJwt(request);
			if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
				String tokenValue = tokenProvider.getUserNameFromJwtToken(jwt);	
				if(tokenValue != null) {
					String[] tokenValues = tokenValue.split(",");

					CustomUserDetails userDetails = userDetailsService.loadUserByUsernameAndTenantname(tokenValues[0], tokenValues[1]);
					CustomAuthenticationToken authentication = new CustomAuthenticationToken(
							userDetails.getUsername(), userDetails.getPassword(), userDetails.getTenant(), userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					logger.error("Token value not found from JWT");
				}
				
			}
		} catch (Exception e) {
			logger.error("Can NOT set user authentication -> Message: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}

		return null;
	}
}
