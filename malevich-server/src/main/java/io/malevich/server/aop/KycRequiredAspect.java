package io.malevich.server.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Aspect
@Component
@Slf4j
public class KycRequiredAspect {

    @Around("@annotation(KycRequired)")
    public Object kycCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        KycRequired kyc = methodSignature.getMethod().getAnnotation(KycRequired.class);
        log.info("KYC level - " + kyc.level());
        return joinPoint.proceed();
    }

}

