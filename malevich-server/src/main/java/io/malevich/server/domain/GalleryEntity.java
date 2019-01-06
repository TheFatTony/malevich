package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "gallery")
@Inheritance(strategy = InheritanceType.JOINED)
public class GalleryEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private FileEntity thumbnail;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private FileEntity image;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;

    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "gallery_address",
            joinColumns = @JoinColumn(name = "gallery_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<AddressEntity> addresses;

    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "gallery_user",
            joinColumns = @JoinColumn(name = "gallery_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    // TODO to Participant discussion
    private List<UserEntity> users;


}
