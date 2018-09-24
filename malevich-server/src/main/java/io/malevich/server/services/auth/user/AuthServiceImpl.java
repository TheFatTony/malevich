package io.malevich.server.services.auth.user;


import io.malevich.server.dao.accesstoken.AccessTokenDao;
import io.malevich.server.dao.user.UserDao;
import io.malevich.server.entity.AccessTokenEntity;
import io.malevich.server.entity.UserEntity;
import io.malevich.server.rest.util.JWTUtil;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    protected AuthServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return this.userDao.loadUserByUsername(username);
    }

    @Transactional
    public UserEntity findUserByAccessToken(String accessTokenString) {
        AccessTokenEntity accessTokenEntity = this.accessTokenDao.findByToken(accessTokenString);

        if (null == accessTokenEntity) {
            return null;
        }

        if (accessTokenEntity.isExpired()) {
            this.accessTokenDao.delete(accessTokenEntity);
            return null;
        }

        return accessTokenEntity.getUser();
    }

    @Transactional
    public AccessTokenEntity createAccessToken(UserEntity user) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(user, jwtUtil.generateToken(user.getUsername()));
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
        return new UserDto(userDetails.getUsername(), null, this.createRoleMap(userDetails));
    }

    @Override
    @Transactional
    public AccessTokenDto authenticate(LoginFormDto loginFormDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginFormDto.getUsername(), loginFormDto.getPassword());
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
