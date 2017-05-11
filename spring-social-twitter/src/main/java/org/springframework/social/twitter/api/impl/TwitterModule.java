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

import org.springframework.social.twitter.api.AccountSettings;
import org.springframework.social.twitter.api.DirectMessage;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.OEmbedTweet;
import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.SavedSearch;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.SuggestionCategory;
import org.springframework.social.twitter.api.Trend;
import org.springframework.social.twitter.api.Trends;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.social.twitter.api.UserList;
import org.springframework.social.twitter.api.VideoInfoMediaEntity;
import org.springframework.social.twitter.api.VideoInfoVariantMediaEntity;
import org.springframework.social.twitter.api.advertising.AdvertisingAccount;
import org.springframework.social.twitter.api.advertising.AdvertisingAccountPermissions;
import org.springframework.social.twitter.api.advertising.Campaign;
import org.springframework.social.twitter.api.advertising.FundingInstrument;
import org.springframework.social.twitter.api.advertising.GlobalOptOut;
import org.springframework.social.twitter.api.advertising.LineItem;
import org.springframework.social.twitter.api.advertising.LineItemPlacements;
import org.springframework.social.twitter.api.advertising.PromotableUser;
import org.springframework.social.twitter.api.advertising.PromotedTweetReference;
import org.springframework.social.twitter.api.advertising.PromotedUserReference;
import org.springframework.social.twitter.api.advertising.StatisticsSnapshot;
import org.springframework.social.twitter.api.advertising.TailoredAudience;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChange;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForAppStoreCategories;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviorTaxonomies;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviors;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForDevices;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForEvents;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForInterests;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLanguages;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLocations;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForNetworkOperators;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatformVersions;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatforms;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvChannel;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvGenre;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvMarket;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvShow;
import org.springframework.social.twitter.api.advertising.TargetingCriterion;
import org.springframework.social.twitter.api.impl.advertising.AdvertisingAccountMixin;
import org.springframework.social.twitter.api.impl.advertising.AdvertisingAccountPermissionsMixin;
import org.springframework.social.twitter.api.impl.advertising.CampaignMixin;
import org.springframework.social.twitter.api.impl.advertising.FundingInstrumentMixin;
import org.springframework.social.twitter.api.impl.advertising.GlobalOptOutMixin;
import org.springframework.social.twitter.api.impl.advertising.LineItemMixin;
import org.springframework.social.twitter.api.impl.advertising.LineItemPlacementsMixin;
import org.springframework.social.twitter.api.impl.advertising.PromotableUserMixin;
import org.springframework.social.twitter.api.impl.advertising.PromotedTweetReferenceMixin;
import org.springframework.social.twitter.api.impl.advertising.PromotedUserReferenceMixin;
import org.springframework.social.twitter.api.impl.advertising.StatisticsSnapshotMixin;
import org.springframework.social.twitter.api.impl.advertising.TailoredAudienceChangeMixin;
import org.springframework.social.twitter.api.impl.advertising.TailoredAudienceMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForAppStoreCategoriesMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForBehaviorTaxonomiesMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForBehaviorsMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForDevicesMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForEventsMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForInterestsMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForLanguagesMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForLocationsMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForNetworkOperatorsMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForPlatformVersionsMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForPlatformsMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForTvChannelMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForTvGenreMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForTvMarketMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryForTvShowMixin;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriterionMixin;
import org.springframework.social.twitter.api.impl.upload.ImageEntityMixin;
import org.springframework.social.twitter.api.impl.upload.UploadedEntityMixin;
import org.springframework.social.twitter.api.impl.upload.VideoEntityMixin;
import org.springframework.social.twitter.api.upload.ImageEntity;
import org.springframework.social.twitter.api.upload.UploadedEntity;
import org.springframework.social.twitter.api.upload.VideoEntity;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module for registering mixin annotations against Twitter model classes.
 */
@SuppressWarnings("serial")
public class TwitterModule extends SimpleModule {

    public TwitterModule() {
        super("TwitterModule");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(TwitterProfile.class, TwitterProfileMixin.class);
        context.setMixInAnnotations(SavedSearch.class, SavedSearchMixin.class);
        context.setMixInAnnotations(Trend.class, TrendMixin.class);
        context.setMixInAnnotations(Trends.class, TrendsMixin.class);
        context.setMixInAnnotations(SuggestionCategory.class, SuggestionCategoryMixin.class);
        context.setMixInAnnotations(DirectMessage.class, DirectMessageMixin.class);
        context.setMixInAnnotations(UserList.class, UserListMixin.class);
        context.setMixInAnnotations(Tweet.class, TweetMixin.class);
        context.setMixInAnnotations(SearchResults.class, SearchResultsMixin.class);
        context.setMixInAnnotations(Place.class, PlaceMixin.class);
        context.setMixInAnnotations(SimilarPlacesResponse.class, SimilarPlacesMixin.class);
        context.setMixInAnnotations(Entities.class, EntitiesMixin.class);
        context.setMixInAnnotations(HashTagEntity.class, HashTagEntityMixin.class);
        context.setMixInAnnotations(MediaEntity.class, MediaEntityMixin.class);
        context.setMixInAnnotations(VideoInfoMediaEntity.class, VideoInfoEntityMediaMixin.class);
        context.setMixInAnnotations(VideoInfoVariantMediaEntity.class, VideoInfoVariantMediaEntityMixin.class);
        context.setMixInAnnotations(MentionEntity.class, MentionEntityMixin.class);
        context.setMixInAnnotations(UrlEntity.class, UrlEntityMixin.class);

        context.setMixInAnnotations(PromotableUser.class, PromotableUserMixin.class);
        context.setMixInAnnotations(PromotedTweetReference.class, PromotedTweetReferenceMixin.class);
        context.setMixInAnnotations(PromotedUserReference.class, PromotedUserReferenceMixin.class);
        context.setMixInAnnotations(AdvertisingAccount.class, AdvertisingAccountMixin.class);
        context.setMixInAnnotations(AdvertisingAccountPermissions.class, AdvertisingAccountPermissionsMixin.class);
        context.setMixInAnnotations(FundingInstrument.class, FundingInstrumentMixin.class);
        context.setMixInAnnotations(LineItem.class, LineItemMixin.class);
        context.setMixInAnnotations(LineItemPlacements.class, LineItemPlacementsMixin.class);
        context.setMixInAnnotations(Campaign.class, CampaignMixin.class);
        context.setMixInAnnotations(TargetingCriterion.class, TargetingCriterionMixin.class);
        context.setMixInAnnotations(StatisticsSnapshot.class, StatisticsSnapshotMixin.class);
        context.setMixInAnnotations(TailoredAudience.class, TailoredAudienceMixin.class);
        context.setMixInAnnotations(GlobalOptOut.class, GlobalOptOutMixin.class);
        context.setMixInAnnotations(TailoredAudience.class, TailoredAudienceMixin.class);
        context.setMixInAnnotations(TailoredAudienceChange.class, TailoredAudienceChangeMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForAppStoreCategories.class, TargetingCriteriaDiscoveryForAppStoreCategoriesMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForBehaviorTaxonomies.class, TargetingCriteriaDiscoveryForBehaviorTaxonomiesMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForBehaviors.class, TargetingCriteriaDiscoveryForBehaviorsMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForDevices.class, TargetingCriteriaDiscoveryForDevicesMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForEvents.class, TargetingCriteriaDiscoveryForEventsMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForInterests.class, TargetingCriteriaDiscoveryForInterestsMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForLanguages.class, TargetingCriteriaDiscoveryForLanguagesMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForLocations.class, TargetingCriteriaDiscoveryForLocationsMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForNetworkOperators.class, TargetingCriteriaDiscoveryForNetworkOperatorsMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForPlatformVersions.class, TargetingCriteriaDiscoveryForPlatformVersionsMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForPlatforms.class, TargetingCriteriaDiscoveryForPlatformsMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForTvChannel.class, TargetingCriteriaDiscoveryForTvChannelMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForTvGenre.class, TargetingCriteriaDiscoveryForTvGenreMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForTvMarket.class, TargetingCriteriaDiscoveryForTvMarketMixin.class);
        context.setMixInAnnotations(TargetingCriteriaDiscoveryForTvShow.class, TargetingCriteriaDiscoveryForTvShowMixin.class);

        context.setMixInAnnotations(AccountSettings.class, AccountSettingsMixin.class);
        context.setMixInAnnotations(AccountSettings.TimeZone.class, AccountSettingsMixin.TimeZoneMixin.class);
        context.setMixInAnnotations(AccountSettings.SleepTime.class, AccountSettingsMixin.SleepTimeMixin.class);
        context.setMixInAnnotations(AccountSettings.TrendLocation.class, AccountSettingsMixin.TrendLocationMixin.class);

        context.setMixInAnnotations(UploadedEntity.class, UploadedEntityMixin.class);
        context.setMixInAnnotations(ImageEntity.class, ImageEntityMixin.class);
        context.setMixInAnnotations(VideoEntity.class, VideoEntityMixin.class);

        context.setMixInAnnotations(OEmbedTweet.class, OEmbedTweetMixin.class);
    }

}
