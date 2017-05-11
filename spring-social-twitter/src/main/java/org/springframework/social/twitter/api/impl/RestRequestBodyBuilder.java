package org.springframework.social.twitter.api.impl;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RestRequestBodyBuilder {
	private final MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	
	public RestRequestBodyBuilder withField(String name, Object value) {
		this.map.set(name, value);
		return this;
	}
	
	public RestRequestBodyBuilder withFields(MultiValueMap<String, Object> fields) {
		this.map.putAll(fields);
		return this;
	}
	
	public MultiValueMap<String, Object> build() {
		return this.map;
	}
}
