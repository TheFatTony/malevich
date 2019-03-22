package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceHistoryAsset {

    private String id;

    private Timestamp effectiveDate;

    private Double amount;

    private String operationType;

    private String transactionId;

    private String counterparty;

}
