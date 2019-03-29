package io.malevich.server.config;


import com.yinyang.core.server.core.dto.View;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;

@ControllerAdvice
public class JsonViewConfiguration extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

        Class<?> viewClass = View.Anonymous.class;

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            if (authorities.stream().anyMatch(o -> o.getAuthority().equals(MyAuthenticationProvider.ROLE_USER.getAuthority()))) {
                viewClass = View.User.class;
            }
            if (authorities.stream().anyMatch(o -> o.getAuthority().equals(MyAuthenticationProvider.ROLE_ADMIN.getAuthority()))) {
                viewClass = View.Admin.class;
            }
            if (authorities.stream().anyMatch(o -> o.getAuthority().equals(MyAuthenticationProvider.ROLE_TRADER.getAuthority()))) {
                viewClass = View.Trader.class;
            }
            if (authorities.stream().anyMatch(o -> o.getAuthority().equals(MyAuthenticationProvider.ROLE_GALLERY.getAuthority()))) {
                    viewClass = View.Gallery.class;
            }
        }
        bodyContainer.setSerializationView(viewClass);
    }
}
