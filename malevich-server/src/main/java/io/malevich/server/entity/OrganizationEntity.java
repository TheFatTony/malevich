package io.malevich.server.entity;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "organization")
public class OrganizationEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 1000, nullable = false)
    private String fullName;

    @Column(name = "short_name", length = 1000, nullable = false)
    private String shortName;

    @Column(name = "legal_name", length = 1000, nullable = false)
    private String legalName;

    @Column(name = "phone", length = 1000, nullable = false)
    private String phone;

    @Column(name = "location", length = 1000, nullable = false)
    private String location;

    @Column(name = "legislation", length = 1000, nullable = false)
    private String legislation;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne
    private FileEntity file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLegislation() {
        return legislation;
    }

    public void setLegislation(String legislation) {
        this.legislation = legislation;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
