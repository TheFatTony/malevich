package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@javax.persistence.Entity
@Table(name = "person")
public class PersonEntity extends AbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private GenderEntity gender;

    @Getter
    @Setter
    @Column(name = "date_of_birth")
    @NotNull
    private Timestamp dateOfBirth;

    // TODO crap
    @Override
    public void setId(@Nullable Long id) {
        super.setId(id);
    }

}
