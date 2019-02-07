package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@javax.persistence.Entity
@Table(name = "person")
public class PersonEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private GenderEntity gender;

    @Getter
    @Setter
    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    // TODO crap
    @Override
    public void setId(@Nullable Long id) {
        super.setId(id);
    }

}
