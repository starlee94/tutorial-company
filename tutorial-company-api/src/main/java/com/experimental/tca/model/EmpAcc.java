package com.experimental.tca.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
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
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emp_acc")
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "api_create_emp_acc", procedureName = "api_create_emp_acc", parameters =
		{
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "emp_id", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "uname", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "pword", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "action_timestamp", type = Timestamp.class)
		}),
	@NamedStoredProcedureQuery(name = "api_update_emp_acc_tag", procedureName = "api_update_emp_acc_tag", parameters =
		{
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "emp_id", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "uname", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "tagname", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "action_timestamp", type = Timestamp.class)
		})
})
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
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(role.name()));
	}


	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}


	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}


	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
