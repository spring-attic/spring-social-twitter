/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.social.twitter.api.AccountSettings;
import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;
import org.springframework.social.twitter.api.SuggestionCategory;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.DeliveryDevice.Device;

/**
 * @author Craig Walls
 */
public class UserTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void getProfileId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/verify_credentials.json"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		assertEquals(161064614, twitter.userOperations().getProfileId());
	}

	@Test
	public void getScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/verify_credentials.json"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		assertEquals("artnames", twitter.userOperations().getScreenName());
	}

	@Test
	public void getUserProfile() throws Exception {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/verify_credentials.json"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));

		TwitterProfile profile = twitter.userOperations().getUserProfile();
		assertEquals(161064614, profile.getId());
		assertEquals("artnames", profile.getScreenName());
		assertEquals("Art Names", profile.getName());
		assertEquals("I'm just a normal kinda guy", profile.getDescription());
		assertEquals("Denton, TX", profile.getLocation());
		assertEquals("http://www.springsource.org", profile.getUrl());
		assertEquals("http://a1.twimg.com/sticky/default_profile_images/default_profile_4_normal.png", profile.getProfileImageUrl());
		assertTrue(profile.isNotificationsEnabled());
		assertFalse(profile.isVerified());
		assertTrue(profile.isGeoEnabled());
		assertTrue(profile.isContributorsEnabled());
		assertTrue(profile.isTranslator());
		assertTrue(profile.isFollowing());
		assertTrue(profile.isFollowRequestSent());
		assertTrue(profile.isProtected());
		assertEquals("en", profile.getLanguage());
		assertEquals(125, profile.getStatusesCount());
		assertEquals(1001, profile.getListedCount());
		assertEquals(14, profile.getFollowersCount());
		assertEquals(194, profile.getFriendsCount());
		assertEquals(4, profile.getFavoritesCount());
		assertEquals("Mountain Time (US & Canada)", profile.getTimeZone());
		assertEquals(-25200, profile.getUtcOffset());
		assertTrue(profile.useBackgroundImage());
		assertEquals("C0DEED", profile.getSidebarBorderColor());
		assertEquals("DDEEF6", profile.getSidebarFillColor());
		assertEquals("C0DEED", profile.getBackgroundColor());
		assertEquals("http://a3.twimg.com/a/1301419075/images/themes/theme1/bg.png", profile.getBackgroundImageUrl());
		assertFalse(profile.isBackgroundImageTiled());
		assertEquals("333333", profile.getTextColor());
		assertEquals("0084B4", profile.getLinkColor());
	}
	
	@Test
	public void getUserProfile_userId() throws Exception {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/show.json?user_id=12345"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));

		TwitterProfile profile = twitter.userOperations().getUserProfile(12345);
		assertEquals(161064614, profile.getId());
		assertEquals("artnames", profile.getScreenName());
		assertEquals("Art Names", profile.getName());
		assertEquals("I'm just a normal kinda guy", profile.getDescription());
		assertEquals("Denton, TX", profile.getLocation());
		assertEquals("http://www.springsource.org", profile.getUrl());
		assertEquals("http://a1.twimg.com/sticky/default_profile_images/default_profile_4_normal.png", profile.getProfileImageUrl());
	}

	@Test
	public void getUserProfile_userId_appAuthorization() throws Exception {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/users/show.json?user_id=12345"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));

		TwitterProfile profile = appAuthTwitter.userOperations().getUserProfile(12345);
		assertEquals(161064614, profile.getId());
		assertEquals("artnames", profile.getScreenName());
		assertEquals("Art Names", profile.getName());
		assertEquals("I'm just a normal kinda guy", profile.getDescription());
		assertEquals("Denton, TX", profile.getLocation());
		assertEquals("http://www.springsource.org", profile.getUrl());
		assertEquals("http://a1.twimg.com/sticky/default_profile_images/default_profile_4_normal.png", profile.getProfileImageUrl());
	}

	@Test
	public void getUserProfile_screenName() throws Exception {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/show.json?screen_name=artnames"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));

		TwitterProfile profile = twitter.userOperations().getUserProfile("artnames");
		assertEquals(161064614, profile.getId());
		assertEquals("artnames", profile.getScreenName());
		assertEquals("Art Names", profile.getName());
		assertEquals("I'm just a normal kinda guy", profile.getDescription());
		assertEquals("Denton, TX", profile.getLocation());
		assertEquals("http://www.springsource.org", profile.getUrl());
		assertEquals("http://a1.twimg.com/sticky/default_profile_images/default_profile_4_normal.png", profile.getProfileImageUrl());
	}

	@Test
	public void getUserProfile_screenName_appAuthorization() throws Exception {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/users/show.json?screen_name=artnames"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));

		TwitterProfile profile = appAuthTwitter.userOperations().getUserProfile("artnames");
		assertEquals(161064614, profile.getId());
		assertEquals("artnames", profile.getScreenName());
		assertEquals("Art Names", profile.getName());
		assertEquals("I'm just a normal kinda guy", profile.getDescription());
		assertEquals("Denton, TX", profile.getLocation());
		assertEquals("http://www.springsource.org", profile.getUrl());
		assertEquals("http://a1.twimg.com/sticky/default_profile_images/default_profile_4_normal.png", profile.getProfileImageUrl());
	}

	@Test
	public void getUsers_byUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		List<TwitterProfile> users = twitter.userOperations().getUsers(14846645, 14718006);
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}

	@Test
	public void getUsers_byUserId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?user_id=14846645%2C14718006"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		List<TwitterProfile> users = appAuthTwitter.userOperations().getUsers(14846645, 14718006);
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}
	
	@Test
	public void getUsers_byScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?screen_name=royclarkson%2Ckdonald"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		List<TwitterProfile> users = twitter.userOperations().getUsers("royclarkson", "kdonald");
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}

	@Test
	public void getUsers_byScreenName_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/users/lookup.json?screen_name=royclarkson%2Ckdonald"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		List<TwitterProfile> users = appAuthTwitter.userOperations().getUsers("royclarkson", "kdonald");
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}

	@Test
	public void searchForUsers() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/search.json?page=1&count=20&q=some+query"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		List<TwitterProfile> users = twitter.userOperations().searchForUsers("some query");
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}

	@Test
	public void searchForUsers_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/search.json?page=3&count=35&q=some+query"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("list-of-profiles"), APPLICATION_JSON));
		List<TwitterProfile> users = twitter.userOperations().searchForUsers("some query", 3, 35);
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}

	@Test
	public void getSuggestionCategories() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/suggestions.json"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("suggestion-categories"), APPLICATION_JSON));
		List<SuggestionCategory> categories = twitter.userOperations().getSuggestionCategories();
		assertEquals(4, categories.size());
		assertEquals("Art & Design", categories.get(0).getName());
		assertEquals("art-design", categories.get(0).getSlug());
		assertEquals(56, categories.get(0).getSize());
		assertEquals("Books", categories.get(1).getName());
		assertEquals("books", categories.get(1).getSlug());
		assertEquals(72, categories.get(1).getSize());
		assertEquals("Business", categories.get(2).getName());
		assertEquals("business", categories.get(2).getSlug());
		assertEquals(65, categories.get(2).getSize());
		assertEquals("Twitter", categories.get(3).getName());
		assertEquals("twitter", categories.get(3).getSlug());
		assertEquals(16, categories.get(3).getSize());
	}

	@Test
	public void getSuggestionCategories_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/users/suggestions.json"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("suggestion-categories"), APPLICATION_JSON));
		List<SuggestionCategory> categories = appAuthTwitter.userOperations().getSuggestionCategories();
		assertEquals(4, categories.size());
		assertEquals("Art & Design", categories.get(0).getName());
		assertEquals("art-design", categories.get(0).getSlug());
		assertEquals(56, categories.get(0).getSize());
		assertEquals("Books", categories.get(1).getName());
		assertEquals("books", categories.get(1).getSlug());
		assertEquals(72, categories.get(1).getSize());
		assertEquals("Business", categories.get(2).getName());
		assertEquals("business", categories.get(2).getSlug());
		assertEquals(65, categories.get(2).getSize());
		assertEquals("Twitter", categories.get(3).getName());
		assertEquals("twitter", categories.get(3).getSlug());
		assertEquals(16, categories.get(3).getSize());
	}

	@Test
	public void getSuggestions() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/users/suggestions/springsource.json"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("suggestions"), APPLICATION_JSON));

		List<TwitterProfile> users = twitter.userOperations().getSuggestions("springsource");
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}

	@Test
	public void getSuggestions_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/users/suggestions/springsource.json"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("suggestions"), APPLICATION_JSON));

		List<TwitterProfile> users = appAuthTwitter.userOperations().getSuggestions("springsource");
		assertEquals(2, users.size());
		assertEquals("royclarkson", users.get(0).getScreenName());
		assertEquals("kdonald", users.get(1).getScreenName());
	}

	@Test
	public void getRateLimit() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/application/rate_limit_status.json?resources=help%2Csearch"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("rate-limit-status"), APPLICATION_JSON));
		
		Map<ResourceFamily, List<RateLimitStatus>> statuses = twitter.userOperations().getRateLimitStatus(ResourceFamily.HELP,ResourceFamily.SEARCH);
		assertTrue(statuses.containsKey(ResourceFamily.SEARCH));
		assertTrue(statuses.containsKey(ResourceFamily.HELP));
		assertEquals(1, statuses.get(ResourceFamily.SEARCH).size());
		assertEquals(4, statuses.get(ResourceFamily.HELP).size());
		assertEquals("/help/privacy", statuses.get(ResourceFamily.HELP).get(0).getEndpoint());
		assertEquals(15, statuses.get(ResourceFamily.HELP).get(0).getQuarterOfHourLimit());
		assertEquals("/help/languages", statuses.get(ResourceFamily.HELP).get(3).getEndpoint());
		assertEquals(15, statuses.get(ResourceFamily.HELP).get(0).getQuarterOfHourLimit());
		assertEquals("/search/tweets", statuses.get(ResourceFamily.SEARCH).get(0).getEndpoint());
		assertEquals(180, statuses.get(ResourceFamily.SEARCH).get(0).getQuarterOfHourLimit());
	}

	@Test
	public void getRateLimit_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/application/rate_limit_status.json?resources=help%2Csearch"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("rate-limit-status"), APPLICATION_JSON));
		
		Map<ResourceFamily, List<RateLimitStatus>> statuses = appAuthTwitter.userOperations().getRateLimitStatus(ResourceFamily.HELP,ResourceFamily.SEARCH);
		assertTrue(statuses.containsKey(ResourceFamily.SEARCH));
		assertTrue(statuses.containsKey(ResourceFamily.HELP));
		assertEquals(1, statuses.get(ResourceFamily.SEARCH).size());
		assertEquals(4, statuses.get(ResourceFamily.HELP).size());
		assertEquals("/help/privacy", statuses.get(ResourceFamily.HELP).get(0).getEndpoint());
		assertEquals(15, statuses.get(ResourceFamily.HELP).get(0).getQuarterOfHourLimit());
		assertEquals("/help/languages", statuses.get(ResourceFamily.HELP).get(3).getEndpoint());
		assertEquals(15, statuses.get(ResourceFamily.HELP).get(0).getQuarterOfHourLimit());
		assertEquals("/search/tweets", statuses.get(ResourceFamily.SEARCH).get(0).getEndpoint());
		assertEquals(180, statuses.get(ResourceFamily.SEARCH).get(0).getQuarterOfHourLimit());
	}
	
	@Test
	public void getAccountSettings() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/settings.json"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("account-settings"), APPLICATION_JSON));

		AccountSettings settings = twitter.userOperations().getAccountSettings();
		assertTrue(settings.isAlwaysUseHttps());
		assertTrue(settings.isDiscoverableByEmail());
		assertTrue(settings.isDiscoverableByMobilePhone());
		assertTrue(settings.isDisplaySensitiveMedia());
		assertTrue(settings.isGeoEnabled());
		assertEquals("en", settings.getLanguage());
		assertFalse(settings.isProtected());
		assertEquals("theSeanCook", settings.getScreenName());
		assertTrue(settings.isUseCookiePersonalization());
		assertTrue(settings.getSleepTime().isEnabled());
		assertEquals(1, settings.getSleepTime().getStartTime().intValue());
		assertEquals(6, settings.getSleepTime().getEndTime().intValue());
		assertEquals("Pacific Time (US & Canada)", settings.getTimeZone().getName());
		assertEquals("America/Los_Angeles", settings.getTimeZone().getTZInfoName());
		assertEquals(-28800, settings.getTimeZone().getUTCOffset());
		assertEquals("United States", settings.getTrendLocation().get(0).getCountry());
		assertEquals("US", settings.getTrendLocation().get(0).getCountryCode());
		assertEquals("Atlanta", settings.getTrendLocation().get(0).getName());
		assertEquals(23424977, settings.getTrendLocation().get(0).getParentId());
		assertEquals("http://where.yahooapis.com/v1/place/2357024", settings.getTrendLocation().get(0).getUrl());
		assertEquals(2357024, settings.getTrendLocation().get(0).getWhereOnEarthID());
	}
	
	@Test
	public void updateAccountSettings_allSettings() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/settings.json"))
				.andExpect(method(POST))
				.andExpect(content().string("trend_location_woeid=12345&sleep_time_enabled=true&start_sleep_time=2&end_sleep_time=8&time_zone=America%2FLos_Angeles&lang=EN"))
				.andRespond(withSuccess(jsonResource("account-settings"), APPLICATION_JSON));
		AccountSettings settings = twitter.userOperations().updateAccountSettings(
				new AccountSettingsData().language("EN").timeZone("America/Los_Angeles").withSleepTimeEnabled(2, 8).trendLocationWOEID(12345));
		assertTrue(settings.isAlwaysUseHttps());
		assertTrue(settings.isDiscoverableByEmail());
		assertTrue(settings.isDiscoverableByMobilePhone());
		assertTrue(settings.isDisplaySensitiveMedia());
		assertTrue(settings.isGeoEnabled());
		assertEquals("en", settings.getLanguage());
		assertFalse(settings.isProtected());
		assertEquals("theSeanCook", settings.getScreenName());
		assertTrue(settings.isUseCookiePersonalization());
		assertTrue(settings.getSleepTime().isEnabled());
		assertEquals(1, settings.getSleepTime().getStartTime().intValue());
		assertEquals(6, settings.getSleepTime().getEndTime().intValue());
		assertEquals("Pacific Time (US & Canada)", settings.getTimeZone().getName());
		assertEquals("America/Los_Angeles", settings.getTimeZone().getTZInfoName());
		assertEquals(-28800, settings.getTimeZone().getUTCOffset());
		assertEquals("United States", settings.getTrendLocation().get(0).getCountry());
		assertEquals("US", settings.getTrendLocation().get(0).getCountryCode());
		assertEquals("Atlanta", settings.getTrendLocation().get(0).getName());
		assertEquals(23424977, settings.getTrendLocation().get(0).getParentId());
		assertEquals("http://where.yahooapis.com/v1/place/2357024", settings.getTrendLocation().get(0).getUrl());
		assertEquals(2357024, settings.getTrendLocation().get(0).getWhereOnEarthID());
		mockServer.verify();
	}

	@Test
	public void updateDeliveryDevice() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/update_delivery_device.json")).andExpect(method(POST))
				.andExpect(content().string("device=sms&include_entities=false")).andRespond(withSuccess());
		Boolean response = twitter.userOperations().updateDeliveryDevice(new DeliveryDevice().device(Device.sms).includeEntities(Boolean.FALSE));
		assertTrue(response);
		mockServer.verify();
	}
	
	@Test
	public void updateProfile() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/update_profile.json"))
				.andExpect(method(POST))
				.andExpect(content()
						.string("name=Art+Names&url=http%3A%2F%2Fwww.springsource.org&location=Denton%2C+TX&description=I%27m+just+a+normal+kinda+guy&include_entities=false&skip_status=true"))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile profile = twitter.userOperations().updateProfile(
				new ProfileData().description("I'm just a normal kinda guy").includeEntities(Boolean.FALSE).location("Denton, TX").name("Art Names").skipStatus(Boolean.TRUE)
						.url("http://www.springsource.org"));
		assertEquals("http://www.springsource.org", profile.getUrl());
		assertEquals("I'm just a normal kinda guy", profile.getDescription());
		assertEquals("Art Names", profile.getName());
		assertEquals("Denton, TX", profile.getLocation());
		mockServer.verify();
	}
	
	@Test
	public void updateProfileBackgroundImage() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/update_profile_background_image.json"))
				.andExpect(method(POST))
				.andExpect(content().string("tile=false&include_entities=false&skip_status=false&use=true&image=BASE64DATA"))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile twitterProfile = twitter.userOperations().updateProfileBackgroundImage(
				new ProfileBackgroundImage().image("BASE64DATA").tile(Boolean.FALSE).includeEntities(Boolean.FALSE).skipStatus(Boolean.FALSE).use(Boolean.TRUE));
		assertEquals("http://a3.twimg.com/a/1301419075/images/themes/theme1/bg.png", twitterProfile.getBackgroundImageUrl());
		mockServer.verify();
	}
	
	@Test
	public void updateProfileBackgroundColors() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/update_profile_colors.json"))
				.andExpect(method(POST))
				.andExpect(content()
						.string("profile_background_color=C0DEED&profile_link_color=0084B4&profile_sidebar_border_color=C0DEED&profile_sidebar_fill_color=DDEEF6&profile_text_color=333333&include_entities=false&skip_status=true"))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile twitterProfile = twitter.userOperations().updateProfileColors(
				new ProfileBackgroundColors().backgroundColor("C0DEED").includeEntities(Boolean.FALSE).linkColor("0084B4").sidebarBorderColor("C0DEED").sidebarFillColor("DDEEF6")
						.skipStatus(Boolean.TRUE).textColor("333333"));
		assertEquals("C0DEED", twitterProfile.getBackgroundColor());
		assertEquals("0084B4", twitterProfile.getLinkColor());
		assertEquals("C0DEED", twitterProfile.getSidebarBorderColor());
		assertEquals("DDEEF6", twitterProfile.getSidebarFillColor());
		assertEquals("333333", twitterProfile.getTextColor());

		mockServer.verify();
	}
	
	@Test
	public void updateProfileImage() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/update_profile_image.json"))
				.andExpect(method(POST))
				.andExpect(content()
						.string("image=BASE64DATA&include_entities=false&skip_status=true"))
				.andRespond(withSuccess(jsonResource("twitter-profile"), APPLICATION_JSON));
		TwitterProfile twitterProfile = twitter.userOperations().updateProfileImage(new ProfileImage().image("BASE64DATA").includeEntities(Boolean.FALSE).skipStatus(Boolean.TRUE));
		assertEquals("http://a1.twimg.com/sticky/default_profile_images/default_profile_4_normal.png", twitterProfile.getProfileImageUrl());
		mockServer.verify();
	}
	
	@Test
	public void removeProfileBanner() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/remove_profile_banner.json")).andExpect(method(POST))
				.andExpect(content().string("")).andRespond(withSuccess());
		Boolean response = twitter.userOperations().removeProfileBanner();
		assertTrue(response);
		mockServer.verify();
	}

	@Test
	public void updateProfileBanner() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/account/update_profile_banner.json")).andExpect(method(POST))
				.andExpect(content().string("banner=BASE64DATA&width=300&height=200&offset_left=10&offset_top=20")).andRespond(withSuccess());
		Boolean response = twitter.userOperations().updateProfileBanner(new ProfileBanner().banner("BASE64DATA").height(200).width(300).offsetLeft(10).offsetTop(20));
		assertTrue(response);
		mockServer.verify();
	}

}
