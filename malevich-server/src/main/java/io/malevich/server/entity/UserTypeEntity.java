package io.malevich.server.entity;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "user_type")
public class UserTypeEntity implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type_name", length = 1000, nullable = false)
    private String typeName;

    @Column(name = "description", length = 4000, nullable = true)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
