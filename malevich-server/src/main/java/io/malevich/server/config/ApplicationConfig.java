package io.malevich.server.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Aspect;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


@Configuration
@ComponentScan(basePackages = {"io.malevich.server.*"},
        excludeFilters = {@ComponentScan.Filter(Aspect.class)})
public class ApplicationConfig {

    @Bean
    public LocaleResolver localeResolver() {
        SmartLocaleResolver localeResolver = new SmartLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    class SmartLocaleResolver extends CookieLocaleResolver {

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            String acceptLanguage = request.getHeader("Accept-Language");
            if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
                return super.determineDefaultLocale(request);
            }
            return request.getLocale();
        }

    }


}
