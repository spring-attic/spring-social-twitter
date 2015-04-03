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

/**
 * Possible metrics that can be retrieved in a {@link StatisticsSnapshot}.
 * 
 * @author Hudson Mendes
 *
 */
public enum StatisticsMetric {
    conversion_custom(
            StatisticsMetricFamily.CONVERSION,
            StatisticsSegmentationType.PLATFORMS,
            true,
            Integer.class,
            "Count of conversions of type CUSTOM"),

    conversion_downloads(
            StatisticsMetricFamily.CONVERSION,
            StatisticsSegmentationType.PLATFORMS,
            true,
            Integer.class,
            "Count of conversions of type DOWNLOAD"),

    conversion_order_quantity(
            StatisticsMetricFamily.CONVERSION,
            StatisticsSegmentationType.CONVERSION_TAGS,
            true,
            Integer.class,
            "Count of tw_sale_quantity from web event tag"),

    conversion_purchases(
            StatisticsMetricFamily.CONVERSION,
            StatisticsSegmentationType.PLATFORMS,
            true,
            Integer.class,
            "Count of conversions of type PURCHASE"),

    conversion_sale_amount(
            StatisticsMetricFamily.CONVERSION,
            StatisticsSegmentationType.CONVERSION_TAGS,
            true,
            Integer.class,
            "Count of tw_sale_amount from web event tag"),

    conversion_sign_ups(
            StatisticsMetricFamily.CONVERSION,
            StatisticsSegmentationType.PLATFORMS,
            true,
            Integer.class,
            "Count of conversions of type SIGN_UP"),

    conversion_site_visits(
            StatisticsMetricFamily.CONVERSION,
            StatisticsSegmentationType.PLATFORMS,
            true,
            Integer.class,
            "Count of conversions of type SITE_VISIT"),

    promotion_card_responses(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Card engagements"),

    promoted_account_follows(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Total follows for Promoted Account campaigns"),

    billed_engagements(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Total count of billed engagements"),

    billed_follows(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Total count of billed follows"),

    promoted_tweet_profile_clicks(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Catch-all clicks for Promoted Tweet in Profile inventory. Includes any click within the tweet, including favorites and other engagements."),

    promoted_tweet_profile_engagements(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Total count of engagements for Promoted Tweet in Profiles"),

    promoted_tweet_profile_favorites(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of favorites of Promoted Tweet in Profiles"),

    promoted_tweet_profile_follows(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of follows for Promoted Tweet in Profiles"),

    promoted_tweet_profile_replies(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of replies for Promoted Tweet in Profiles"),

    promoted_tweet_profile_retweets(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of Retweets for Promoted Tweet in Profiles"),

    promoted_tweet_profile_url_clicks(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of URL clicks on Promoted Tweet in Profiles"),

    promoted_tweet_search_clicks(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Catch-all clicks for Promoted Tweet in Search inventory. Includes any click within the tweet, including favorites and other engagements."),

    promoted_tweet_search_engagements(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Total count of engagements for Promoted Tweet in Search"),

    promoted_tweet_search_favorites(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of favorites of Promoted Tweet in Search"),

    promoted_tweet_search_follows(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of follows for Promoted Tweet in Search"),

    promoted_tweet_search_replies(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of replies for Promoted Tweet in Search"),

    promoted_tweet_search_retweets(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of Retweets for Promoted Tweet in Search"),

    promoted_tweet_search_url_clicks(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of URL clicks on Promoted Tweet in Search"),

    promoted_tweet_timeline_clicks(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Catch-all clicks for Promoted Tweet in Timeline inventory. Includes any click within the tweet, including favorites and other engagements."),

    promoted_tweet_timeline_engagements(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of overall engagements for Promoted Tweet in Timeline"),

    promoted_tweet_timeline_favorites(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of favorites of Promoted Tweet in Timeline"),

    promoted_tweet_timeline_follows(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of follows for Promoted Tweet in Timeline"),

    promoted_tweet_timeline_replies(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of replies for Promoted Tweet in Timeline"),

    promoted_tweet_timeline_retweets(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of Retweets for Promoted Tweet in Timeline"),

    promoted_tweet_timeline_url_clicks(
            StatisticsMetricFamily.ENGAGEMENT,
            null,
            true,
            Integer.class,
            "Count of URL clicks of Promoted Tweet in Timeline"),

    mobile_conversion_achievement_unlocked(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_add_to_cart(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_added_payment_infos(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_add_to_wishlist(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_checkout_initiated(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_content_views(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_installs(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            "install conversion events"),

    mobile_conversion_invites(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_level_achieved(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_logins(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            "login conversion events"),

    mobile_conversion_purchases(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            "purchase conversion events"),

    mobile_conversion_re_engages(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            "re-engagement conversion events"),

    mobile_conversion_sign_ups(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            "sign-up conversion events"),

    mobile_conversion_rated(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_reservations(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_searches(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_shares(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_spent_credits(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_tutorial_completes(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    mobile_conversion_updates(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            ""),

    promoted_tweet_app_install_attempts(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            "tracks install attempts within the twitter app"),

    promoted_tweet_app_open_attempts(
            StatisticsMetricFamily.MAP,
            null,
            true,
            Integer.class,
            "tracks open attempts within the twitter app"),

    mobile_conversion_achievement_unlocked_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_add_to_cart_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_add_to_wishlist_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_added_payment_infos_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_checkout_initiated_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_content_views_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_installs_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_invites_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_level_achieved_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_logins_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_order_quantity(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "order quantity conversion events"),

    mobile_conversion_purchases_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_rated_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_re_engages_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_reservations_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_sale_amount_local_micro(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "sale amount conversion events"),

    mobile_conversion_searches_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_shares_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_sign_ups_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_spent_credits_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_tutorial_completes_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    mobile_conversion_updates_breakdown(
            StatisticsMetricFamily.MAP,
            null,
            false,
            Object.class,
            "Provides a breakdown where total = post-view + post-engagement + assisted"),

    promoted_video_cta_clicks(
            StatisticsMetricFamily.VIDEO,
            null,
            true,
            Integer.class,
            "Total CTA clicks."),

    promoted_video_replays(
            StatisticsMetricFamily.VIDEO,
            null,
            true,
            Integer.class,
            "Number of times a user elects to re-watch a video."),

    promoted_video_total_views(
            StatisticsMetricFamily.VIDEO,
            null,
            true,
            Integer.class,
            "Total non-unique views for a promoted video where at least 3 seconds was viewed."),

    promoted_video_views_100(
            StatisticsMetricFamily.VIDEO,
            null,
            true,
            Integer.class,
            "Total number of views where 100% of the video was viewed."),

    promoted_video_views_25(
            StatisticsMetricFamily.VIDEO,
            null,
            true,
            Integer.class,
            "Total number of views where at least 25% of the video was viewed."),

    promoted_video_views_50(
            StatisticsMetricFamily.VIDEO,
            null,
            true,
            Integer.class,
            "Total number of views where at least 50% of the video was viewed."),

    promoted_video_views_75(
            StatisticsMetricFamily.VIDEO,
            null,
            true,
            Integer.class,
            "Total number of views where at least 75% of the video was viewed."),

    promoted_account_follow_rate(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Follow rate for Promoted Account campaigns. This is promoted_account_follows / promoted_account_impressions"),

    promoted_account_impressions(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Total impressions for Promoted Account campaigns"),

    promoted_account_profile_visits(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Total profile visits for Promoted Account campaigns"),

    promoted_tweet_search_engagement_rate(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Engagement rate for Promoted Tweet in Search. This is promoted_tweet_search_engagements / promoted_tweet_search_impressions"),

    promoted_tweet_profile_impressions(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Count of impressions for Promoted Tweet in Profiles"),

    promoted_tweet_search_impressions(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Count of impressions for Promoted Tweet in Search"),

    promoted_tweet_timeline_engagement_rate(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Engagement rate for Promoted Tweet in Timeline. This is promoted_tweet_timeline_engagements / promoted_tweet_timeline_impressions"),

    promoted_tweet_timeline_impressions(
            StatisticsMetricFamily.OTHER,
            null,
            true,
            Integer.class,
            "Count of impressions for Promoted Tweet in Timeline"),

    billed_charge_local_micro(
            StatisticsMetricFamily.SPEND,
            null,
            true,
            Integer.class,
            "Spend"),

    promoted_tweet_tpn_card_engagements(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Card engagements from TPN"),

    promoted_tweet_tpn_engagement_rate(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Engagement rate from TPN"),

    promoted_tweet_tpn_engagements(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Total engagements from TPN"),

    promoted_tweet_tpn_clicks(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Total clicks from Twitter Publisher Network (TPN)"),

    promoted_tweet_tpn_favorites(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Total favorites from TPN"),

    promoted_tweet_tpn_follows(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Total follows from TPN"),

    promoted_tweet_tpn_impressions(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Total impressions from TPN"),

    promoted_tweet_tpn_replies(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Total replies from TPN"),

    promoted_tweet_tpn_retweets(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "Total retweets from TPN"),

    promoted_tweet_tpn_url_clicks(
            StatisticsMetricFamily.TPN,
            null,
            true,
            Integer.class,
            "");

    private final StatisticsMetricFamily family;
    private final StatisticsSegmentationType onlyForSegmentation;
    private final Boolean availableForPromotedAccounts;
    private final Class<?> valueType;
    private final String description;

    StatisticsMetric(
            StatisticsMetricFamily family,
            StatisticsSegmentationType onlyForSegmentation,
            Boolean availableForPromotedAccounts,
            Class<?> valueType,
            String description) {

        this.family = family;
        this.onlyForSegmentation = onlyForSegmentation;
        this.availableForPromotedAccounts = availableForPromotedAccounts;
        this.valueType = valueType;
        this.description = description;
    }

    public StatisticsMetricFamily getFamily() {
        return family;
    }

    public StatisticsSegmentationType getOnlyForSegmentation() {
        return onlyForSegmentation;
    }

    public Boolean getAvailableForPromotedAccounts() {
        return availableForPromotedAccounts;
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public String getDescription() {
        return description;
    }
}
