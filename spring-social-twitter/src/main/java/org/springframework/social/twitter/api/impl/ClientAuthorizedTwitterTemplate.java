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

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * A utility implementation of an OAuth2 API binding used by TwitterTemplate to create an OAuth2
 * RestTemplate for client-authorized API requests. This is to handle the unique situation where
 * the API binding needs to use OAuth 1.0a for user requests and OAuth 2 for client requests.
 * @author Craig Walls
 */
class ClientAuthorizedTwitterTemplate extends AbstractOAuth2ApiBinding {

	public ClientAuthorizedTwitterTemplate(String clientToken) {
		super(clientToken);
	}

}
