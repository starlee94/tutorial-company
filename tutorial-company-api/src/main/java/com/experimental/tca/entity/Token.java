package com.experimental.tca.entity;

import javax.persistence.*;

import com.experimental.tca.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String token;
	
	@Enumerated(EnumType.STRING)
	private TokenType tokenType;
	
	private boolean revoked;
	
	@ManyToOne
	@JoinColumn(name = "i_emp_id")
	private EmpAcc empAcc;
	
	
}
