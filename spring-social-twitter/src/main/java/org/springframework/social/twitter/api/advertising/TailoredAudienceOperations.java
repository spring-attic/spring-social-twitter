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

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * Interface defining the operations for targeting audience operations.
 * 
 * @author Hudson Mendes
 */
public interface TailoredAudienceOperations {

    /**
     * Retrieves a {@link TailoredAudience} linked to a particular {@link AdvertisingAccount} referred to by its id.
     * 
     * @param accountId identifies the account for which we wish to get the particular {@link TailoredAudience}.
     * @param id identifies which {@link TailoredAudience} we wish to retrieve.
     * @return an instance of {@link TailoredAudience}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    TailoredAudience getTailoredAudience(String accountId, String id);

    /**
     * Retrieves a list of all {@link TailoredAudience} linked to a particular {@link AdvertisingAccount}.
     * 
     * @param accountId identifies the account for which we want to get the {@link TailoredAudience}.
     * @param query The query parameters that will filter the request
     * @return a list of {@link TailoredAudience}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    DataListHolder<TailoredAudience> getTailoredAudiences(String accountId, TailoredAudienceQuery query);

    /**
     * Creates {@link TailoredAudience} linked to a particular {@link AdvertisingAccount}.
     * 
     * @param accountId identifies the account for which we want to create a {@link TailoredAudience}.
     * @param input is the request data builder that will generate the request body for the operation.
     * @return an instance of {@link TailoredAudience}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    TailoredAudience createTailoredAudience(String accountId, TailoredAudienceForm input);

    /**
     * Deletes a {@link TailoredAudience} related to an {@link AdvertisingAccount} found by its campaignId.
     * 
     * @param accountId identifies the account of which {@link TailoredAudience} we wish to delete.
     * @param id identifies the {@link TailoredAudience} that we desire to delete.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    void deleteTailoredAudience(String accountId, String id);

    /**
     * Updates a {@link TailoredAudience} indicating a TON file and a {@link TailoredAudienceChangeOperation} to a tailored_audience_change
     * (https://dev.twitter.com/ads/reference/post/accounts/%3Aaccount_id/tailored_audience_change)
     * 
     * @param accountId identifies the account for which we sending the change.
     * @param input defines the data used for this tailored audience change.
     * @return a {@link TailoredAudienceChange} describing what the change posted was.
     */
    TailoredAudienceChange createTailoredAudienceChange(String accountId, TailoredAudienceChangeForm input);

    /**
     * Retrieves a {@link TailoredAudienceChange} with information about the file,
     * the operation and its status.
     * (https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/tailored_audience_change/%3Aid)
     * 
     * @param accountId identifies the account for which we sending the change.
     * @param id identifies the file (or change) in the {@link TailoredAudience}.
     * @return the matching {@link TailoredAudienceChange}
     */
    TailoredAudienceChange getTailoredAudienceChange(String accountId, String id);

    /**
     * Creates an entry in the global opt-out list that points
     * to a file of people who will be ignored even if sent
     * as part of tailored audiences.
     * 
     * @param accountId defines the id of the account for which this opt-out post will function.
     * @param input defines the parameters that will be posted.
     * @return the instance of {@link GlobalOptOut} that has been just created.
     */
    GlobalOptOut createGlobalOptOut(String accountId, GlobalOptOutForm input);
}
