package com.experimental.tca.entity;

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
@Table(name = "rm_access")
public class RmAccess {
	
	@Id
	@Column(name="i_rm_access_id")
	private Integer id;

	@Column(name="vc_rm_name")
	private String roomName;

	@Column(name="i_entry_count_daily")
	private Integer entryCount;
}
