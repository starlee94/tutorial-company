package com.experimental.tca.model;

import java.io.Serializable;

import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Immutable
@Table(name = "no_tag_emp_view")
public class NoTagView implements Serializable{
	
	@Id
	private String vc_emp_uname;
}
