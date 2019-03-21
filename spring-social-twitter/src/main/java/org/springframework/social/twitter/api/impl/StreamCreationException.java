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

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
class StreamCreationException extends Exception {
	
	private HttpStatus httpStatus;

	public StreamCreationException(String message, HttpStatus httpStatus) {
		super(message + "; HTTP status: " + httpStatus);
		this.httpStatus = httpStatus;
	}
	
	public StreamCreationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
