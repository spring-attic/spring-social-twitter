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

import java.util.List;

import org.springframework.social.twitter.api.DirectMessage;
import org.springframework.social.twitter.api.DirectMessageOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link DirectMessageOperations}, providing a binding to Twitter's direct message-oriented REST resources.
 * @author Craig Walls
 */
class DirectMessageTemplate extends AbstractTwitterOperations implements DirectMessageOperations {
	
	private final RestTemplate restTemplate;

	public DirectMessageTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
		super(isAuthorizedForUser, isAuthorizedForApp);
		this.restTemplate = restTemplate;
	}

	public List<DirectMessage> getDirectMessagesReceived() {
		return getDirectMessagesReceived(1, 20, 0, 0);
	}

	public List<DirectMessage> getDirectMessagesReceived(int page, int pageSize) {
		return getDirectMessagesReceived(page, pageSize, 0, 0);
	}

	public List<DirectMessage> getDirectMessagesReceived(int page, int pageSize, long sinceId, long maxId) {
		requireUserAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		return restTemplate.getForObject(buildUri("direct_messages.json", parameters), DirectMessageList.class);
	}

	public List<DirectMessage> getDirectMessagesSent() {
		return getDirectMessagesSent(1, 20, 0, 0);
	}

	public List<DirectMessage> getDirectMessagesSent(int page, int pageSize) {
		return getDirectMessagesSent(page, pageSize, 0, 0);
	}

	public List<DirectMessage> getDirectMessagesSent(int page, int pageSize, long sinceId, long maxId) {
		requireUserAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		return restTemplate.getForObject(buildUri("direct_messages/sent.json", parameters), DirectMessageList.class);
	}
	
	public DirectMessage getDirectMessage(long id) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("direct_messages/show.json", "id", String.valueOf(id)), DirectMessage.class);
	}

	public DirectMessage sendDirectMessage(String toScreenName, String text) {
		requireUserAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.add("screen_name", String.valueOf(toScreenName));
		data.add("text", text);
		return restTemplate.postForObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
	}

	public DirectMessage sendDirectMessage(long toUserId, String text) {
		requireUserAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.add("user_id", String.valueOf(toUserId));
		data.add("text", text);
		return restTemplate.postForObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
	}

	public void deleteDirectMessage(long messageId) {
		requireUserAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.add("id", String.valueOf(messageId));
		restTemplate.postForObject(buildUri("direct_messages/destroy.json"), data, DirectMessage.class);
	}
	
}
