package io.malevich.server.entity;

import io.malevich.server.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;
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
	@Getter @Setter
	private Long id;

	@Column(name = "name")
	@Getter @Setter
	private String name;

	@Column(name = "password")
    @Setter
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	@Getter @Setter
	private Set<Role> roles = new HashSet<Role>();

	@ManyToOne
	@JoinColumn(name = "user_type_id", referencedColumnName = "id")
	@Getter @Setter
	private UserTypeEntity userType;

	@Column(name = "activity_flag")
	@Getter @Setter
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


	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
