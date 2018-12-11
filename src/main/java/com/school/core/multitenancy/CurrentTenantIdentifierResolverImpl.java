package com.school.core.multitenancy;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import com.school.core.util.TenantContextHolder;

/**
 * Hibernate needs to know which database to use i.e. which tenant to connect
 * to. This class provides a mechanism to provide the correct datasource at run
 * time.
 * 
 * @see {@link com.example.util.TenantContextHolder}
 * @see {@link com.example.security.CustomAuthenticationFilter}
 * 
 * @author Satyam Dollani
 */
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT_ID = "tenant_1";
    @Override
    public String resolveCurrentTenantIdentifier() {
        // The tenant is stored in a ThreadLocal before the end user's login information
        // is submitted for spring security authentication mechanism. Refer to
        // CustomAuthenticationFilter
        String tenant = TenantContextHolder.getTenant();
        return StringUtils.isNotBlank(tenant) ? tenant : DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}
