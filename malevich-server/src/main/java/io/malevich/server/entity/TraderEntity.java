package io.malevich.server.entity;


import io.malevich.server.entity.utils.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;

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
    @OneToOne
    private PersonEntity person;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne
    private UserEntity user;

    @Getter
    @Setter
    @Column(name = "mobile")
    private String mobile;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private FileEntity thumbnail;

}
