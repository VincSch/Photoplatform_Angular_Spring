package de.htw.sdf.photoplatform.persistence;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a user representing the corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_USER")
public class User extends BaseAuditEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3719799602561353931L;

	public User() {
		super();
	}

	@Column(name = "NAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "BIRTHDAY")
	private String birthday;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "CITY")
	private String city;

	@Column(name = "SECTOKEN")
	private String secToken;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<UserRole> userRoles;

	@Column(name = "IS_ACCOUNT_NON_LOCKED")
	private boolean accountNonLocked;

	@Column(name = "IS_ENABLED")
	private boolean enabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<String> roles = new HashSet<String>();
		for (UserRole userRole : getUserRoles()) {
			roles.add(userRole.getRole().getName());
		}

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getSecToken() {
		return secToken;
	}

	public void setSecToken(String secToken) {
		this.secToken = secToken;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRole(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setUserName(String name) {
		this.username = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.accountNonLocked = isAccountNonLocked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
