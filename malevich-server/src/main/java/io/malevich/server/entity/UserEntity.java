package io.malevich.server.entity;

import io.malevich.server.entity.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity(name = "user")
@Table(name = "user")
public class UserEntity implements Entity, UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 255, nullable = false)
	private String name;

	@Column(length = 255, nullable = false)
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	@ManyToOne
	@JoinColumn(name = "user_type_id", referencedColumnName = "id")
	private UserTypeEntity userType;

	@Column(name = "activity_flag")
	private boolean activityFlag;

	protected UserEntity() {
	}

	public UserEntity(String name, String passwordHash) {
		this.name = name;
		this.password = passwordHash;
	}

	public UserEntity(String name, String password, Set<Role> roles, UserTypeEntity userType, boolean activityFlag) {
		this.name = name;
		this.password = password;
		this.roles = roles;
		this.userType = userType;
		this.activityFlag = activityFlag;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(boolean activityFlag) {
		this.activityFlag = activityFlag;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles();
	}

	public UserTypeEntity getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEntity userTypeEntity) {
		this.userType = userTypeEntity;
	}

	public String getUsername() {
		return this.name;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
