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
package org.springframework.social.twitter.api.advertising;

import org.springframework.social.twitter.api.TwitterForm;

/**
 * Create Promoted-Only Tweets. The Tweets posted via this
 * endpoint can be used in Promoted Tweets campaigns but will
 * not appear on the public timeline and are not served to
 * followers (see https://dev.twitter.com/ads/reference/post/accounts/%3Aaccount_id/tweet)
 * 
 * @author Hudson Mendes
 */
public interface SponsoredTweetForm extends TwitterForm {

    public SponsoredTweetForm withStatus(String text);

    public SponsoredTweetForm asUser(Long userId);

    public SponsoredTweetForm trimUser(Boolean trimUser);

    public SponsoredTweetForm withMediaIds(Long... mediaIds);
}
