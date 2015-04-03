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

import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.twitter.api.BlockOperations;
import org.springframework.social.twitter.api.DirectMessageOperations;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.social.twitter.api.GeoOperations;
import org.springframework.social.twitter.api.ListOperations;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.Settings;
import org.springframework.social.twitter.api.StreamingOperations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.social.twitter.api.advertising.AdvertisingOperations;
import org.springframework.social.twitter.api.advertising.CampaignOperations;
import org.springframework.social.twitter.api.advertising.LineItemOperations;
import org.springframework.social.twitter.api.advertising.PromotionOperations;
import org.springframework.social.twitter.api.advertising.StatisticsOperations;
import org.springframework.social.twitter.api.advertising.TailoredAudienceOperations;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryOperations;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaOperations;
import org.springframework.social.twitter.api.impl.advertising.AdvertisingTemplate;
import org.springframework.social.twitter.api.impl.advertising.CampaignTemplate;
import org.springframework.social.twitter.api.impl.advertising.LineItemTemplate;
import org.springframework.social.twitter.api.impl.advertising.PromotionTemplate;
import org.springframework.social.twitter.api.impl.advertising.StatisticsTemplate;
import org.springframework.social.twitter.api.impl.advertising.TailoredAudienceTemplate;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaDiscoveryTemplate;
import org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaTemplate;
import org.springframework.social.twitter.api.impl.ton.TonTemplate;
import org.springframework.social.twitter.api.impl.upload.UploadTemplate;
import org.springframework.social.twitter.api.ton.TonOperations;
import org.springframework.social.twitter.api.upload.UploadOperations;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the central class for interacting with Twitter.
 * <p>
 * Most (not all) Twitter operations require OAuth authentication. To perform such operations, {@link TwitterTemplate} must be constructed with the
 * minimal amount of information required to sign requests to Twitter's API with an OAuth <code>Authorization</code> header.
 * </p>
 * 
 * @author Craig Walls
 */
public class TwitterTemplate extends AbstractOAuth1ApiBinding implements Twitter {

    private TimelineOperations timelineOperations;

    private UserOperations userOperations;

    private FriendOperations friendOperations;

    private ListOperations listOperations;

    private SearchOperations searchOperations;

    private DirectMessageOperations directMessageOperations;

    private BlockOperations blockOperations;

    private GeoOperations geoOperations;

    private StreamingOperations streamOperations;

    private AdvertisingOperations advertisingOperations;

    private PromotionOperations promotionOperations;

    private StatisticsOperations statisticsOperations;

    private CampaignOperations campaignOperations;

    private LineItemOperations lineItemOperations;

    private TargetingCriteriaOperations targetingCriteriaOperations;

    private TargetingCriteriaDiscoveryOperations targetingCriteriaDiscoveryOperations;

    private TailoredAudienceOperations tailoredAudienceOperations;

    private TonOperations tonOperations;

    private UploadOperations uploadOperations;

    private RestTemplate clientRestTemplate = null;

    private Settings settings = null;

    /**
     * Create a new instance of TwitterTemplate.
     * 
     * @param consumerKey the application's API key
     * @param consumerSecret the application's API secret
     * @param accessToken an access token acquired through OAuth authentication with Twitter
     * @param accessTokenSecret an access token secret acquired through OAuth authentication with Twitter
     * @param hostForStandardApi defines the host for which standard api calls will be pointed
     * @param hostForAdsApi defines the host for which ads api calls will be pointed.
     */
    public TwitterTemplate(
            String consumerKey,
            String consumerSecret,
            String accessToken,
            String accessTokenSecret,
            String hostForStandardApi,
            String hostForAdsApi) {
        super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        initSubApis();
        initHosts(hostForStandardApi, hostForAdsApi);
    }

    /**
     * Create a new instance of TwitterTemplate.
     * 
     * @param consumerKey the application's API key
     * @param consumerSecret the application's API secret
     * @param accessToken an access token acquired through OAuth authentication with Twitter
     * @param accessTokenSecret an access token secret acquired through OAuth authentication with Twitter
     */
    public TwitterTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        initSubApis();
        initHosts(null, null);
    }

    /**
     * Create a new instance of TwitterTemplate.
     * This instance of TwitterTemplate is limited to only performing operations requiring client authorization.
     * For instance, you can use it to search Twitter, but you cannot use it to post a status update.
     * The access token you use here must be obtained via OAuth 2 Client Credentials Grant. See {@link OAuth2Operations#authenticateClient()}.
     * 
     * @param clientToken an access token obtained through OAuth 2 client credentials grant with Twitter.
     */
    public TwitterTemplate(String clientToken) {
        super();
        Assert.notNull(clientToken, "Constructor argument 'clientToken' cannot be null.");
        this.clientRestTemplate = createClientRestTemplate(clientToken);
        initSubApis();
        initHosts(null, null);
    }

    /**
     * Create a new instance of TwitterTemplate.
     * This instance of TwitterTemplate is limited to only performing operations requiring client authorization.
     * For instance, you can use it to search Twitter, but you cannot use it to post a status update.
     * The client credentials given here are used to obtain a client access token via OAuth 2 Client Credentials Grant. See
     * {@link OAuth2Operations#authenticateClient()}.
     * 
     * @param consumerKey the application's API key
     * @param consumerSecret the application's API secret
     */
    public TwitterTemplate(String consumerKey, String consumerSecret) {
        this(exchangeCredentialsForClientToken(consumerKey, consumerSecret));
    }

    @Override
    /**
     * Add additional media types for TON upload
     */
    protected ByteArrayHttpMessageConverter getByteArrayMessageConverter() {
        ByteArrayHttpMessageConverter converter = new ByteArrayHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.IMAGE_JPEG, MediaType.IMAGE_GIF, MediaType.IMAGE_PNG));
        return converter;
    }

    @Override
    public TimelineOperations timelineOperations() {
        return timelineOperations;
    }

    @Override
    public FriendOperations friendOperations() {
        return friendOperations;
    }

    @Override
    public ListOperations listOperations() {
        return listOperations;
    }

    @Override
    public SearchOperations searchOperations() {
        return searchOperations;
    }

    @Override
    public DirectMessageOperations directMessageOperations() {
        return directMessageOperations;
    }

    @Override
    public UserOperations userOperations() {
        return userOperations;
    }

    @Override
    public BlockOperations blockOperations() {
        return blockOperations;
    }

    @Override
    public GeoOperations geoOperations() {
        return geoOperations;
    }

    @Override
    public StreamingOperations streamingOperations() {
        return streamOperations;
    }

    @Override
    public AdvertisingOperations advertisingOperations() {
        return advertisingOperations;
    }

    @Override
    public PromotionOperations promotionOperations() {
        return promotionOperations;
    }

    @Override
    public StatisticsOperations statisticsOperations() {
        return statisticsOperations;
    }

    @Override
    public CampaignOperations campaignOperations() {
        return campaignOperations;
    }

    @Override
    public LineItemOperations lineItemOperations() {
        return lineItemOperations;
    }

    @Override
    public TargetingCriteriaOperations targetingCriteriaOperations() {
        return targetingCriteriaOperations;
    }

    @Override
    public TailoredAudienceOperations tailoredAudienceOperations() {
        return tailoredAudienceOperations;
    }

    @Override
    public TargetingCriteriaDiscoveryOperations targetingCriteriaDiscoveryOperations() {
        return targetingCriteriaDiscoveryOperations;
    }

    @Override
    public TonOperations tonOperations() {
        return tonOperations;
    }

    @Override
    public UploadOperations uploadOperations() {
        return uploadOperations;
    }

    @Override
    public Settings settings() {
        return settings;
    }

    @Override
    public RestOperations restOperations() {
        return getRestTemplate();
    }

    // Override getRestTemplate() to return an app-authorized RestTemplate if a client token is available.
    @Override
    public RestTemplate getRestTemplate() {
        if (clientRestTemplate != null) {
            return clientRestTemplate;
        }
        return super.getRestTemplate();
    }

    // AbstractOAuth1ApiBinding hooks

    @Override
    protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        MappingJackson2HttpMessageConverter converter = super.getJsonMessageConverter();
        converter.setObjectMapper(new ObjectMapper().registerModule(new TwitterModule()));
        return converter;
    }

    @Override
    protected FormHttpMessageConverter getFormMessageConverter() {
        return new TwitterEscapingFormHttpMessageConverter();
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new TwitterErrorHandler());
    }

    // private helper 
    private static String exchangeCredentialsForClientToken(String consumerKey, String consumerSecret) {
        OAuth2Template oauth2 = new OAuth2Template(consumerKey, consumerSecret, "", "https://api.twitter.com/oauth2/token");
        return oauth2.authenticateClient().getAccessToken();
    }

    private RestTemplate createClientRestTemplate(String clientToken) {
        RestTemplate restTemplate = new ClientAuthorizedTwitterTemplate(clientToken).getRestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());
        configureRestTemplate(restTemplate);
        return restTemplate;
    }

    private void initSubApis() {
        this.userOperations = new UserTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.directMessageOperations = new DirectMessageTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.friendOperations = new FriendTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.listOperations = new ListTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.timelineOperations = new TimelineTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.searchOperations = new SearchTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.blockOperations = new BlockTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.geoOperations = new GeoTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());
        this.streamOperations = new StreamingTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp());

        this.advertisingOperations = new AdvertisingTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.promotionOperations = new PromotionTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.statisticsOperations = new StatisticsTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.campaignOperations = new CampaignTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.lineItemOperations = new LineItemTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.targetingCriteriaOperations = new TargetingCriteriaTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.targetingCriteriaDiscoveryOperations = new TargetingCriteriaDiscoveryTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.tailoredAudienceOperations = new TailoredAudienceTemplate(getRestTemplate(), isAuthorized(), isAuthorized());
        this.tonOperations = new TonTemplate(getRestTemplate(), isAuthorized(), isAuthorized());

        this.uploadOperations = new UploadTemplate(getRestTemplate(), isAuthorized(), isAuthorized());

        this.settings = new SettingsImpl();
    }

    private void initHosts(String standardApiHost, String adsApiHost) {
        TwitterApiHosts.setStandardApiHost(standardApiHost);
        TwitterApiHosts.setAdsApiHost(adsApiHost);
    }

    private boolean isAuthorizedForApp() {
        return clientRestTemplate != null;
    }

}