/*
 * Copyright 2014 the original author or authors.
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
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.social.ApiException;
import org.springframework.social.InternalServerErrorException;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.ServerDownException;
import org.springframework.social.ServerOverloadedException;

public class ApiErrorTest extends AbstractTwitterApiTest {

	@Test(expected = NotAuthorizedException.class)
	@Ignore("Come back to this one")
	public void badOrMissingAccessToken() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
			.andExpect(method(POST))
			.andExpect(content().string("status=Some+message"))
			.andRespond(withStatus(UNAUTHORIZED).body("").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Some message");		
	}

	@Test(expected = InvalidAuthorizationException.class)
	public void badAccessToken() { // token is fabricated or fails signature validation
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/verify_credentials.json"))
			.andExpect(method(GET))
			.andRespond(withStatus(UNAUTHORIZED).body(jsonResource("error-invalid-token")).contentType(APPLICATION_JSON));
		twitter.userOperations().getUserProfile();
	}
	
	@Test(expected = RevokedAuthorizationException.class)
	public void revokedToken() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/verify_credentials.json"))
			.andExpect(method(GET))
			.andRespond(withStatus(UNAUTHORIZED).body(jsonResource("error-revoked-token")).contentType(APPLICATION_JSON));
		twitter.userOperations().getUserProfile();		
	}

	@Test(expected = RateLimitExceededException.class)
	public void enhanceYourCalm() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50"))
			.andExpect(method(GET))
			.andRespond(withStatus(HttpStatus.valueOf(420)).body("{\"error\":\"You have been rate limited. Enhance your calm.\"}").contentType(APPLICATION_JSON));
		twitter.searchOperations().search("#spring");
	}

	@Test(expected = RateLimitExceededException.class)
	public void tooManyRequests() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50"))
			.andExpect(method(GET))
			.andRespond(withStatus(HttpStatus.valueOf(429)).body("{\"errors\":[ {\"code\":88, \"message\":\"Rate limit exceeded\"} ] }").contentType(APPLICATION_JSON));
		twitter.searchOperations().search("#spring");
	}

	@Test(expected = InternalServerErrorException.class)
	public void twitterIsBroken() {
		try {
			mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withServerError().body("Non-JSON body").contentType(APPLICATION_JSON));
			twitter.timelineOperations().getHomeTimeline();
		} catch (InternalServerErrorException e) {
			assertEquals("Something is broken at Twitter. Please see https://dev.twitter.com/pages/support to report the issue.", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ServerDownException.class)
	public void twitterIsDownOrBeingUpgraded() {
		try {
			mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withStatus(BAD_GATEWAY).body("Non-JSON body").contentType(APPLICATION_JSON));
			twitter.timelineOperations().getHomeTimeline();
		} catch (ServerDownException e) {
			assertEquals("Twitter is down or is being upgraded.", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ServerOverloadedException.class)
	public void twitterIsOverloaded() {
		try {
			mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withStatus(SERVICE_UNAVAILABLE).body("Non-JSON body").contentType(APPLICATION_JSON));
			twitter.timelineOperations().getHomeTimeline();
		} catch (ServerDownException e) {
			assertEquals("Twitter is overloaded with requests. Try again later.", e.getMessage());
			throw e;
		}
	}

	@Test(expected = ApiException.class)
	public void nonJSONErrorResponse() {
		try { 
			mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withBadRequest().body("<h1>HTML response</h1>").contentType(APPLICATION_JSON));
			twitter.timelineOperations().getHomeTimeline();
		} catch (ApiException e) {
			assertEquals("Error consuming Twitter REST API", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ApiException.class)
	@Ignore("TODO: Need to handle cases where there isn't an error, but the body is unparseable.")
	public void unparseableSuccessResponse() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json"))
			.andExpect(method(GET))
			.andRespond(withSuccess("Unparseable {text}", APPLICATION_JSON));
		twitter.timelineOperations().getHomeTimeline();		
	}
	
	@Test(expected = RateLimitExceededException.class)
	public void dailyStatusLimitExceeded() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
			.andExpect(method(POST))
			.andExpect(content().string("status=Some+message"))
			.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"User is over daily status update limit.\", \"request\":\"/1/statuses/update.json\"}").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Some message");				
	}

	@Test(expected = RateLimitExceededException.class)
	public void hourlyRateLimitExceeded() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/verify_credentials.json"))
			.andExpect(method(GET))
			.andRespond(withBadRequest().body("{\"error\":\"Rate limit exceeded. Clients may not make more than 350 requests per hour.\"}").contentType(APPLICATION_JSON));
		twitter.userOperations().getUserProfile();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void streamingUnauthorizedWithEmptyBody() {
		mockServer.expect(requestTo("https://stream.twitter.com/1.1/statuses/sample.json"))
			.andExpect(method(GET))
			.andRespond(withUnauthorizedRequest().body(new ClassPathResource("401.html", getClass())));
		twitter.restOperations().getForObject("https://stream.twitter.com/1.1/statuses/sample.json", String.class);
	}
}
