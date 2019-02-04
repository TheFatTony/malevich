package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExchangeOrderDto {

    private long id;

    private PaymentMethodSuperDto paymentMethod;

    private String exchangeName;

    private String internalStatus;

    private String type;

    private double originalAmount;

    private String currencyPair;

    private String orderId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private java.sql.Timestamp timestamp;

    private String status;

    private double cumulativeAmount;

    private double averagePrice;

    private double fee;

    private String leverage;

}
