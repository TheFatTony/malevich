package io.malevich.server.config;

import com.yinyang.core.server.domain.Role;
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

    private static final List<Role> AUTHORITIES
            = new ArrayList<Role>();

    public static Role ROLE_ADMIN = new Role(0L, "ROLE_ADMIN");
    public static Role ROLE_USER = new Role(1L,"ROLE_USER");
    public static Role ROLE_GALLERY = new Role(2L,"ROLE_GALLERY");
    public static Role ROLE_TRADER = new Role(3L,"ROLE_TRADER");
    public static Role ROLE_MALEVICH = new Role(4L,"ROLE_MALEVICH");

    static {
        AUTHORITIES.add(ROLE_ADMIN);
        AUTHORITIES.add(ROLE_USER);
        AUTHORITIES.add(ROLE_GALLERY);
        AUTHORITIES.add(ROLE_TRADER);
        AUTHORITIES.add(ROLE_MALEVICH);
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
