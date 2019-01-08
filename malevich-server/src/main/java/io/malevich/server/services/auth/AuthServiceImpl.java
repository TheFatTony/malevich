package io.malevich.server.services.auth;


import com.yinyang.core.server.core.security.JWTUtil;
import io.malevich.server.domain.*;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    protected AuthServiceImpl() {
    }


    public AccessTokenEntity createAccessToken(UserEntity user) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(user, jwtUtil.generateToken(user.getUsername()));
//        accessTokenDao.save(accessTokenEntity);
        return accessTokenEntity;
    }

    @Override
    @Transactional
    public UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) principal;
        } catch (ClassCastException e) {
            return null;
        }
        return new UserDto(userDetails.getUsername(), this.createRoleMap(userDetails));
    }

    @Override
    public AccessTokenDto authenticate(LoginFormDto loginFormDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginFormDto.getUsername(), loginFormDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        return new AccessTokenDto(this.createAccessToken((UserEntity) authentication.getPrincipal()).getToken());
    }




    private List<String> createRoleMap(UserDetails userDetails) {
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

}
