package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactUsDto {


    private Long id;

    private String emailId;

    private String name;

    private String message;

    private String subject;

    private String phone;

}
