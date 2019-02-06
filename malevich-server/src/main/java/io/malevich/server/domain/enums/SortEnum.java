package io.malevich.server.domain.enums;

public enum SortEnum {
    NAME("NAME"), PHTL("PHTL"), PLTH("PLTH");

    private String value;

    SortEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}

