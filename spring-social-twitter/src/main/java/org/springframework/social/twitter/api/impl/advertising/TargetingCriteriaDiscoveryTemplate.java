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
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForAppStoreCategories;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForAppStoreCategoriesQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviorTaxonomies;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviorTaxonomiesQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviors;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviorsQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForDevices;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForDevicesQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForEvents;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForEventsQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForInterests;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForInterestsQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLanguages;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLanguagesQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLocations;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLocationsQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForNetworkOperators;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForNetworkOperatorsQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatformVersions;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatformVersionsQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatforms;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatformsQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvChannel;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvChannelQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvGenre;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvGenreQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvMarket;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvMarketQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvShow;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvShowQuery;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryOperations;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.DataListHolder;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForAdvertising;
import org.springframework.web.client.RestTemplate;

public class TargetingCriteriaDiscoveryTemplate extends AbstractTwitterOperations implements TargetingCriteriaDiscoveryOperations {
    private final RestTemplate restTemplate;

    public TargetingCriteriaDiscoveryTemplate(RestTemplate restTemplate, boolean isUserAuthorized, boolean isAppAuthorized) {
        super(isUserAuthorized, isAppAuthorized);
        this.restTemplate = restTemplate;
    }

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForAppStoreCategories> appStoreCategories(TargetingCriteriaDiscoveryForAppStoreCategoriesQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_APP_STORE_CATEGORIES)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForAppStoreCategories>>() {}
                ).getBody();
	}

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForBehaviorTaxonomies> behaviorTaxonomies(TargetingCriteriaDiscoveryForBehaviorTaxonomiesQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_BEHAVIOR_TAXONOMIES)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForBehaviorTaxonomies>>() {}
                ).getBody();
	}

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForBehaviors> behaviors(TargetingCriteriaDiscoveryForBehaviorsQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_BEHAVIORS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForBehaviors>>() {}
                ).getBody();
	}

    @Override
    public DataListHolder<TargetingCriteriaDiscoveryForDevices> devices(TargetingCriteriaDiscoveryForDevicesQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_DEVICES)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForDevices>>() {}
                ).getBody();
    }

    @Override
    public DataListHolder<TargetingCriteriaDiscoveryForEvents> events(TargetingCriteriaDiscoveryForEventsQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_EVENTS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForEvents>>() {}
                ).getBody();
    }

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForInterests> interests(TargetingCriteriaDiscoveryForInterestsQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_INTERESTS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForInterests>>() {}
                ).getBody();
	}

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForLanguages> languages(TargetingCriteriaDiscoveryForLanguagesQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_LANGUAGES)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForLanguages>>() {}
                ).getBody();
	}

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForLocations> locations(TargetingCriteriaDiscoveryForLocationsQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_LOCATIONS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForLocations>>() {}
                ).getBody();
	}

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForNetworkOperators> networkOperators(TargetingCriteriaDiscoveryForNetworkOperatorsQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_NETWORK_OPERATORS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForNetworkOperators>>() {}
                ).getBody();
	}

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForPlatformVersions> platformVersions(TargetingCriteriaDiscoveryForPlatformVersionsQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_PLATFORM_VERSIONS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForPlatformVersions>>() {}
                ).getBody();
	}

	@Override
	public DataListHolder<TargetingCriteriaDiscoveryForPlatforms> platforms(TargetingCriteriaDiscoveryForPlatformsQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_PLATFORMS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForPlatforms>>() {}
                ).getBody();
	}

    @Override
    public DataListHolder<TargetingCriteriaDiscoveryForTvShow> tvShow(TargetingCriteriaDiscoveryForTvShowQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_TV_SHOWS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForTvShow>>() {}
                ).getBody();
    }

    @Override
    public DataListHolder<TargetingCriteriaDiscoveryForTvMarket> tvMarkets(TargetingCriteriaDiscoveryForTvMarketQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_TV_MARKETS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForTvMarket>>() {}
                ).getBody();
    }

    @Override
    public DataListHolder<TargetingCriteriaDiscoveryForTvGenre> tvGenres(TargetingCriteriaDiscoveryForTvGenreQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_TV_GENRES)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForTvGenre>>() {}
                ).getBody();
    }

    @Override
    public DataListHolder<TargetingCriteriaDiscoveryForTvChannel> tvChannels(TargetingCriteriaDiscoveryForTvChannelQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETINGS_DISCOVERY_TV_CHANNELS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriteriaDiscoveryForTvChannel>>() {}
                ).getBody();
    }

}
