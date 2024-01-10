package com.experimental.tca.entity.v1;

import javax.persistence.*;

import com.experimental.tca.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author star.lee
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="token")
	private String tokenString;

	@Enumerated(EnumType.STRING)
	private TokenType tokenType;
	
	private boolean revoked;
	
	@ManyToOne
	@JoinColumn(name = "i_emp_id")
	private EmpAcc empAcc;
	
	
}
