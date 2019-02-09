package io.malevich.server.aop;

import io.malevich.server.domain.enums.KycLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KycRequiredFor {

    KycLevel level();
}
