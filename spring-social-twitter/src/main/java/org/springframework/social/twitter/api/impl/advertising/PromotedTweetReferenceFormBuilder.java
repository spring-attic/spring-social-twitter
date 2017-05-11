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
package org.springframework.social.twitter.api.impl.advertising;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.PromotedTweetReference;
import org.springframework.social.twitter.api.advertising.PromotedTweetReferenceForm;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Builder for {@link PromotedTweetReference} input form.
 * 
 * @author Hudson Mendes
 */
public class PromotedTweetReferenceFormBuilder extends AbstractTwitterFormBuilder implements PromotedTweetReferenceForm {

    private final List<Long> tweetIds = new ArrayList<>();
    private String lineItemId;

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        appendParameter(params, "line_item_id", lineItemId);
        appendParameter(params, "tweet_ids", this.tweetIds);
        return params;
    }

    @Override
    public PromotedTweetReferenceForm onLineItem(String lineItemId) {
        this.lineItemId = lineItemId;
        return this;
    }

    @Override
    public PromotedTweetReferenceForm forTweets(Long... tweetIds) {
        if (tweetIds != null)
            for (Long tweetId : tweetIds)
                this.tweetIds.add(tweetId);
        return this;
    }

}
