package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "trader")
public class TraderEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    private PersonEntity person;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.DETACH)
    private UserEntity user;

    @Getter
    @Setter
    private String mobile;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private GenderEntity gender;

    @Getter
    @Setter
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private FileEntity thumbnail;

}
