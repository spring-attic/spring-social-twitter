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

/**
 *
 * @author Hudson Mendes
 *
 */
public enum TwitterApiUriResourceForStandard {
    APPLICATION_RATE_LIMIT_STATUS("application/rate_limit_status.json"),

    ACCOUNT_VERIFY_CREDENTIALS("account/verify_credentials.json"),
    ACCOUNT_SETTINGS("account/settings.json"),

    BLOCKS("blocks/list.json"),
    BLOCKS_IDS("blocks/ids.json"),
    BLOCKS_CREATE("blocks/create.json"),
    BLOCKS_DESTROY("blocks/destroy.json"),

    DIRECTMESSAGES("direct_messages.json"),
    DIRECTMESSAGES_SENT("direct_messages/sent.json"),
    DIRECTMESSAGES_SHOW("direct_messages/show.json"),
    DIRECTMESSAGES_NEW("direct_messages/new.json"),
    DIRECTMESSAGES_DESTROY("direct_messages/destroy.json"),

    FRIENDS("friends/list.json"),
    FRIENDS_IDS("friends/ids.json"),

    FOLLOWERS("followers/list.json"),
    FOLLOWERS_IDS("followers/ids.json"),

    FRIENDSHIPS_CREATE("friendships/create.json"),
    FRIENDSHIPS_DESTROY("friendships/destroy.json"),
    FRIENDSHIPS_UPDATE("friendships/update.json"),
    FRIENDSHIPS_INCOMING("friendships/incoming.json"),
    FRIENDSHIPS_OUTGOING("friendships/outgoing.json"),

    LISTS("lists/list.json"),
    LISTS_SHOW("lists/show.json"),
    LISTS_CREATE("lists/create.json"),
    LISTS_UPDATE("lists/update.json"),
    LISTS_DESTROY("lists/destroy.json"),
    LISTS_STATUSES("lists/statuses.json"),

    LISTS_SUBSCRIBERS("lists/subscribers.json"),
    LISTS_SUBSCRIBERS_SHOW("lists/subscribers/show.json"),
    LISTS_SUBSCRIBERS_CREATE("lists/subscribers/create.json"),
    LISTS_SUBSCRIBERS_DESTROY("lists/subscribers/destroy.json"),

    LISTS_MEMBERS("lists/members.json"),
    LISTS_MEMBERS_SHOW("lists/members/show.json"),
    LISTS_MEMBERS_CREATE_ALL("lists/members/create_all.json"),
    LISTS_MEMBERS_DESTROY("lists/members/destroy.json"),

    LISTS_MEMBERSHIPS("lists/memberships.json"),
    LISTS_SUBSCRIPTIONS("lists/subscriptions.json"),

    GEO_ID("geo/id/:place_id.json"),
    GEO_PLACE("geo/place.json"),
    GEO_SEARCH("geo/search.json"),
    GEO_SIMILAR_PLACES("geo/similar_places.json"),
    GEO_REVERSE_GEOCODE("geo/reverse_geocode.json"),

    SAVED_SEARCHES_LIST("saved_searches/list.json"),
    SAVED_SEARCHES_SHOW("saved_searches/show/:search_id.json"),
    SAVED_SEARCHES_CREATE("saved_searches/create.json"),
    SAVED_SEARCHES_DESTROY("saved_searches/destroy/:search_id.json"),

    SEARCH_TWEETS("search/tweets.json"),

    TRENDS_PLACE("trends/place.json"),

    STATUSES_HOME_TIMELINE("statuses/home_timeline.json"),
    STATUSES_USER_TIMELINE("statuses/user_timeline.json"),
    STATUSES_MENTIONS_TIMELINE("statuses/mentions_timeline.json"),
    STATUSES_RETWEETS_OF_ME("statuses/retweets_of_me.json"),
    STATUSES_SHOW("statuses/show/:tweet_id.json"),
    STATUSES_UPDATE("statuses/update.json"),
    STATUSES_UPDATE_WITH_MEDIA("statuses/update_with_media.json"),
    STATUSES_DESTROY("statuses/destroy/:tweet_id.json"),
    STATUSES_RETWEET("statuses/retweet/:tweet_id.json"),
    STATUSES_RETWEETS("statuses/retweets/:tweet_id.json"),
    STATUSES_OEMBEDED("statuses/oembed.json"),

    FAVORITES_LIST("favorites/list.json"),
    FAVORITES_CREATE("favorites/create.json"),
    FAVORITES_DESTROY("favorites/destroy.json"),

    USERS_SHOW("users/show.json"),
    USERS_LOOKUP("users/lookup.json"),
    USERS_SEARCH("users/search.json"),
    USERS_SUGGESTIONS("users/suggestions.json"),
    USERS_SUGGESTIONS_WITH_SLUG("users/suggestions/:slug.json");

    private final String path;

    TwitterApiUriResourceForStandard(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
