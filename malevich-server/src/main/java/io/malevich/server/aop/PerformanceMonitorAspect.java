package io.malevich.server.aop;

import io.malevich.server.core.aop.PerformanceMonitor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class PerformanceMonitorAspect {

    @Around("execution(public * io.malevich.server.rest.resources.**.*(..))")
    public Object logResources(ProceedingJoinPoint joinPoint) throws Throwable {
        return PerformanceMonitor.logAccess(joinPoint);
    }

    @Around("execution(public * io.malevich.server.services.**..*(..))")
    public Object logServices(ProceedingJoinPoint joinPoint) throws Throwable {
        return PerformanceMonitor.logPerformance(joinPoint);
    }

}
