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

import org.springframework.social.OperationNotPermittedException;

/**
 * Exception indicating that an attempt was made to post a status or send a direct message where the length exceeds Twitter's 140 character limit.
 * @author Craig Walls
 */
@SuppressWarnings("serial")
public class MessageTooLongException extends OperationNotPermittedException {

	public MessageTooLongException(String message) {
		super("twitter", message);
	}
	
}
