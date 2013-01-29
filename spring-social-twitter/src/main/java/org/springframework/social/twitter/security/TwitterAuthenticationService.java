package org.springframework.social.twitter.security;

import org.springframework.social.security.provider.OAuth1AuthenticationService;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

public class TwitterAuthenticationService extends OAuth1AuthenticationService<Twitter> {

	public TwitterAuthenticationService(String apiKey, String appSecret) {
		super(new TwitterConnectionFactory(apiKey, appSecret));
	}

}
