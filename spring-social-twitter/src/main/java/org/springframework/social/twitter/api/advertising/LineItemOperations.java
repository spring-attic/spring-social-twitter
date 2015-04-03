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
 * Interface defining the operations for line item (Ads API).
 * 
 * @author Hudson Mendes
 */
public interface LineItemOperations {

    /**
     * Retrieves a {@link LineItem} linked to a particular {@link AdvertisingAccount} referred to by its id.
     * 
     * @param query Scope the results by a specific product type.
     * @return a list of {@link LineItemPlacements}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
	DataListHolder<LineItemPlacements> getLineItemPlacements(LineItemPlacementsQuery query);

    /**
     * Retrieves a {@link LineItem} linked to a particular {@link AdvertisingAccount} referred to by its id.
     * 
     * @param accountId identifies the account for which we wish to get the particular line item.
     * @param id identifies which line id we wish to retrieve.
     * @return an instance of {@link LineItem}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    LineItem getLineItem(String accountId, String id);

    /**
     * Retrieves a list of all {@link LineItem} linked to a particular {@link AdvertisingAccount}.
     * 
     * @param accountId identifies the account for which we want to get the line items.
     * @param query The query parameters that will filter the request
     * @return a list of {@link LineItem}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    DataListHolder<LineItem> getLineItems(String accountId, LineItemQuery query);

    /**
     * Creates {@link LineItem} linked to a particular {@link AdvertisingAccount}.
     * 
     * @param accountId identifies the account for which we want to create a line item.
     * @param data is the request data builder that will generate the request body for the operation.
     * @return an instance of {@link LineItem}
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    LineItem createLineItem(String accountId, LineItemForm data);

    /**
     * Updates {@link LineItem} linked to a particular {@link AdvertisingAccount} referred to by its id.
     * 
     * @param accountId identifies the account for which we want to update a line item.
     * @param id identifies which line id we wish to update.
     * @param data is the request data builder that will generate the request body for the operation.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    void updateLineItem(String accountId, String id, LineItemForm data);

    /**
     * Deletes a {@link LineItem} related to an {@link AdvertisingAccount} found by its campaignId.
     * 
     * @param accountId identifies the account of which line item we wish to delete.
     * @param id identifies the line item that we desire to delete.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    void deleteLineItem(String accountId, String id);

}
