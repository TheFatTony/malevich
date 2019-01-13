package io.malevich.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyAuthenticationProvider implements AuthenticationProvider {

    private static final List<GrantedAuthority> AUTHORITIES
            = new ArrayList<GrantedAuthority>();

    public static SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    public static SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    public static SimpleGrantedAuthority ROLE_GALLERY = new SimpleGrantedAuthority("ROLE_GALLERY");
    public static SimpleGrantedAuthority ROLE_TRADER = new SimpleGrantedAuthority("ROLE_TRADER");

    static {
        AUTHORITIES.add(ROLE_ADMIN);
        AUTHORITIES.add(ROLE_USER);
        AUTHORITIES.add(ROLE_GALLERY);
        AUTHORITIES.add(ROLE_TRADER);
    }

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {

        if (auth.getName().equals(auth.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(auth.getName(),
                    auth.getCredentials(), AUTHORITIES);
        }

        throw new BadCredentialsException("Bad Credentials");

    }

    @Override
    public boolean supports(Class<?> authentication) {

        if ( authentication == null ) return false;
        return Authentication.class.isAssignableFrom(authentication);

    }
}
