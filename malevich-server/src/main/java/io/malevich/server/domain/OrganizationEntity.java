package io.malevich.server.domain;

import io.malevich.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "organization")
public class OrganizationEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "legal_name")
    private String legalName;

    @Getter
    @Setter
    @Column(name = "phone_number")
    private String phoneNumber;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "legal_name_ml")
    private Map<String, String> legalNameMl;

}
