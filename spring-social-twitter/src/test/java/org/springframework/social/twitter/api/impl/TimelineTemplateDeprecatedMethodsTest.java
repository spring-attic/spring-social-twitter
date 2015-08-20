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

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.StatusDetails;
import org.springframework.social.twitter.api.Tweet;


/**
 * @author Craig Walls
 * @deprecated
 */
@Deprecated
public class TimelineTemplateDeprecatedMethodsTest extends AbstractTwitterApiTest {

	@Test
	public void updateStatus_withImage_DEPRECATED() {
		mockServer.expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("media-upload"), APPLICATION_JSON));
		
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&media_ids=553639437322563584"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", photo);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withLocation_DEPRECATED() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=123.1&long=-111.2"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setLocation(123.1f, -111.2f);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withLocationAndDisplayCoordinates_DEPRECATED() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=123.1&long=-111.2&display_coordinates=true"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setLocation(123.1f, -111.2f);
		details.setDisplayCoordinates(true);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withInReplyToStatus_DEPRECATED() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message+in+reply+to+%40someone&in_reply_to_status_id=123456"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setInReplyToStatusId(123456);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message in reply to @someone", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}
	
	@Test
	public void updateStatus_withWrapLinks_DEPRECATED() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&wrap_links=true"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setWrapLinks(true);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withImageAndLocation_DEPRECATED() {
		mockServer.expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("media-upload"), APPLICATION_JSON));
	
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=123.1&long=-111.2&media_ids=553639437322563584"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		StatusDetails details = new StatusDetails();
		details.setLocation(123.1f, -111.2f);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", photo, details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	// private helper
	private Resource getUploadResource(final String filename, String content) {
		Resource resource = new ByteArrayResource(content.getBytes()) {
			public String getFilename() throws IllegalStateException {
				return filename;
			};
		};
		return resource;
	}
	
}
