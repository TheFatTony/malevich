package io.malevich.server.domain.enums;

public enum ExchangeOrderStatus {
    SUBMITTED("SUBMITTED"), EXECUTED("EXECUTED");

    private String value;

    ExchangeOrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
