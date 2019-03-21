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

import java.util.List;

import org.junit.Test;
import org.springframework.social.twitter.api.SavedSearch;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Trend;
import org.springframework.social.twitter.api.Trends;
import org.springframework.social.twitter.api.Tweet;


/**
 * @author Craig Walls
 */
public class SearchTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void search_queryOnly() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));
		SearchResults searchResults = twitter.searchOperations().search("#spring");
		assertEquals(10, searchResults.getSearchMetadata().getSinceId());
		assertEquals(999, searchResults.getSearchMetadata().getMaxId());
		List<Tweet> tweets = searchResults.getTweets();
		assertSearchTweets(tweets);
	}
	
	@Test
	public void search_pageAndResultsPerPage() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=10"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));
		SearchResults searchResults = twitter.searchOperations().search("#spring", 10);
		assertEquals(10, searchResults.getSearchMetadata().getSinceId());
		assertEquals(999, searchResults.getSearchMetadata().getMaxId());
		List<Tweet> tweets = searchResults.getTweets();
		assertSearchTweets(tweets);
	}
	
	@Test
	public void search_sinceAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=10&since_id=123&max_id=54321"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));
		SearchResults searchResults = twitter.searchOperations().search("#spring", 10, 123, 54321);
		assertEquals(10, searchResults.getSearchMetadata().getSinceId());
		assertEquals(999, searchResults.getSearchMetadata().getMaxId());
		List<Tweet> tweets = searchResults.getTweets();
		assertSearchTweets(tweets);
	}
	
	@Test
	public void search_queryOnly_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));
		SearchResults searchResults = appAuthTwitter.searchOperations().search("#spring");
		assertEquals(10, searchResults.getSearchMetadata().getSinceId());
		assertEquals(999, searchResults.getSearchMetadata().getMaxId());
		List<Tweet> tweets = searchResults.getTweets();
		assertSearchTweets(tweets);
	}
	
	@Test
	public void search_pageAndResultsPerPage_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=10"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));
		SearchResults searchResults = appAuthTwitter.searchOperations().search("#spring", 10);
		assertEquals(10, searchResults.getSearchMetadata().getSinceId());
		assertEquals(999, searchResults.getSearchMetadata().getMaxId());
		List<Tweet> tweets = searchResults.getTweets();
		assertSearchTweets(tweets);
	}
	
	@Test
	public void search_sinceAndMaxId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=10&since_id=123&max_id=54321"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));
		SearchResults searchResults = appAuthTwitter.searchOperations().search("#spring", 10, 123, 54321);
		assertEquals(10, searchResults.getSearchMetadata().getSinceId());
		assertEquals(999, searchResults.getSearchMetadata().getMaxId());
		List<Tweet> tweets = searchResults.getTweets();
		assertSearchTweets(tweets);
	}
	
	@Test
	public void getSavedSearches() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/saved_searches/list.json"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("saved-searches"), APPLICATION_JSON));
		List<SavedSearch> savedSearches = twitter.searchOperations().getSavedSearches();
		assertEquals(2, savedSearches.size());
		SavedSearch search1 = savedSearches.get(0);
		assertEquals(26897775, search1.getId());
		assertEquals("#springsocial", search1.getQuery());
		assertEquals("#springsocial", search1.getName());
		assertEquals(0, search1.getPosition());
		SavedSearch search2 = savedSearches.get(1);
		assertEquals(56897772, search2.getId());
		assertEquals("#twitter", search2.getQuery());
		assertEquals("#twitter", search2.getName());
		assertEquals(1, search2.getPosition());
	}

	@Test
	public void getSavedSearch() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/saved_searches/show/26897775.json"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("saved-search"), APPLICATION_JSON));
		SavedSearch savedSearch = twitter.searchOperations().getSavedSearch(26897775);
		assertEquals(26897775, savedSearch.getId());
		assertEquals("#springsocial", savedSearch.getQuery());
		assertEquals("#springsocial", savedSearch.getName());
		assertEquals(0, savedSearch.getPosition());
	}
	
	@Test
	public void createSavedSearch() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/saved_searches/create.json"))
			.andExpect(method(POST))
			.andExpect(content().string("query=%23twitter"))
			.andRespond(withSuccess(jsonResource("saved-search"), APPLICATION_JSON));
		SavedSearch savedSearch = twitter.searchOperations().createSavedSearch("#twitter");
		assertEquals(26897775, savedSearch.getId());
		assertEquals("#springsocial", savedSearch.getQuery());
		assertEquals("#springsocial", savedSearch.getName());
		assertEquals(0, savedSearch.getPosition());
		mockServer.verify();
	}

	@Test
	public void deleteSavedSearch() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/saved_searches/destroy/26897775.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.searchOperations().deleteSavedSearch(26897775);
		mockServer.verify();
	}
	
	@Test
	public void getLocalTrends() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/trends/place.json?id=2442047"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("local-trends"), APPLICATION_JSON));
		Trends localTrends = twitter.searchOperations().getLocalTrends(2442047);
		List<Trend> trends = localTrends.getTrends();
		assertEquals(2, trends.size());
		Trend trend1 = trends.get(0);
		assertEquals("Cool Stuff", trend1.getName());
		assertEquals("Cool+Stuff", trend1.getQuery());
		Trend trend2 = trends.get(1);
		assertEquals("#springsocial", trend2.getName());
		assertEquals("%23springsocial", trend2.getQuery());
	}
	
	@Test
	public void getLocalTrends_excludeHashtags() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/trends/place.json?id=2442047&exclude=hashtags"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("local-trends"), APPLICATION_JSON));
		Trends localTrends = twitter.searchOperations().getLocalTrends(2442047, true);
		List<Trend> trends = localTrends.getTrends();
		assertEquals(2, trends.size());
	}	

	@Test
	public void getLocalTrends_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/trends/place.json?id=2442047"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("local-trends"), APPLICATION_JSON));
		Trends localTrends = appAuthTwitter.searchOperations().getLocalTrends(2442047);
		List<Trend> trends = localTrends.getTrends();
		assertEquals(2, trends.size());
		Trend trend1 = trends.get(0);
		assertEquals("Cool Stuff", trend1.getName());
		assertEquals("Cool+Stuff", trend1.getQuery());
		Trend trend2 = trends.get(1);
		assertEquals("#springsocial", trend2.getName());
		assertEquals("%23springsocial", trend2.getQuery());
	}
	
	@Test
	public void getLocalTrends_excludeHashtags_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/trends/place.json?id=2442047&exclude=hashtags"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("local-trends"), APPLICATION_JSON));
		Trends localTrends = appAuthTwitter.searchOperations().getLocalTrends(2442047, true);
		List<Trend> trends = localTrends.getTrends();
		assertEquals(2, trends.size());
	}	

	// test helpers

	private void assertSearchTweets(List<Tweet> tweets) {
		assertTimelineTweets(tweets, true);
		assertEquals("en", tweets.get(0).getLanguageCode());
		assertEquals("de", tweets.get(1).getLanguageCode());
	}
}
