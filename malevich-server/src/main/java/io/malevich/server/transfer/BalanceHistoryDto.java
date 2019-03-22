package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BalanceHistoryDto {


    private String id;

    private Timestamp effectiveDate;

    private Double amount;

    private String operationType;

    private String transactionId;

}
