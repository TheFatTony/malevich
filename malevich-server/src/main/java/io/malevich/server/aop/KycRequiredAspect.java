package io.malevich.server.aop;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.services.kyc.KycLevelService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class KycRequiredAspect {

    @Autowired
    KycLevelService kycLevelService;

    @Autowired
    ParticipantService participantService;

    @Around("@annotation(KycRequired)")
    public Object kycCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        KycRequired kyc = methodSignature.getMethod().getAnnotation(KycRequired.class);

        ParticipantEntity participantEntity = participantService.getCurrent();

        kycLevelService.checkLevel(participantEntity, kyc.level());
//        log.info("KYC level - {}", (Object) kyc.level());

        return joinPoint.proceed();
    }

}

