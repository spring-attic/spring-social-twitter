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
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.test.web.client.MockRestServiceServer;

public class ClientSearchTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void search_forClient() {
		ClientSearchTemplate clientSearch = new ClientSearchTemplate("client_token");
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(clientSearch.getRestTemplate());

		mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer client_token"))
				.andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));
		SearchResults searchResults = clientSearch.search(new SearchParameters("#spring"));
		assertEquals(10, searchResults.getSearchMetadata().getSince_id());
		assertEquals(999, searchResults.getSearchMetadata().getMax_id());
		List<Tweet> tweets = searchResults.getTweets();
		assertSearchTweets(tweets);
	}

	// test helpers

	private void assertSearchTweets(List<Tweet> tweets) {
		assertTimelineTweets(tweets, true);
		assertEquals("en", tweets.get(0).getLanguageCode());
		assertEquals("de", tweets.get(1).getLanguageCode());
	}
}
