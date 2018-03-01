package com.datawalk.hashcode.mappers;

import org.apache.flink.api.common.functions.MapFunction;

public class InputMapper implements MapFunction<String, String> {

	private static final long serialVersionUID = -5001568268992166643L;

	@Override
	public String map(String value) throws Exception {
		System.out.println(value);
		System.out.println(value.length());
		return value;
	}

}
