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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.Test;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;


/**
 * @author Craig Walls
 */
public class BlockTemplateTest extends AbstractTwitterApiTest {
	
	@Test
	public void block_userId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/create.json"))
			.andExpect(method(POST))
			.andExpect(content().string("user_id=12345"))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile blockedUser = twitter.blockOperations().block(12345);
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}

	@Test
	public void block_screenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/create.json"))
			.andExpect(method(POST))
			.andExpect(content().string("screen_name=habuma"))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile blockedUser = twitter.blockOperations().block("habuma");
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}	

	@Test
	public void unblock_userId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/destroy.json"))
			.andExpect(method(POST))
			.andExpect(content().string("user_id=12345"))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile blockedUser = twitter.blockOperations().unblock(12345);
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}
	
	@Test
	public void unblock_screenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/destroy.json"))
			.andExpect(method(POST))
			.andExpect(content().string("screen_name=habuma"))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile blockedUser = twitter.blockOperations().unblock("habuma");
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}	
	
	@Test
	public void getBlockedUsers() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/list.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		CursoredList<TwitterProfile> blockedUsers = twitter.blockOperations().getBlockedUsers();
		assertBlockedUsers(blockedUsers);
	}
	
	@Test
	public void getBlockedUsersInCursor() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/list.json?cursor=332211"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		CursoredList<TwitterProfile> blockedUsers = twitter.blockOperations().getBlockedUsersInCursor(332211);
		assertBlockedUsers(blockedUsers);
	}

	@Test
	public void getBlockedUserIds() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("blocked-user-ids"), APPLICATION_JSON));
		CursoredList<Long> blockedUsers = twitter.blockOperations().getBlockedUserIds();
		assertEquals(2, blockedUsers.size());
		mockServer.verify();
	}
	
	@Test
	public void getBlockedUserIdsInCursor() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/blocks/ids.json?cursor=332211"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("blocked-user-ids"), APPLICATION_JSON));
		CursoredList<Long> blockedUsers = twitter.blockOperations().getBlockedUserIdsInCursor(332211);
		assertEquals(2, blockedUsers.size());
		mockServer.verify();
	}
	
	// private helpers
	
	private void assertTwitterProfile(TwitterProfile blockedUser) {
		assertEquals(161064614, blockedUser.getId());
		assertEquals("artnames", blockedUser.getScreenName());
		assertEquals("Art Names", blockedUser.getName());
		assertEquals("I'm just a normal kinda guy", blockedUser.getDescription());
		assertEquals("Denton, TX", blockedUser.getLocation());
		assertEquals("http://www.springsource.org", blockedUser.getUrl());
		assertEquals("http://a1.twimg.com/sticky/default_profile_images/default_profile_4_normal.png", blockedUser.getProfileImageUrl());
	}
	
	private void assertBlockedUsers(CursoredList<TwitterProfile> friends) {
		assertEquals(2, friends.size());
		assertEquals("royclarkson", friends.get(0).getScreenName());
		assertEquals("kdonald", friends.get(1).getScreenName());
		assertEquals(112233, friends.getPreviousCursor());
		assertEquals(332211, friends.getNextCursor());
	}

}
