package com.experimental.tca.domain.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author star.lee
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

	private Integer infoId;
	private String infoMsg;
	private T data;
}
