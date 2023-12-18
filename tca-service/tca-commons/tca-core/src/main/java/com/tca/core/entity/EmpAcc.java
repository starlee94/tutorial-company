package com.tca.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author star.lee
 */
@Data
@Entity
@NoArgsConstructor
public class EmpAcc implements UserDetails {
	private Integer id;
	private String username;
	private String password;
	private Integer status;
	private Integer tagId = 0;
	private String token;
	private Boolean enabled = Boolean.TRUE;
	private Boolean accountNonExpired = Boolean.TRUE;
	private Boolean accountNonLocked = Boolean.TRUE;
	private Boolean credentialsNonExpired = Boolean.TRUE;
	private List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

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
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
