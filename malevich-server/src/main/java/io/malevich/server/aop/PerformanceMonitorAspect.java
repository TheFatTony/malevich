package io.malevich.server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class PerformanceMonitorAspect {

    @Around("execution(public * io.malevich.server.rest.resources.**.*(..))")
    public Object logResources(ProceedingJoinPoint joinPoint) throws Throwable {
        return logFunc(joinPoint);
    }

    @Around("execution(public * io.malevich.server.services.**..*(..))")
    public Object logServices(ProceedingJoinPoint joinPoint) throws Throwable {
        return logFunc(joinPoint);
    }

    private Object logFunc(ProceedingJoinPoint joinPoint) throws Throwable {
        final Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        final String name = joinPoint.getSignature().toString();
        // log.info("Method " + name + " execution started at:" + new Date());
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            log.info("Method " + name + " execution lasted:" + time + " ms");
            // log.info("Method " + name + " execution ended at:" + new Date());
            if (time > 250) {
                log.warn("Method " + name + " execution longer than 250 ms!");
            }
        }
    }

}
