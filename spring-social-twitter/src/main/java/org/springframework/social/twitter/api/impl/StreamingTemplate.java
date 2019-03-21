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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.twitter.api.FilterStreamParameters;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamingOperations;
import org.springframework.social.twitter.api.UserStreamParameters;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Craig Walls
 */
class StreamingTemplate extends AbstractTwitterOperations implements StreamingOperations {
	
	private final RestTemplate restTemplate;
					
	public StreamingTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
		super(isAuthorizedForUser, isAuthorizedForApp);
		this.restTemplate = restTemplate;
	}

	public Stream firehose(final List<StreamListener> listeners) {
		Assert.notEmpty(listeners, "Listeners collection may not be null or empty");
		Stream stream = new ThreadedStreamConsumer() {
			protected StreamReader getStreamReader() throws StreamCreationException {
				return createStream(HttpMethod.GET, FIREHOSE_STREAM_URL, EMPTY_BODY, listeners);
			}
		};
		stream.open();
		return stream;
	}
	
	public Stream firehose(final int backfill, final List<StreamListener> listeners) {
		Assert.isTrue(Math.abs(backfill) >= -1 && Math.abs(backfill) <= 150000, "'backfill' must be a value between 1 to 150000 or -1 to -150000");
		Assert.notEmpty(listeners, "Listeners collection may not be null or empty");
		Stream stream = new ThreadedStreamConsumer() {
			protected StreamReader getStreamReader() throws StreamCreationException {				
				MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>(1);
				parameters.set("count", String.valueOf(backfill));
				return createStream(HttpMethod.GET, FIREHOSE_STREAM_URL, parameters, listeners);
			}
		};
		stream.open();
		return stream;
	}
	
	public Stream sample(final List<StreamListener> listeners) {
		Assert.notEmpty(listeners, "Listeners collection may not be null or empty");
		Stream stream = new ThreadedStreamConsumer() {
			protected StreamReader getStreamReader() throws StreamCreationException {
				return createStream(HttpMethod.GET, SAMPLE_STREAM_URL, EMPTY_BODY, listeners);
			}
		};
		stream.open();
		return stream;
	}
	
	public Stream filter(String trackKeywords, List<StreamListener> listeners) {
		return filter((FilterStreamParameters) new FilterStreamParameters().track(trackKeywords), listeners);
	}

	public Stream filter(final FilterStreamParameters parameters, final List<StreamListener> listeners) {
		Assert.notNull(parameters, "StreamFilter may not be null");
		Assert.isTrue(parameters.isValid(), "At least one of follow, track, or location must be specified in StreamFilter");
		Assert.notEmpty(listeners, "Listeners collection may not be null or empty");
		Stream stream = new ThreadedStreamConsumer() {
			protected StreamReader getStreamReader() throws StreamCreationException {
				return createStream(HttpMethod.POST, FILTERED_STREAM_URL, parameters.toParameterMap(), listeners);
			}
		};
		stream.open();
		return stream;
	}
	
	public Stream user(List<StreamListener> listeners) {
		return user(new UserStreamParameters(), listeners);
	}
	
	public Stream user(final UserStreamParameters parameters, final List<StreamListener> listeners) {
		Assert.notNull(parameters, "StreamFilter may not be null");
		Assert.notEmpty(listeners, "Listeners collection may not be null or empty");
		Stream stream = new ThreadedStreamConsumer() {
			protected StreamReader getStreamReader() throws StreamCreationException {
				return createStream(HttpMethod.POST, USER_STREAM_URL, parameters.toParameterMap(), listeners);
			}
		};
		stream.open();
		return stream;
	}
	
	private StreamReader createStream(HttpMethod method, String streamUrl, MultiValueMap<String, String> body, List<StreamListener> listeners) throws StreamCreationException {
		try {
			ClientHttpResponse response = executeRequest(method, streamUrl, body);
			if (response.getStatusCode().value() > 200) {
				throw new StreamCreationException("Unable to create stream", response.getStatusCode());
			}
			return new StreamReaderImpl(response.getBody(), listeners);
		} catch (IOException e) {
			throw new StreamCreationException("Unable to create stream.", e);
		}
	}
	
	private ClientHttpResponse executeRequest(HttpMethod method, String url, MultiValueMap<String, String> body) throws IOException {
		ClientHttpRequestFactory requestFactory = restTemplate.getRequestFactory();
		ClientHttpRequest request = requestFactory.createRequest(URI.create(url), method);
		OutputStreamWriter writer = new OutputStreamWriter(request.getBody());
		writer.write(createFormUrlEncodedBodyString(body));
		writer.flush();
		request.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return request.execute();
	}

	private String createFormUrlEncodedBodyString(MultiValueMap<String, String> body) {
		StringBuffer bodyBuffer = new StringBuffer();
		for (Iterator<Entry<String, List<String>>> bodyIt = body.entrySet().iterator(); bodyIt.hasNext(); ) {
			Entry<String, List<String>> entry = bodyIt.next();
			String key = entry.getKey();
			List<String> values = entry.getValue();
			for (Iterator<String> valuesIt = values.iterator(); valuesIt.hasNext(); ) {
				bodyBuffer.append(key).append("=").append(formEncode(valuesIt.next()));
				if(valuesIt.hasNext()) {
					bodyBuffer.append("&");
				}
			}
			if(bodyIt.hasNext()) {
				bodyBuffer.append("&");
			}
		}
		return bodyBuffer.toString();
	}
	
	private String formEncode(String data) {
		try {
			return URLEncoder.encode(data, "UTF-8");
		}
		catch (UnsupportedEncodingException wontHappen) {
			throw new IllegalStateException(wontHappen);
		}
	}

	private static final String SAMPLE_STREAM_URL = "https://stream.twitter.com/1.1/statuses/sample.json";
	private static final String FIREHOSE_STREAM_URL = "https://stream.twitter.com/1.1/statuses/firehose.json";
	private static final String FILTERED_STREAM_URL = "https://stream.twitter.com/1.1/statuses/filter.json";
	private static final String USER_STREAM_URL = "https://userstream.twitter.com/1.1/user.json";
	
	private static final LinkedMultiValueMap<String, String> EMPTY_BODY = new LinkedMultiValueMap<String, String>();


}
