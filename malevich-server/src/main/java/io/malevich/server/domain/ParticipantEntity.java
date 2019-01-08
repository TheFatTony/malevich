package io.malevich.server.domain;

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


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "participant")
@Inheritance(strategy = InheritanceType.JOINED)
public class ParticipantEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private ParticipantTypeEntity type;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private CountryEntity country;

    @Getter
    @Setter
    @Column(name = "phone_number")
    private String phoneNumber;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private FileEntity thumbnail;

    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "participant_address",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<AddressEntity> addresses;

    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "participant_user",
            joinColumns = @JoinColumn(name = "participant_id", updatable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", updatable = false)
//            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"})
    )
    private List<UserEntity> users;

    @Transient
    public UserEntity getUser() {
        List<UserEntity> usersList = getUsers();
        return usersList != null && !usersList.isEmpty() ? usersList.get(0) : null;
    }

}
