package io.malevich.server.entity;

import io.malevich.server.entity.enums.Role;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "person")
public class PersonEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name", length = 1000, nullable = false)
    private String fullName;

    @Column(name = "postal_address", length = 1000, nullable = false)
    private String postalAddress;

    @Column(name = "billing_address", length = 1000, nullable = false)
    private String billingAddress;

    @Column(name = "document_type", length = 1000, nullable = false)
    private String documentType;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> userRoleId;

    @Column(name = "document_number", length = 1000, nullable = false)
    private String documentNumber;

    @Column(name = "registration_flag", nullable = false)
    private boolean registrationFlag;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Set<Role> getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Set<Role> userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public boolean isRegistrationFlag() {
        return registrationFlag;
    }

    public void setRegistrationFlag(boolean registrationFlag) {
        this.registrationFlag = registrationFlag;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
