package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "artwork")
public class Artwork implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "language")
    @Getter @Setter
    private String language;

    @Column(name = "title")
    @Getter @Setter
    private String title;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    @Column(name = "price")
    @Getter @Setter
    private double price;

}
