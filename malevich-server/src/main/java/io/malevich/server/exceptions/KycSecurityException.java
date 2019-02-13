package io.malevich.server.exceptions;

import io.malevich.server.domain.enums.KycLevel;
import lombok.Getter;

@Getter
public class KycSecurityException extends RuntimeException {


    private KycLevel required;
    private KycLevel current;
    private String[] requiredFields;

    public KycSecurityException(KycLevel required, KycLevel current, String[] requiredFields) {

        this.required = required;
        this.current = current;
        this.requiredFields = requiredFields;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder("KYC level ")
                .append(required)
                .append(" required but was ")
                .append(current);

        return sb.toString();
    }

}
