package org.springframework.social.twitter.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

public class TwitterApiBuilderForHttpEntity<TData> {
	private final TData data;
	private boolean multipart = false;
	Map<String, Collection<String>> headers = new HashMap<String, Collection<String>>();

	public TwitterApiBuilderForHttpEntity(TData data) {
		this.data = data;
	}

	public TwitterApiBuilderForHttpEntity<TData> addHeader(String key, Collection<String> values) {
		this.headers.put(key, values);
		return this;
	}

	public TwitterApiBuilderForHttpEntity<TData> multipart(boolean multipart) {
		this.multipart = multipart;
		return this;
	}

	public HttpEntity<?> build() {
		return new HttpEntity<>(
				this.data,
				this.makeHeaders());
	}

	private HttpHeaders makeHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		for(Entry<String, Collection<String>> header: headers.entrySet()) {
			httpHeaders.add(header.getKey(), StringUtils.collectionToDelimitedString(header.getValue(), ";"));
		}

		if(null == httpHeaders.getContentType()) {
			if(this.multipart) {
				httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			} else {
				httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			}
		}

		return httpHeaders;
	}
}
