/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;

/**
 * Custom FormHttpMessageConverter that meets Twitter's non-RFC1738 escaping of asterisks ('*').
 * @author Craig Walls
 */
class TwitterEscapingFormHttpMessageConverter extends FormHttpMessageConverter {

	TwitterEscapingFormHttpMessageConverter() {
		setCharset(Charset.forName("UTF-8"));
		List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();
		partConverters.add(new ByteArrayHttpMessageConverter());
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		partConverters.add(stringHttpMessageConverter);
		partConverters.add(new ResourceHttpMessageConverter());
		setPartConverters(partConverters);
	}
	
	
	@Override
	public void write(MultiValueMap<String, ?> map, MediaType contentType, HttpOutputMessage outputMessage) 
			throws IOException, HttpMessageNotWritableException {
		// only do special escaping if this is a non-multipart request
		if (!isMultipart(map, contentType)) {
			super.write(map, contentType, new TwitterEscapingHttpOutputMessage(outputMessage));
		} else {
			super.write(map, contentType, outputMessage);
		}
	}
	
	private static class TwitterEscapingHttpOutputMessage implements HttpOutputMessage {
		private HttpOutputMessage target;

		public TwitterEscapingHttpOutputMessage(HttpOutputMessage target) {
			this.target = target;
		}
		
		public HttpHeaders getHeaders() {
			return target.getHeaders();
		}

		public OutputStream getBody() throws IOException {
			return new TwitterEscapingOutputStream(target.getBody());
		}
	}

	private static class TwitterEscapingOutputStream extends OutputStream {
		private OutputStream target;

		public TwitterEscapingOutputStream(OutputStream target) {
			this.target = target;
		}
		
		@Override
		public void write(int b) throws IOException {
			// If more exceptions to URL encoding are found, this can be made to be mode clever than a bunch of
			// else-if blocks. But until then, this is sufficient.
			if (b == '*') {
				target.write('%');
				target.write('2');
				target.write('A');
			} else {
				target.write(b);
			}
		}
	}

	// "borrowed" from FormHttpMessageConverter
	private boolean isMultipart(MultiValueMap<String, ?> map, MediaType contentType) {
		if (contentType != null) {
			return MediaType.MULTIPART_FORM_DATA.equals(contentType);
		}
		for (String name : map.keySet()) {
			for (Object value : map.get(name)) {
				if (value != null && !(value instanceof String)) {
					return true;
				}
			}
		}
		return false;
	}

}

