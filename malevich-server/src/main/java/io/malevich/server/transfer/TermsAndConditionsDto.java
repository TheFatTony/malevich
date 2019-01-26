package io.malevich.server.transfer;


import com.yinyang.core.server.transfer.UserTypeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermsAndConditionsDto {

    private String htmlText;

    private UserTypeDto userType;

}
