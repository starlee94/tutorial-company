package com.experimental.tca.domain.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request<T> {

	private T data;
}


