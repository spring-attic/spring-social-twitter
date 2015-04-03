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
package org.springframework.social.twitter.api.impl;

import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.AccountSettings;
import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;
import org.springframework.social.twitter.api.SuggestionCategory;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the {@link UserOperations} interface providing binding to Twitters' user-oriented REST resources.
 * 
 * @author Craig Walls
 */
public class UserTemplate extends AbstractTwitterOperations implements UserOperations {

    private final RestTemplate restTemplate;

    public UserTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public long getProfileId() {
        requireUserAuthorization();
        return getUserProfile().getId();
    }

    @Override
    public String getScreenName() {
        requireUserAuthorization();
        return getUserProfile().getScreenName();
    }

    @Override
    public TwitterProfile getUserProfile() {
        requireUserAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.ACCOUNT_VERIFY_CREDENTIALS)
                        .build(),
                TwitterProfile.class);
    }

    @Override
    public TwitterProfile getUserProfile(String screenName) {
        requireEitherUserOrAppAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.USERS_SHOW)
                        .withArgument("screen_name", screenName)
                        .build(),
                TwitterProfile.class);
    }

    @Override
    public TwitterProfile getUserProfile(long userId) {
        requireEitherUserOrAppAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.USERS_SHOW)
                        .withArgument("user_id", String.valueOf(userId))
                        .build(),
                TwitterProfile.class);
    }

    @Override
    public List<TwitterProfile> getUsers(long... userIds) {
        requireEitherUserOrAppAuthorization();
        String joinedIds = ArrayUtils.join(userIds);
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.USERS_LOOKUP)
                        .withArgument("user_id", joinedIds)
                        .build(),
                TwitterProfileList.class);
    }

    @Override
    public List<TwitterProfile> getUsers(String... screenNames) {
        requireEitherUserOrAppAuthorization();
        String joinedScreenNames = ArrayUtils.join(screenNames);
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.USERS_LOOKUP)
                        .withArgument("screen_name", joinedScreenNames)
                        .build(),
                TwitterProfileList.class);
    }

    @Override
    public List<TwitterProfile> searchForUsers(String query) {
        return searchForUsers(query, 1, 20);
    }

    @Override
    public List<TwitterProfile> searchForUsers(String query, int page, int pageSize) {
        requireUserAuthorization();

        MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, 0, 0);
        parameters.set("q", query);

        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.USERS_SEARCH)
                        .withArgument(parameters)
                        .build(),
                TwitterProfileList.class);
    }

    @Override
    public List<SuggestionCategory> getSuggestionCategories() {
        requireEitherUserOrAppAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.USERS_SUGGESTIONS)
                        .build(),
                SuggestionCategoryList.class);
    }

    @Override
    public List<TwitterProfile> getSuggestions(String slug) {
        requireEitherUserOrAppAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.USERS_SUGGESTIONS_WITH_SLUG)
                        .withArgument("slug", slug)
                        .build(),
                TwitterProfileUsersList.class
                ).getList();
    }

    @Override
    public Map<ResourceFamily, List<RateLimitStatus>> getRateLimitStatus(ResourceFamily... resources) {
        requireEitherUserOrAppAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.APPLICATION_RATE_LIMIT_STATUS)
                        .withArgument("resources", ArrayUtils.join(resources))
                        .build(),
                RateLimitStatusHolder.class
                ).getRateLimits();
    }

    @Override
    public AccountSettings getAccountSettings() {
        requireUserAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.ACCOUNT_SETTINGS)
                        .build(),
                AccountSettings.class);
    }

    @Override
    public AccountSettings updateAccountSettings(AccountSettingsData accountSettingsData) {
        requireUserAuthorization();
        return restTemplate.postForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.ACCOUNT_SETTINGS)
                        .build(),
                accountSettingsData.toRequestParameters(),
                AccountSettings.class);
    }

}
