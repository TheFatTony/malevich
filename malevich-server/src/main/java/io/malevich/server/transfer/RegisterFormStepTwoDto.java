package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterFormStepTwoDto {

    private String password;

    private Boolean isOrganization;

    private Boolean isGallery;

}
