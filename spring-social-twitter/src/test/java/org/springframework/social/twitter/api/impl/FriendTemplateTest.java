/*
 * Copyright 2013 the original author or authors.
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
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;


/**
 * @author Craig Walls
 */
public class FriendTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void getFriends_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriendsInCursor_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=987654321"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriendsInCursor(987654321);
		assertFriendsFollowers(friends);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFriends_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFriends();
	}
	
	@Test
	public void getFriends_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends(98765L);
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriendsInCursor_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=987654321&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriendsInCursor(98765L, 987654321);
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriends_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=-1&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends("habuma");
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriendsInCursor_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=987654321&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriendsInCursor("habuma", 987654321);
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriends_currentUser_noFriends() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("no-friend-or-follower-ids"), APPLICATION_JSON));

		List<TwitterProfile> friends = twitter.friendOperations().getFriends();
		assertEquals(0, friends.size());
	}

	@Test
	public void getFriends_currentUser_manyFriends() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/list.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriendIds_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> friendIds = twitter.friendOperations().getFriendIds();
		assertFriendFollowerIdsList(friendIds);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFriendIds_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFriendIds();
	}

	@Test
	public void getFriendIdsInCursor_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=123456"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> friendIds = twitter.friendOperations().getFriendIdsInCursor(123456);
		assertFriendFollowerIdsList(friendIds);
	}

	@Test
	public void getFriendIds_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> friendIds = twitter.friendOperations().getFriendIds(98765L);
		assertFriendFollowerIdsList(friendIds);
	}

	@Test
	public void getFriendIdsInCursor_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=123456&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> friendIds = twitter.friendOperations().getFriendIdsInCursor(98765L, 123456);
		assertFriendFollowerIdsList(friendIds);
	}

	@Test
	public void getFriendIds_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> friendIds = twitter.friendOperations().getFriendIds("habuma");
		assertFriendFollowerIdsList(friendIds);
	}

	@Test
	public void getFriendIdsInCursor_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=123456&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> friendIds = twitter.friendOperations().getFriendIdsInCursor("habuma", 123456);
		assertFriendFollowerIdsList(friendIds);
	}

	@Test 
	public void getFollowers_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowers();
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test 
	public void getFollowersInCursor_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=24680"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowersInCursor(24680);
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFollowers_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowers();
	}

	@Test 
	public void getFollowers_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowers(98765L);
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test 
	public void getFollowersInCursor_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=13579&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowersInCursor(98765L, 13579);
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getFollowersInCursor_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowersInCursor(98765L,13579);
	}

	@Test 
	public void getFollowers_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=-1&screen_name=oizik"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowers("oizik");
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test 
	public void getFollowersInCursor_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=12357&screen_name=oizik"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowersInCursor("oizik", 12357);
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getFollowersInCursor_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowersInCursor("oizik",12357);
	}

	@Test
	public void getFriends_currentUser_noFollowers() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("no-friend-or-follower-ids"), APPLICATION_JSON));

		List<TwitterProfile> friends = twitter.friendOperations().getFollowers();
		assertEquals(0, friends.size());
	}

	@Test
	public void getFollowers_currentUser_manyFollowers() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/list.json?cursor=24680"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowersInCursor(24680);
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test
	public void getFollowerIds_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> followerIds = twitter.friendOperations().getFollowerIds();
		assertFriendFollowerIdsList(followerIds);
	}

	@Test
	public void getFollowerIdsInCursor_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=24680"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> followerIds = twitter.friendOperations().getFollowerIdsInCursor(24680);
		assertFriendFollowerIdsList(followerIds);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFollowerIds_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowerIds();
	}

	@Test
	public void getFollowerIds_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> followerIds = twitter.friendOperations().getFollowerIds(98765L);
		assertFriendFollowerIdsList(followerIds);
	}

	@Test
	public void getFollowerIdsInCursor_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=24680&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> followerIds = twitter.friendOperations().getFollowerIdsInCursor(98765L, 24680);
		assertFriendFollowerIdsList(followerIds);
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getFollowerIdsInCursor_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowerIdsInCursor(98765L, 24680);
	}

	@Test
	public void getFollowerIds_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> followerIds = twitter.friendOperations().getFollowerIds("habuma");
		assertFriendFollowerIdsList(followerIds);
	}

	@Test
	public void getFollowerIdsInCursor_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=24680&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		
		CursoredList<Long> followerIds = twitter.friendOperations().getFollowerIdsInCursor("habuma", 24680);
		assertFriendFollowerIdsList(followerIds);
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getFollowerIdsInCursor_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().getFollowerIdsInCursor("habuma",24680);
	}

	@Test
	public void follow_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/create.json?user_id=98765"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("follow"), APPLICATION_JSON));
		
		String followedScreenName = twitter.friendOperations().follow(98765);
		assertEquals("oizik2", followedScreenName);
		
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void follow_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().follow(98765);
	}
	
	@Test
	public void follow_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/create.json?screen_name=oizik2"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("follow"), APPLICATION_JSON));
		String followedScreenName = twitter.friendOperations().follow("oizik2");
		assertEquals("oizik2", followedScreenName);

		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void follow_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().follow("aizik2");
	}
	
	@Test
	public void unfollow_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/destroy.json?user_id=98765"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("unfollow"), APPLICATION_JSON));
		String unFollowedScreenName = twitter.friendOperations().unfollow(98765);
		assertEquals("oizik2", unFollowedScreenName);
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void unfollow_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().unfollow(98765);
	}

	@Test
	public void unfollow_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/destroy.json?screen_name=oizik2"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("unfollow"), APPLICATION_JSON));

		String unFollowedScreenName = twitter.friendOperations().unfollow("oizik2");
		assertEquals("oizik2", unFollowedScreenName);
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void unfollow_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().follow("aizik2");
	}

	@Test
	public void enableNotifications_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/update.json?user_id=98765&device=true"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("follow"), APPLICATION_JSON));
		TwitterProfile unFollowedUser = twitter.friendOperations().enableNotifications(98765);
		assertEquals("oizik2", unFollowedUser.getScreenName());
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void enableNotifications_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().enableNotifications(98765);
	}
	
	@Test
	public void enableNotifications_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/update.json?screen_name=oizik2&device=true"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("follow"), APPLICATION_JSON));
		TwitterProfile unFollowedUser = twitter.friendOperations().enableNotifications("oizik2");
		assertEquals("oizik2", unFollowedUser.getScreenName());
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void enableNotifications_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().enableNotifications("oizik2");
	}

	@Test
	public void disableNotifications_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/update.json?user_id=98765&device=false"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("unfollow"), APPLICATION_JSON));
		TwitterProfile unFollowedUser = twitter.friendOperations().disableNotifications(98765);
		assertEquals("oizik2", unFollowedUser.getScreenName());
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void disableNotifications_byUserId_unauthorized() {
		unauthorizedTwitter.friendOperations().disableNotifications(98765);
	}
	
	@Test
	public void disableNotifications_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/update.json?screen_name=oizik2&device=false"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("unfollow"), APPLICATION_JSON));
		TwitterProfile unFollowedUser = twitter.friendOperations().disableNotifications("oizik2");
		assertEquals("oizik2", unFollowedUser.getScreenName());
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void disableNotifications_byScreenName_unauthorized() {
		unauthorizedTwitter.friendOperations().disableNotifications("oizik2");
	}
	
	@Test
	public void getIncomingFriendships() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/incoming.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("incoming-or-outgoing-friendships"), APPLICATION_JSON));

		CursoredList<Long> friendships = twitter.friendOperations().getIncomingFriendships();
		assertIncomingOutgoingFriendships(friendships);
	}

	@Test
	public void getIncomingFriendships_cursored() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/incoming.json?cursor=1234567"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("incoming-or-outgoing-friendships"), APPLICATION_JSON));

		CursoredList<Long> friendships = twitter.friendOperations().getIncomingFriendships(1234567);
		assertIncomingOutgoingFriendships(friendships);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getIncomingFriendships_unauthorized() {
		unauthorizedTwitter.friendOperations().getIncomingFriendships();
	}
	
	@Test
	public void getOutgoingFriendships() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/outgoing.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("incoming-or-outgoing-friendships"), APPLICATION_JSON));

		CursoredList<Long> friendships = twitter.friendOperations().getOutgoingFriendships();
		assertIncomingOutgoingFriendships(friendships);
	}

	@Test
	public void getOutgoingFriendships_cursored() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friendships/outgoing.json?cursor=9876543"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("incoming-or-outgoing-friendships"), APPLICATION_JSON));

		CursoredList<Long> friendships = twitter.friendOperations().getOutgoingFriendships(9876543);
		assertIncomingOutgoingFriendships(friendships);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getOutgoingFriendships_unauthorized() {
		unauthorizedTwitter.friendOperations().getOutgoingFriendships();
	}
	
	private void assertFriendFollowerIdsList(CursoredList<Long> friendIds) {
		assertEquals(2, friendIds.size());
		assertEquals(14846645L, (long) friendIds.get(0));
		assertEquals(14718006L, (long) friendIds.get(1));
		assertEquals(112233, friendIds.getPreviousCursor());
		assertEquals(332211, friendIds.getNextCursor());
	}

	private void assertFriendsFollowers(CursoredList<TwitterProfile> friends) {
		assertEquals(2, friends.size());
		assertEquals("royclarkson", friends.get(0).getScreenName());
		assertEquals("kdonald", friends.get(1).getScreenName());
		assertEquals(112233, friends.getPreviousCursor());
		assertEquals(332211, friends.getNextCursor());
	}

	private void assertIncomingOutgoingFriendships(CursoredList<Long> friendships) {
		assertEquals(3, friendships.size());
		assertEquals(12345, (long) friendships.get(0));
		assertEquals(23456, (long) friendships.get(1));
		assertEquals(34567, (long) friendships.get(2));
		assertEquals(1234567890, friendships.getPreviousCursor());
		assertEquals(1357924680, friendships.getNextCursor());
	}

}
