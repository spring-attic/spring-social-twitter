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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.HttpRequestDecorator;
import org.springframework.social.twitter.api.BlockOperations;
import org.springframework.social.twitter.api.DirectMessageOperations;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.social.twitter.api.GeoOperations;
import org.springframework.social.twitter.api.ListOperations;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * This is the central class for interacting with Twitter.
 * <p>
 * Most (not all) Twitter operations require OAuth authentication. To perform
 * such operations, {@link TwitterTemplate} must be constructed with the minimal
 * amount of information required to sign requests to Twitter's API with an
 * OAuth <code>Authorization</code> header.
 * </p>
 * <p>
 * There are some operations, such as searching, that do not require OAuth
 * authentication. In those cases, you may use a {@link TwitterTemplate} that is
 * created through the default constructor and without any OAuth details.
 * Attempts to perform secured operations through such an instance, however,
 * will result in {@link NotAuthorizedException} being thrown.
 * </p>
 * @author Craig Walls
 */
public class TwitterTemplate extends AbstractOAuth1ApiBinding implements Twitter {
	
	private TimelineOperations timelineOperations;

	private UserOperations userOperations;

	private FriendOperations friendOperations;

	private ListOperations listOperations;

	private SearchOperations searchOperations;

	private DirectMessageOperations directMessageOperations;
	
	private BlockOperations blockOperations;
	
	private GeoOperations geoOperations;

	private RestTemplate clientRestTemplate = null;

	/**
	 * Create a new instance of TwitterTemplate.
	 * This constructor creates a new TwitterTemplate able to perform unauthenticated operations against Twitter's API.
	 * Some operations, such as search, do not require OAuth authentication.
	 * A TwitterTemplate created with this constructor will support those operations.
	 * Any operations requiring authentication will throw {@link NotAuthorizedException} .
	 */
	public TwitterTemplate() {
		super();
		initSubApis();
	}

	/**
	 * Create a new instance of TwitterTemplate.
	 * @param consumerKey the application's API key
	 * @param consumerSecret the application's API secret
	 * @param accessToken an access token acquired through OAuth authentication with Twitter
	 * @param accessTokenSecret an access token secret acquired through OAuth authentication with Twitter
	 */
	public TwitterTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		initSubApis();
	}

	/**
	 * Create a new instance of TwitterTemplate.
	 * This instance of TwitterTemplate is limited to only performing operations requiring client authorization.
	 * For instance, you can use it to search Twitter, but you cannot use it to post a status update.
	 * The access token you use here must be obtained via OAuth 2 Client Credentials Grant. See {@link OAuth2Operations#authenticateClient()}.
	 * @param clientToken an access token obtained through OAuth 2 client credentials grant with Twitter.
	 */
	public TwitterTemplate(String clientToken) {
		super();
		this.clientRestTemplate = createClientRestTemplate(clientToken);
		initSubApis();
	}

	public TimelineOperations timelineOperations() {
		return timelineOperations;
	}

	public FriendOperations friendOperations() {
		return friendOperations;
	}

	public ListOperations listOperations() {
		return listOperations;
	}

	public SearchOperations searchOperations() {
		return searchOperations;
	}

	public DirectMessageOperations directMessageOperations() {
		return directMessageOperations;
	}

	public UserOperations userOperations() {
		return userOperations;
	}
	
	public BlockOperations blockOperations() {
		return blockOperations;
	}
	
	public GeoOperations geoOperations() {
		return geoOperations;
	}
	
	public RestOperations restOperations() {
		return getRestTemplate();
	}

	// Override getRestTemplate() to return an app-authorized RestTemplate if a client token is available.
	@Override
	public RestTemplate getRestTemplate() {
		if (clientRestTemplate != null) {
			return clientRestTemplate;
		}
		return super.getRestTemplate();
	}
	
	// AbstractOAuth1ApiBinding hooks
	
	@Override
	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		MappingJacksonHttpMessageConverter converter = super.getJsonMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();				
		objectMapper.registerModule(new TwitterModule());
		converter.setObjectMapper(objectMapper);		
		return converter;
	}
	
	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
		restTemplate.setErrorHandler(new TwitterErrorHandler());
	}
	
	// private helper 
	private RestTemplate createClientRestTemplate(String clientToken) {
		RestTemplate restTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
		OAuth2RequestInterceptor interceptor = new OAuth2RequestInterceptor(clientToken, OAuth2Version.BEARER);
		List<ClientHttpRequestInterceptor> interceptors = new LinkedList<ClientHttpRequestInterceptor>();
		interceptors.add(interceptor);
		restTemplate.setInterceptors(interceptors);
		restTemplate.setMessageConverters(getMessageConverters());
		configureRestTemplate(restTemplate);
		return restTemplate;
	}

	private void initSubApis() {
		this.userOperations = new UserTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
		this.directMessageOperations = new DirectMessageTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
		this.friendOperations = new FriendTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
		this.listOperations = new ListTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
		this.timelineOperations = new TimelineTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
		this.searchOperations = new SearchTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
		this.blockOperations = new BlockTemplate(getRestTemplate(), isAuthorized(),isAuthorizedForApp());
		this.geoOperations = new GeoTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
	}

	private boolean isAuthorizedForApp() {
		return clientRestTemplate != null;
	}

	// TODO: This duplicates the class of the same name in Spring Social Core. Consider making the core implementation public.
	private static final class OAuth2RequestInterceptor implements ClientHttpRequestInterceptor {

		private final String accessToken;
		
		private final OAuth2Version oauth2Version;

		public OAuth2RequestInterceptor(String accessToken, OAuth2Version oauth2Version) {
			this.accessToken = accessToken;
			this.oauth2Version = oauth2Version;
		}
		
		public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, ClientHttpRequestExecution execution) throws IOException {
			HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
			protectedResourceRequest.getHeaders().set("Authorization", oauth2Version.getAuthorizationHeaderValue(accessToken));
			return execution.execute(protectedResourceRequest, body);
		}

	}
}
