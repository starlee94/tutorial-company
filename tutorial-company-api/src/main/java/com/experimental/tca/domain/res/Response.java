package com.experimental.tca.domain.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

	private String infoId;
	private String infoMsg;
	private T data;
}
