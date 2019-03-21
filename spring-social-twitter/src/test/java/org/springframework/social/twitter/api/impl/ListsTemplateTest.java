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
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserList;

/**
 * @author Craig Walls
 */
public class ListsTemplateTest extends AbstractTwitterApiTest {
	
	@Test
	public void getLists_currentUser() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/list.json"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("multiple-list"), APPLICATION_JSON));
		assertListOfLists(twitter.listOperations().getLists());
	}
	
	@Test
	public void getLists_byId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/list.json?user_id=161064614"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("multiple-list"), APPLICATION_JSON));
		assertListOfLists(twitter.listOperations().getLists(161064614));
	}

	@Test
	public void getLists_byId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/list.json?user_id=161064614"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("multiple-list"), APPLICATION_JSON));
		assertListOfLists(appAuthTwitter.listOperations().getLists(161064614));
	}

	@Test
	public void getLists_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/list.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("multiple-list"), APPLICATION_JSON));
		assertListOfLists(twitter.listOperations().getLists("habuma"));
	}

	@Test
	public void getLists_byScreenName_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/list.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("multiple-list"), APPLICATION_JSON));
		assertListOfLists(appAuthTwitter.listOperations().getLists("habuma"));
	}

	@Test
	public void getList_byListId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/show.json?list_id=40841803"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		assertSingleList(twitter.listOperations().getList(40841803));
	}
	
	@Test
	public void getList_byScreenNameAndSlug() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/show.json?owner_screen_name=habuma&slug=sluggo"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		assertSingleList(twitter.listOperations().getList("habuma", "sluggo"));
	}

	@Test
	public void getList_byScreenNameAndSlug_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/show.json?owner_screen_name=habuma&slug=sluggo"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		assertSingleList(appAuthTwitter.listOperations().getList("habuma", "sluggo"));
	}

	
	@Test
	public void createList_publicListForUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/create.json"))
			.andExpect(method(POST))
			.andExpect(content().string("name=forfun&description=Just+for+Fun&mode=public"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		assertSingleList(twitter.listOperations().createList("forfun", "Just for Fun", true));
	}

	@Test
	public void createList_privateListForUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/create.json"))
			.andExpect(method(POST))
			.andExpect(content().string("name=forfun2&description=Just+for+Fun%2C+too&mode=private"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		assertSingleList(twitter.listOperations().createList("forfun2", "Just for Fun, too", false));
	}
	
	@Test
	public void updateList_publicListForUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/update.json"))
			.andExpect(method(POST))
			.andExpect(content().string("name=forfun&description=Just+for+Fun&mode=public&list_id=40841803"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		assertSingleList(twitter.listOperations().updateList(40841803, "forfun", "Just for Fun", true));
	}

	@Test
	public void updateList_privateListForUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/update.json"))
			.andExpect(method(POST))
			.andExpect(content().string("name=forfun2&description=Just+for+Fun%2C+too&mode=private&list_id=40841803"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		assertSingleList(twitter.listOperations().updateList(40841803, "forfun2", "Just for Fun, too", false));
	}

	@Test
	public void deleteList_forUserIdByListId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/destroy.json"))
			.andExpect(method(POST))
			.andExpect(content().string("list_id=40841803"))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.listOperations().deleteList(40841803);
		mockServer.verify();
	}
	
	@Test
	public void getListMembers_byListId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members.json?list_id=40841803"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		assertListMembers(twitter.listOperations().getListMembers(40841803));
	}

	@Test
	public void getListMembersInCursor_byListId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members.json?list_id=40841803&cursor=12345"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		CursoredList<TwitterProfile> listMembers = twitter.listOperations().getListMembersInCursor(40841803, 12345);
		assertListMembers(listMembers);
		assertEquals(332211, listMembers.getNextCursor());
		assertEquals(112233, listMembers.getPreviousCursor());
		
	}

	@Test
	public void getListMembers_byScreenNameAndListSlug() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members.json?owner_screen_name=habuma&slug=forfun"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		assertListMembers(twitter.listOperations().getListMembers("habuma", "forfun"));
	}

	@Test
	public void getListMembersInCursor_byScreenNameAndListSlug() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members.json?owner_screen_name=habuma&slug=forfun&cursor=12345"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		CursoredList<TwitterProfile> listMembers = twitter.listOperations().getListMembersInCursor("habuma", "forfun", 12345);
		assertListMembers(listMembers);
		assertEquals(332211, listMembers.getNextCursor());
		assertEquals(112233, listMembers.getPreviousCursor());
	}

	@Test
	public void getListMembers_byScreenNameAndListSlug_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members.json?owner_screen_name=habuma&slug=forfun"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		assertListMembers(appAuthTwitter.listOperations().getListMembers("habuma", "forfun"));
	}

	@Test
	public void addToList_forUserIdListIdSingle() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/create_all.json"))
			.andExpect(method(POST))
			.andExpect(content().string("user_id=123456&list_id=40841803"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));

		assertSingleList(twitter.listOperations().addToList(40841803, 123456));
	}

	@Test
	public void addToList_forUserIdListIdMultiple() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/create_all.json"))
			.andExpect(method(POST))
			.andExpect(content().string("user_id=123456%2C234567%2C345678&list_id=40841803"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));

		assertSingleList(twitter.listOperations().addToList(40841803, 123456, 234567, 345678));
	}

	@Test
	public void addToList_forScreenNameMultiple() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/create_all.json"))
			.andExpect(method(POST))
			.andExpect(content().string("screen_name=habuma%2Croyclarkson&list_id=40841803"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));

		assertSingleList(twitter.listOperations().addToList(40841803, "habuma", "royclarkson"));
	}

	@Test
	public void removeFromList_ownerIdListIdMemberId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/destroy.json"))
			.andExpect(method(POST))
			.andExpect(content().string("user_id=12345&list_id=40841803"))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.listOperations().removeFromList(40841803, 12345);
		mockServer.verify();
	}
	
	@Test
	public void removeFromList_screenName() {		
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/destroy.json"))
			.andExpect(method(POST))
			.andExpect(content().string("screen_name=habuma&list_id=40841803"))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.listOperations().removeFromList(40841803, "habuma");
		mockServer.verify();
	}

	@Test
	public void getListSubscribers_byListId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers.json?list_id=40841803"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		assertListMembers(twitter.listOperations().getListSubscribers(40841803));
	}
	
	@Test
	public void getListSubscribers_byScreenNameAndListSlug() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers.json?owner_screen_name=habuma&slug=forfun"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		assertListMembers(twitter.listOperations().getListSubscribers("habuma", "forfun"));
	}

	@Test
	public void getListSubscribers_byScreenNameAndListSlug_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers.json?owner_screen_name=habuma&slug=forfun"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-members"), APPLICATION_JSON));
		assertListMembers(appAuthTwitter.listOperations().getListSubscribers("habuma", "forfun"));
	}

	@Test
	public void getMemberships_forUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/memberships.json?user_id=161064614"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(twitter.listOperations().getMemberships(161064614));
	}

	@Test
	public void getMembershipsInCursor_forUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/memberships.json?user_id=161064614&cursor=12345"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		CursoredList<UserList> memberships = twitter.listOperations().getMembershipsInCursor(161064614, 12345);
		assertListOfLists(memberships);
		assertEquals(234567, memberships.getNextCursor());
		assertEquals(123456, memberships.getPreviousCursor());
	}

	@Test
	public void getMemberships_forUserId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/memberships.json?user_id=161064614"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(appAuthTwitter.listOperations().getMemberships(161064614));
	}

	@Test
	public void getMemberships_forScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/memberships.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(twitter.listOperations().getMemberships("habuma"));
	}

	@Test
	public void getMembershipsInCursor_forScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/memberships.json?screen_name=habuma&cursor=12345"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		CursoredList<UserList> memberships = twitter.listOperations().getMembershipsInCursor("habuma", 12345);
		assertListOfLists(memberships);
		assertEquals(234567, memberships.getNextCursor());
		assertEquals(123456, memberships.getPreviousCursor());
	}

	@Test
	public void getMemberships_forScreenName_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/memberships.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(appAuthTwitter.listOperations().getMemberships("habuma"));
	}

	@Test
	public void getSubscriptions_forUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscriptions.json?user_id=161064614"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(twitter.listOperations().getSubscriptions(161064614));
	}

	@Test
	public void getSubscriptionsInCursor_forUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscriptions.json?user_id=161064614&cursor=12345"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		CursoredList<UserList> subscriptions = twitter.listOperations().getSubscriptionsInCursor(161064614, 12345);
		assertListOfLists(subscriptions);
		assertEquals(234567, subscriptions.getNextCursor());
		assertEquals(123456, subscriptions.getPreviousCursor());
	}

	@Test
	public void getSubscriptions_forUserId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscriptions.json?user_id=161064614"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(appAuthTwitter.listOperations().getSubscriptions(161064614));
	}

	@Test
	public void getSubscriptions_forScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscriptions.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(twitter.listOperations().getSubscriptions("habuma"));
	}

	@Test
	public void getSubscriptionsInCursor_forScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscriptions.json?screen_name=habuma&cursor=12345"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		CursoredList<UserList> subscriptions = twitter.listOperations().getSubscriptionsInCursor("habuma", 12345);
		assertListOfLists(subscriptions);
		assertEquals(234567, subscriptions.getNextCursor());
		assertEquals(123456, subscriptions.getPreviousCursor());
	}

	@Test
	public void getSubscriptions_forScreenName_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscriptions.json?screen_name=habuma"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		assertListOfLists(appAuthTwitter.listOperations().getSubscriptions("habuma"));
	}

	@Test
	public void isMember_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?list_id=40841803&user_id=123456"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?list_id=40841803&user_id=987654"))
			.andExpect(method(GET))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(twitter.listOperations().isMember(40841803, 123456));
		assertFalse(twitter.listOperations().isMember(40841803, 987654));
	}

	@Test
	public void isMember_byUserId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?list_id=40841803&user_id=123456"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-of-lists"), APPLICATION_JSON));
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?list_id=40841803&user_id=987654"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(appAuthTwitter.listOperations().isMember(40841803, 123456));
		assertFalse(appAuthTwitter.listOperations().isMember(40841803, 987654));
	}

	@Test
	public void isMember_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?owner_screen_name=habuma&slug=forfun&screen_name=royclarkson"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?owner_screen_name=habuma&slug=forfun&screen_name=kdonald"))
			.andExpect(method(GET))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(twitter.listOperations().isMember("habuma", "forfun", "royclarkson"));
		assertFalse(twitter.listOperations().isMember("habuma", "forfun", "kdonald"));
	}

	@Test
	public void isMember_byScreenName_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?owner_screen_name=habuma&slug=forfun&screen_name=royclarkson"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/members/show.json?owner_screen_name=habuma&slug=forfun&screen_name=kdonald"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(appAuthTwitter.listOperations().isMember("habuma", "forfun", "royclarkson"));
		assertFalse(appAuthTwitter.listOperations().isMember("habuma", "forfun", "kdonald"));
	}

	@Test
	public void isSubscriber_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?list_id=40841803&user_id=123456"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?list_id=40841803&user_id=987654"))
			.andExpect(method(GET))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(twitter.listOperations().isSubscriber(40841803, 123456));
		assertFalse(twitter.listOperations().isSubscriber(40841803, 987654));
	}

	@Test
	public void isSubscriber_byUserId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?list_id=40841803&user_id=123456"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?list_id=40841803&user_id=987654"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(appAuthTwitter.listOperations().isSubscriber(40841803, 123456));
		assertFalse(appAuthTwitter.listOperations().isSubscriber(40841803, 987654));
	}

	@Test
	public void isSubscriber_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?owner_screen_name=habuma&slug=forfun&screen_name=royclarkson"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?owner_screen_name=habuma&slug=forfun&screen_name=kdonald"))
			.andExpect(method(GET))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(twitter.listOperations().isSubscriber("habuma", "forfun", "royclarkson"));
		assertFalse(twitter.listOperations().isSubscriber("habuma", "forfun", "kdonald"));
	}

	@Test
	public void isSubscriber_byScreenName_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?owner_screen_name=habuma&slug=forfun&screen_name=royclarkson"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/show.json?owner_screen_name=habuma&slug=forfun&screen_name=kdonald"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withStatus(NOT_FOUND).body("{}").contentType(APPLICATION_JSON));
		assertTrue(appAuthTwitter.listOperations().isSubscriber("habuma", "forfun", "royclarkson"));
		assertFalse(appAuthTwitter.listOperations().isSubscriber("habuma", "forfun", "kdonald"));
	}

	@Test
	public void subscribe() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/create.json"))
			.andExpect(method(POST))
			.andExpect(content().string("list_id=54321"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		UserList list = twitter.listOperations().subscribe(54321);
		assertSingleList(list);
	}
	
	@Test
	public void subscribe_usernameAndSlug() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/create.json"))
			.andExpect(method(POST))
			.andExpect(content().string("owner_screen_name=habuma&slug=somelist"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		UserList list = twitter.listOperations().subscribe("habuma", "somelist");
		assertSingleList(list);
	}
	
	@Test
	public void unsubscribe() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/destroy.json"))
			.andExpect(method(POST))
			.andExpect(content().string("list_id=54321"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		UserList list = twitter.listOperations().unsubscribe(54321);
		assertSingleList(list);
		mockServer.verify();
	}
	
	@Test
	public void unsubscribe_usernameAndSlug() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/subscribers/destroy.json"))
			.andExpect(method(POST))
			.andExpect(content().string("owner_screen_name=habuma&slug=somelist"))
			.andRespond(withSuccess(jsonResource("single-list"), APPLICATION_JSON));
		twitter.listOperations().unsubscribe("habuma", "somelist");
		mockServer.verify();
	}

	@Test
	public void getListStatuses_listId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=20&list_id=1234&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.listOperations().getListStatuses(1234);
		assertTimelineTweets(timeline);
	}
	
	@Test
	public void getListStatuses_listId_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=30&since_id=12345&max_id=54321&list_id=1234&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.listOperations().getListStatuses(1234, 30, 12345, 54321);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getListStatuses_slug() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=20&owner_screen_name=habuma&slug=mylist&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.listOperations().getListStatuses("habuma", "mylist");
		assertTimelineTweets(timeline);
	}

	@Test
	public void getListStatuses_slug_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=20&owner_screen_name=habuma&slug=mylist&include_entities=true"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.listOperations().getListStatuses("habuma", "mylist");
		assertTimelineTweets(timeline);
	}

	@Test
	public void getListStatuses_slug_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=30&owner_screen_name=habuma&slug=mylist&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.listOperations().getListStatuses("habuma", "mylist", 30);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getListStatuses_slug_paged_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=30&owner_screen_name=habuma&slug=mylist&include_entities=true"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.listOperations().getListStatuses("habuma", "mylist", 30);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getListStatuses_slug_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=30&since_id=12345&max_id=54321&owner_screen_name=habuma&slug=mylist&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.listOperations().getListStatuses("habuma", "mylist", 30, 12345, 54321);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getListStatuses_slug_withSinceIdAndMaxId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/lists/statuses.json?count=30&since_id=12345&max_id=54321&owner_screen_name=habuma&slug=mylist&include_entities=true"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.listOperations().getListStatuses("habuma", "mylist", 30, 12345, 54321);
		assertTimelineTweets(timeline);
	}

	// private helpers
	
	private void assertSingleList(UserList list) {
		assertEquals(40841803, list.getId());
		assertEquals("forFun", list.getName());
		assertEquals("@habuma/forfun", list.getFullName());
		assertEquals("forfun", list.getSlug());
		assertEquals("Just for fun", list.getDescription());
		assertEquals(22, list.getMemberCount());
		assertEquals(100, list.getSubscriberCount());
		assertEquals("/habuma/forfun", list.getUriPath());
		assertFalse(list.isPublic());
	}

	private void assertListOfLists(List<UserList> lists) {
		assertEquals(2, lists.size());
		UserList list1 = lists.get(0);
		assertEquals(40842137, list1.getId());
		assertEquals("forFun2", list1.getName());
		assertEquals("@habuma/forfun2", list1.getFullName());
		assertEquals("forfun2", list1.getSlug());
		assertEquals("Just for fun, too", list1.getDescription());
		assertEquals(3, list1.getMemberCount());
		assertEquals(0, list1.getSubscriberCount());
		assertEquals("/habuma/forfun2", list1.getUriPath());
		assertTrue(list1.isPublic());
		UserList list2 = lists.get(1);
		assertEquals(40841803, list2.getId());
		assertEquals("forFun", list2.getName());
		assertEquals("@habuma/forfun", list2.getFullName());
		assertEquals("forfun", list2.getSlug());
		assertEquals("Just for fun", list2.getDescription());
		assertEquals(22, list2.getMemberCount());
		assertEquals(100, list2.getSubscriberCount());
		assertEquals("/habuma/forfun", list2.getUriPath());
		assertFalse(list2.isPublic());
	}

	private void assertListMembers(List<TwitterProfile> members) {
		assertEquals(2, members.size());
		TwitterProfile profile1 = members.get(0);
		assertEquals(14846645, profile1.getId());
		assertEquals("royclarkson", profile1.getScreenName());
		assertEquals("Roy Clarkson", profile1.getName());
		assertEquals("Follower of mobile, social, and web technology trends. I write lots of code, and work at SpringSource.", 
				profile1.getDescription());
		assertEquals("Atlanta, GA, USA", profile1.getLocation());
		TwitterProfile profile2 = members.get(1);
		assertEquals(14718006, profile2.getId());
		assertEquals("kdonald", profile2.getScreenName());
		assertEquals("Keith Donald", profile2.getName());
		assertEquals("SpringSource co-founder", profile2.getDescription());
		assertEquals("Melbourne, Fl", profile2.getLocation());
	}

}
