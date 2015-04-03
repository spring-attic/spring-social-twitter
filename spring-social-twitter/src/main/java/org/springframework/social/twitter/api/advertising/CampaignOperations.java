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
 * Interface defining the operations for campaign operations.
 * 
 * @author Hudson Mendes
 */
public interface CampaignOperations {
    /**
     * Retrieves a {@link Campaign} linked to a particular {@link AdvertisingAccount}.
     * 
     * @param accountId identifies the account for which we are attempting to get the campaign
     * @param id identifies the campaign
     * @return an instance of {@link Campaign}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    Campaign getCampaign(String accountId, String id);

    /**
     * Retrieves a list of all {@link Campaign} linked to a particular {@link AdvertisingAccount}.
     * 
     * @param accountId identifies the account for which we are attempting to get the campaigns
     * @param query The query parameters that will filter the request
     * @return a list of {@link Campaign}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    DataListHolder<Campaign> getCampaigns(String accountId, CampaignQuery query);

    /**
     * Creates a {@link Campaign} for a {@link AdvertisingAccount} referenced by its 'accountId'.
     * 
     * @param accountId identifies the account for which we are attempting to create the campaign.
     * @param data is the request data builder that will generate the request body for the operation.
     * @return an instance of {@link Campaign} which refers to the campaign created in the procedure.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    Campaign createCampaign(String accountId, CampaignForm data);

    /**
     * Updates a {@link Campaign} for a {@link AdvertisingAccount} found by its campaignId.
     * 
     * @param accountId identifies the account for which we are attempting to update the campaign.
     * @param id identifies the campaign that we wish to update.
     * @param data is the request data builder that will generate the request body for the operation.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    void updateCampaign(String accountId, String id, CampaignForm data);

    /**
     * Deletes a {@link Campaign} related to an {@link AdvertisingAccount} found by its campaignId.
     * 
     * @param accountId identifies the account of which campaign we wish to delete.
     * @param id identifies the campaign that we desire to delete.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    void deleteCampaign(String accountId, String id);
}
