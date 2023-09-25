package com.experimental.tca.entity.v1;

import java.io.Serializable;

import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author star.lee
 */
@Data
@Entity
@Immutable
@Table(name = "no_tag_emp_view")
public class NoTagView implements Serializable{
	
	@Id
	@Column(name="vc_emp_uname")
	private String userName;
}
