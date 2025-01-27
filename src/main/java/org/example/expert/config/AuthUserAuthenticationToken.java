package org.example.expert.config;

import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthUserAuthenticationToken extends AbstractAuthenticationToken {
    private final AuthUser principal;

    public AuthUserAuthenticationToken(
        Collection<? extends GrantedAuthority> authorities, AuthUser authUser
    ) {
        super(authorities);
        this.principal = authUser;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
