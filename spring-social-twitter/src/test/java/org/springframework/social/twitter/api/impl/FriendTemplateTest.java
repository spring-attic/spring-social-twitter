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
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriendsInCursor_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=987654321"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriendsInCursor(987654321);
		assertFriendsFollowers(friends);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFriends_currentUser_unauthorized() {
		unauthorizedTwitter.friendOperations().getFriends();
	}
	
	@Test
	public void getFriends_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends(98765L);
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriendsInCursor_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=987654321&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriendsInCursor(98765L, 987654321);
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriends_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends("habuma");
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriendsInCursor_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=987654321&screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriendsInCursor("habuma", 987654321);
		assertFriendsFollowers(friends);
	}

	@Test
	public void getFriends_currentUser_noFriends() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("no-friend-or-follower-ids"), APPLICATION_JSON));

		List<TwitterProfile> friends = twitter.friendOperations().getFriends();
		assertEquals(0, friends.size());
	}

	@Test
	public void getFriends_currentUser_manyFriends() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/friends/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("many-friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=1%2C2%2C3%2C4%2C5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16%2C17%2C18%2C19%2C20%2C21%2C22%2C23%2C24%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C34%2C35%2C36%2C37%2C38%2C39%2C40%2C41%2C42%2C43%2C44%2C45%2C46%2C47%2C48%2C49%2C50%2C51%2C52%2C53%2C54%2C55%2C56%2C57%2C58%2C59%2C60%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C69%2C70%2C71%2C72%2C73%2C74%2C75%2C76%2C77%2C78%2C79%2C80%2C81%2C82%2C83%2C84%2C85%2C86%2C87%2C88%2C89%2C90%2C91%2C92%2C93%2C94%2C95%2C96%2C97%2C98%2C99%2C100"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%2C113%2C114%2C115%2C116%2C117%2C118%2C119%2C120%2C121%2C122%2C123%2C124%2C125%2C126%2C127%2C128%2C129%2C130%2C131%2C132%2C133%2C134%2C135%2C136%2C137%2C138%2C139%2C140%2C141%2C142%2C143%2C144%2C145%2C146%2C147%2C148%2C149%2C150%2C151%2C152%2C153%2C154%2C155%2C156%2C157%2C158%2C159%2C160%2C161%2C162%2C163%2C164%2C165%2C166%2C167%2C168%2C169%2C170%2C171%2C172%2C173%2C174%2C175%2C176%2C177%2C178%2C179%2C180%2C181%2C182%2C183%2C184%2C185%2C186%2C187%2C188%2C189%2C190%2C191%2C192%2C193%2C194%2C195%2C196%2C197%2C198%2C199%2C200"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=201%2C202%2C203%2C204%2C205%2C206%2C207%2C208%2C209%2C210%2C211%2C212%2C213%2C214%2C215%2C216%2C217%2C218%2C219%2C220%2C221%2C222%2C223%2C224%2C225%2C226%2C227%2C228%2C229%2C230%2C231%2C232%2C233%2C234%2C235%2C236%2C237%2C238%2C239%2C240%2C241%2C242"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		List<TwitterProfile> friends = twitter.friendOperations().getFriends();
		// what's important is that the IDs from friends/ids is chunked up correctly and that users/lookup was invoked the right number of times 
		// and that each time its response was added to the big list; not that we actually get 242 profiles back
		assertEquals(6, friends.size());
		assertEquals("royclarkson", friends.get(0).getScreenName());
		assertEquals("kdonald", friends.get(1).getScreenName());
		assertEquals("royclarkson", friends.get(2).getScreenName());
		assertEquals("kdonald", friends.get(3).getScreenName());
		assertEquals("royclarkson", friends.get(4).getScreenName());
		assertEquals("kdonald", friends.get(5).getScreenName());
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
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowers();
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test 
	public void getFollowersInCursor_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=24680"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

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
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		List<TwitterProfile> followers = twitter.friendOperations().getFollowers(98765L);
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test 
	public void getFollowersInCursor_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=13579&user_id=98765"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

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
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=oizik"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
	    
		List<TwitterProfile> followers = twitter.friendOperations().getFollowers("oizik");
		assertEquals(2, followers.size());
		assertEquals("royclarkson", followers.get(0).getScreenName());
		assertEquals("kdonald", followers.get(1).getScreenName());
	}

	@Test 
	public void getFollowersInCursor_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=12357&screen_name=oizik"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
	    
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
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("no-friend-or-follower-ids"), APPLICATION_JSON));

		List<TwitterProfile> friends = twitter.friendOperations().getFollowers();
		assertEquals(0, friends.size());
	}

	@Test
	public void getFollowers_currentUser_manyFollowers() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/followers/ids.json?cursor=-1"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("many-friend-or-follower-ids"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=1%2C2%2C3%2C4%2C5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16%2C17%2C18%2C19%2C20%2C21%2C22%2C23%2C24%2C25%2C26%2C27%2C28%2C29%2C30%2C31%2C32%2C33%2C34%2C35%2C36%2C37%2C38%2C39%2C40%2C41%2C42%2C43%2C44%2C45%2C46%2C47%2C48%2C49%2C50%2C51%2C52%2C53%2C54%2C55%2C56%2C57%2C58%2C59%2C60%2C61%2C62%2C63%2C64%2C65%2C66%2C67%2C68%2C69%2C70%2C71%2C72%2C73%2C74%2C75%2C76%2C77%2C78%2C79%2C80%2C81%2C82%2C83%2C84%2C85%2C86%2C87%2C88%2C89%2C90%2C91%2C92%2C93%2C94%2C95%2C96%2C97%2C98%2C99%2C100"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%2C113%2C114%2C115%2C116%2C117%2C118%2C119%2C120%2C121%2C122%2C123%2C124%2C125%2C126%2C127%2C128%2C129%2C130%2C131%2C132%2C133%2C134%2C135%2C136%2C137%2C138%2C139%2C140%2C141%2C142%2C143%2C144%2C145%2C146%2C147%2C148%2C149%2C150%2C151%2C152%2C153%2C154%2C155%2C156%2C157%2C158%2C159%2C160%2C161%2C162%2C163%2C164%2C165%2C166%2C167%2C168%2C169%2C170%2C171%2C172%2C173%2C174%2C175%2C176%2C177%2C178%2C179%2C180%2C181%2C182%2C183%2C184%2C185%2C186%2C187%2C188%2C189%2C190%2C191%2C192%2C193%2C194%2C195%2C196%2C197%2C198%2C199%2C200"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=201%2C202%2C203%2C204%2C205%2C206%2C207%2C208%2C209%2C210%2C211%2C212%2C213%2C214%2C215%2C216%2C217%2C218%2C219%2C220%2C221%2C222%2C223%2C224%2C225%2C226%2C227%2C228%2C229%2C230%2C231%2C232%2C233%2C234%2C235%2C236%2C237%2C238%2C239%2C240%2C241%2C242"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));

		List<TwitterProfile> friends = twitter.friendOperations().getFollowers();
		// what's important is that the IDs from friends/ids is chunked up correctly and that users/lookup was invoked the right number of times 
		// and that each time its response was added to the big list; not that we actually get 242 profiles back
		assertEquals(6, friends.size());
		assertEquals("royclarkson", friends.get(0).getScreenName());
		assertEquals("kdonald", friends.get(1).getScreenName());
		assertEquals("royclarkson", friends.get(2).getScreenName());
		assertEquals("kdonald", friends.get(3).getScreenName());
		assertEquals("royclarkson", friends.get(4).getScreenName());
		assertEquals("kdonald", friends.get(5).getScreenName());
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
