/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl.ton;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.social.twitter.api.ton.TonOperations;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForHttpEntity;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForTon;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link TonOperations}, providing a binding to the Twitter Object Nest.
 * 
 * @author Chris Latko
 */
public class TonTemplate extends AbstractTwitterOperations implements TonOperations {
	
	private final RestTemplate restTemplate;

    public TonTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

	@Override
	public URI uploadSingleChunk(final String bucketName, final byte[] data, final String contentType, final ZonedDateTime expiry) {
        requireUserAuthorization();

        URI uri = new TwitterApiBuilderForUri()
        .withResource(TwitterApiUriResourceForTon.TWITTER_OBJECT_NEST)
        .withArgument("bucket_name", bucketName)
        .build();

        HttpEntity<?> entity = new TwitterApiBuilderForHttpEntity<>(data)
		.addHeader("Content-Type", Arrays.asList(contentType))
		.addHeader("Content-Length", Arrays.asList(String.valueOf(data.length)))
		.addHeader("X-TON-Expires", Arrays.asList(expiry.format(DateTimeFormatter.RFC_1123_DATE_TIME)))
		.build();

        return restTemplate.postForLocation(uri, entity);
	}

	@Override
	public void uploadResumable(final String bucketName, final String resumeId, final byte[] data, final String contentType, final LocalDateTime expiry) {

	}

	


}
