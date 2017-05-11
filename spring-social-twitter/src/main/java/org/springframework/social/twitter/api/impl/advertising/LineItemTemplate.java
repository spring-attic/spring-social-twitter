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

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.social.twitter.api.advertising.LineItem;
import org.springframework.social.twitter.api.advertising.LineItemForm;
import org.springframework.social.twitter.api.advertising.LineItemOperations;
import org.springframework.social.twitter.api.advertising.LineItemPlacements;
import org.springframework.social.twitter.api.advertising.LineItemPlacementsQuery;
import org.springframework.social.twitter.api.advertising.LineItemQuery;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.DataListHolder;
import org.springframework.social.twitter.api.impl.DataSingleHolder;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForHttpEntity;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForAdvertising;
import org.springframework.web.client.RestTemplate;

public class LineItemTemplate extends AbstractTwitterOperations implements LineItemOperations {
    private final RestTemplate restTemplate;

    public LineItemTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public LineItem getLineItem(String accountId, String id) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.LINE_ITEM)
                        .withArgument("account_id", accountId)
                        .withArgument("line_item_id", id)
                        .withArgument("with_deleted", true)
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataSingleHolder<LineItem>>() {}
                ).getBody().getData();
    }

    @Override
    public DataListHolder<LineItem> getLineItems(String accountId, LineItemQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.LINE_ITEMS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<LineItem>>() {}
                ).getBody();
    }

    @Override
    public LineItem createLineItem(String accountId, LineItemForm data) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.LINE_ITEMS)
                        .withArgument("account_id", accountId)
                        .build(),
                HttpMethod.POST,
                new TwitterApiBuilderForHttpEntity<>(data.toRequestBody()).build(),
                new ParameterizedTypeReference<DataSingleHolder<LineItem>>() {}
                ).getBody().getData();
    }

    @Override
    public void updateLineItem(String accountId, String id, LineItemForm data) {
        requireUserAuthorization();
        restTemplate.put(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.LINE_ITEM)
                        .withArgument("account_id", accountId)
                        .withArgument("line_item_id", id)
                        .build(),
                data.toRequestBody());
    }

    @Override
    public void deleteLineItem(String accountId, String id) {
        requireUserAuthorization();
        restTemplate.delete(new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.LINE_ITEM)
                .withArgument("account_id", accountId)
                .withArgument("line_item_id", id)
                .build());
    }

	@Override
	public DataListHolder<LineItemPlacements> getLineItemPlacements(LineItemPlacementsQuery query) {
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.LINE_ITEM_PLACEMENTS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<LineItemPlacements>>() {}
                ).getBody();
	}

}
