package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CommissionRuleDto {

    private Long id;

    private String name;

    private BigDecimal value;

}
