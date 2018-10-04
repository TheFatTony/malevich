package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "register_token")
public class RegisterTokenEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "token")
    private String token;

    @Getter
    @Setter
    @Column(name = "user_name")
    private String userName;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne()
    private UserTypeEntity userType;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;


}
