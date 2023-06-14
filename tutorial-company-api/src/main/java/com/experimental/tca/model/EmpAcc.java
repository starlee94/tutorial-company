package com.experimental.tca.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emp_acc")
public class EmpAcc implements UserDetails{

	@Id
	@Column(name = "i_emp_id")
	private Integer id;
	@Column(name = "vc_emp_uname")
	private String username;
	@Column(name = "vc_emp_pword")
	private String password;
	@Column(name = "i_emp_status")
	private Integer status;
	@Column(name = "i_tag_id")
	private Integer tagId;
	
	@Transient
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;

	@Transient
	@OneToMany(mappedBy = "empAcc")
	private List<Token> tokens = null ;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
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
