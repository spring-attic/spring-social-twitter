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
package org.springframework.social.twitter.api;

/**
 * A stream event warning that the client is stalling and is in danger of being disconnected.
 * @author Craig Walls
 */
public class StreamWarningEvent extends TwitterObject {

	private final String code;
	
	private final String message;
	
	private final double percentFull;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public double getPercentFull() {
		return percentFull;
	}

	public StreamWarningEvent(String code, String message, double percentFull) {
		this.code = code;
		this.message = message;
		this.percentFull = percentFull;
	}
	
}
