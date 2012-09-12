/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import static org.springframework.test.web.client.match.RequestMatchers.*;
import static org.springframework.test.web.client.response.ResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.twitter.api.TwitterProfile;


/**
 * @author Craig Walls
 */
public class BlockTemplateTest extends AbstractTwitterApiTest {
	
	@Test
	public void block_userId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/create.json"))
			.andExpect(method(POST))
			.andExpect(body("user_id=12345"))
			.andRespond(withResponse(jsonResource("twitter-profile"), responseHeaders));
		TwitterProfile blockedUser = twitter.blockOperations().block(12345);
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void block_userId_unauthorized() {
		unauthorizedTwitter.blockOperations().block(12345);
	}

	@Test
	public void block_screenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/create.json"))
			.andExpect(method(POST))
			.andExpect(body("screen_name=habuma"))
			.andRespond(withResponse(jsonResource("twitter-profile"), responseHeaders));
		TwitterProfile blockedUser = twitter.blockOperations().block("habuma");
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}	

	@Test(expected = NotAuthorizedException.class)
	public void block_screenName_unauthorized() {
		unauthorizedTwitter.blockOperations().block("habuma");
	}

	@Test
	public void unblock_userId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/destroy.json"))
			.andExpect(method(POST))
			.andExpect(body("user_id=12345"))
			.andRespond(withResponse(jsonResource("twitter-profile"), responseHeaders));
		TwitterProfile blockedUser = twitter.blockOperations().unblock(12345);
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void unblock_userId_unauthorized() {
		unauthorizedTwitter.blockOperations().unblock(12345);
	}

	@Test
	public void unblock_screenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/destroy.json"))
			.andExpect(method(POST))
			.andExpect(body("screen_name=habuma"))
			.andRespond(withResponse(jsonResource("twitter-profile"), responseHeaders));
		TwitterProfile blockedUser = twitter.blockOperations().unblock("habuma");
		assertTwitterProfile(blockedUser);
		mockServer.verify();
	}	
	
	@Test(expected = NotAuthorizedException.class)
	public void unblock_screenName_unauthorized() {
		unauthorizedTwitter.blockOperations().unblock("habuma");
	}

	@Test
	public void getBlockedUsers() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/blocking.json?page=1&per_page=20"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("list-of-profiles"), responseHeaders));
		List<TwitterProfile> blockedUsers = twitter.blockOperations().getBlockedUsers();
		assertEquals(2, blockedUsers.size());
		mockServer.verify();
	}

	@Test
	public void getBlockedUsers_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/blocking.json?page=3&per_page=25"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("list-of-profiles"), responseHeaders));
		List<TwitterProfile> blockedUsers = twitter.blockOperations().getBlockedUsers(3, 25);
		assertEquals(2, blockedUsers.size());
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void getBlockedUsers_unauthorized() {
		unauthorizedTwitter.blockOperations().getBlockedUsers();
	}

	@Test
	public void getBlockedUserIds() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/blocking/ids.json"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("list-of-ids"), responseHeaders));
		List<Long> blockedUsers = twitter.blockOperations().getBlockedUserIds();
		assertEquals(4, blockedUsers.size());
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getBlockedUserIds_unauthorized() {
		unauthorizedTwitter.blockOperations().getBlockedUserIds();
	}
	
	@Test
	public void isBlocking_userId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/exists.json?user_id=12345"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("twitter-profile"), responseHeaders));
		assertTrue(twitter.blockOperations().isBlocking(12345));		
	}

	@Test
	public void isBlocking_userId_false() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/exists.json?user_id=12345"))
			.andExpect(method(GET))
			.andRespond(withResponse("{}", responseHeaders, HttpStatus.NOT_FOUND, "Not Found"));
		assertFalse(twitter.blockOperations().isBlocking(12345));		
	}

	@Test
	public void isBlocking_screenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/exists.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("twitter-profile"), responseHeaders));
		assertTrue(twitter.blockOperations().isBlocking("habuma"));		
	}

	@Test
	public void isBlocking_screenName_false() {
		mockServer.expect(requestTo("https://api.twitter.com/1/blocks/exists.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withResponse("{}", responseHeaders, HttpStatus.NOT_FOUND, "Not Found"));
		assertFalse(twitter.blockOperations().isBlocking("habuma"));		
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

}
