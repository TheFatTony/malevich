package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentsDto {


    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private java.sql.Timestamp effectiveDate;

    private PaymentMethodDto paymentMethod;

    private Double amount;

    private PaymentTypeDto paymentType;

    private String transactionId;

}
