package io.malevich.server.fabric.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Counetrparty {

    private String $class;

    private String counetrpartyId;

    private String systemCode;

    public Counetrparty(String counetrpartyId, String systemCode) {
        this.$class = "io.malevich.network.Counetrparty";
        this.counetrpartyId = counetrpartyId;
        this.systemCode = systemCode;

    }
}
