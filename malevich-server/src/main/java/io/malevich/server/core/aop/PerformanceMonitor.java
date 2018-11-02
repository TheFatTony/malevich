package io.malevich.server.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Slf4j
public class PerformanceMonitor {

    public static Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        final String name = joinPoint.getSignature().toString();
        // log.info("Method " + name + " execution started at:" + new Date());
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            // log.info("Method " + name + " execution lasted:" + time + " ms");
            // log.info("Method " + name + " execution ended at:" + new Date());
            if (time > 250) {
                log.warn("Method " + name + " execution longer than 250 ms!");
            }
        }
    }

    public static Object logAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails)
            userDetails = (UserDetails) principal;
        String userName = null;
        if (userDetails == null)
            userName = "anonymous";
        else
            userName = userDetails.getUsername();

        final String name = joinPoint.getSignature().toString();
        try {
            return joinPoint.proceed();
        } finally {
            log.info("User " + userName + " executed method " + name + " at: " + new Date());
        }
    }

}
