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

import org.springframework.social.twitter.api.advertising.SponsoredTweetForm;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Hudson Mendes
 */
public class SponsoredTweetFormBuilder extends AbstractTwitterFormBuilder implements SponsoredTweetForm {

    private String status;
    private Long asUserId;
    private Boolean trimUser;
    private final List<Long> mediaIds = new ArrayList<>();

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        appendParameter(map, "status", this.status);
        appendParameter(map, "as_user_id", this.asUserId);
        appendParameter(map, "trim_user", this.trimUser);
        appendParameter(map, "media_ids", this.mediaIds);
        return map;
    }

    @Override
    public SponsoredTweetForm withStatus(String text) {
        this.status = text;
        return this;
    }

    @Override
    public SponsoredTweetForm asUser(Long userId) {
        this.asUserId = userId;
        return this;
    }

    @Override
    public SponsoredTweetForm trimUser(Boolean trimUser) {
        this.trimUser = trimUser;
        return this;
    }

    @Override
    public SponsoredTweetForm withMediaIds(Long... mediaIds) {
        for (Long mediaId : mediaIds)
            this.mediaIds.add(mediaId);
        return this;
    }
}
