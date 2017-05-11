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
package org.springframework.social.twitter.connect;

import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.twitter.api.Twitter;

/**
 * Twitter ConnectionFactory implementation.
 * 
 * @author Keith Donald
 */
public class TwitterConnectionFactory extends OAuth1ConnectionFactory<Twitter> {

    public TwitterConnectionFactory(String consumerKey, String consumerSecret) {
        this(consumerKey, consumerSecret, null, null);
    }

    public TwitterConnectionFactory(String consumerKey, String consumerSecret, String hostForStandardApi, String hostForAdsApi) {
        super("twitter", new TwitterServiceProvider(consumerKey, consumerSecret, hostForStandardApi, hostForAdsApi), new TwitterAdapter());
    }

}
