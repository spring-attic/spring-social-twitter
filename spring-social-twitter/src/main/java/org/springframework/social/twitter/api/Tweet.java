/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a Twitter status update (e.g., a "tweet").
 *
 * @author Craig Walls
 */
public class Tweet extends TwitterObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String id;
	private final String text;
	private final Date createdAt;
	private String fromUser;
	private String profileImageUrl;
	private Long toUserId;
	private Long inReplyToStatusId;
	private Long inReplyToUserId;
	private String inReplyToScreenName;
	private long fromUserId;
	private String languageCode;
	private String source;
	private Integer retweetCount;
	private boolean retweeted;
	private Tweet retweetedStatus;
	private boolean favorited;
	private Integer favoriteCount;
	private Entities entities;
	private ExtendedEntities extendedEntities;
	private TwitterProfile user;

	/**
	 * Constructs a Tweet
	 *
	 * @param id
	 *            The tweet's ID
	 * @param text
	 *            The tweet's text
	 * @param createdAt
	 *            Date Tweet was created
	 * @param fromUser
	 *            The username of the author of the tweet.
	 * @param profileImageUrl
	 *            The URL to the profile picture of the tweet's author.
	 * @param toUserId
	 *            The user ID of the user to whom the tweet is targeted.
	 * @param fromUserId
	 *            The user ID of the tweet's author.
	 * @param languageCode
	 *            The language code
	 * @param source
	 *            The source of the tweet.
	 *
	 * @deprecated Use other constructor with String ID instead.
	 */
	@Deprecated
	public Tweet(long id, String text, Date createdAt, String fromUser, String profileImageUrl, Long toUserId,
			long fromUserId, String languageCode, String source) {
		this(id, String.valueOf(id), text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode,
				source);
	}

	/**
	 * Constructs a Tweet
	 *
	 * @param id
	 *            The tweet's ID
	 * @param idStr
	 *            The tweet's ID as a String
	 * @param text
	 *            The tweet's text
	 * @param createdAt
	 *            Date Tweet was created
	 * @param fromUser
	 *            The username of the author of the tweet.
	 * @param profileImageUrl
	 *            The URL to the profile picture of the tweet's author.
	 * @param toUserId
	 *            The user ID of the user to whom the tweet is targeted.
	 * @param fromUserId
	 *            The user ID of the tweet's author.
	 * @param languageCode
	 *            The language code
	 * @param source
	 *            The source of the tweet.
	 *
	 * @deprecated Use other constructor with String ID instead.
	 */
	@Deprecated
	public Tweet(long id, String idStr, String text, Date createdAt, String fromUser, String profileImageUrl,
			Long toUserId, long fromUserId, String languageCode, String source) {
		this(Long.toString(id), text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
	}

	public Tweet(String id, String text, Date createdAt, String fromUser, String profileImageUrl, Long toUserId,
			long fromUserId, String languageCode, String source) {
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.fromUser = fromUser;
		this.profileImageUrl = profileImageUrl;
		this.toUserId = toUserId;
		this.fromUserId = fromUserId;
		this.languageCode = languageCode;
		this.source = source;
	}

	/**
	 * The text of the tweet. If this tweet is a retweet of another tweet, the text
	 * may be preceeded with "RT \@someuser" and may be truncated at the end. To get
	 * the raw, unmodified text of the original tweet, use
	 * {@link #getUnmodifiedText()}.
	 *
	 * @return The text of the tweet.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Returns the unmodified text of the tweet. If this tweet is a retweet, it
	 * returns the text of the original tweet. If it is not a retweet, then this
	 * method will return the same value as {@link #getText()}.
	 *
	 * @return The unmodified text of the tweet.
	 */
	public String getUnmodifiedText() {
		return this.isRetweet() ? this.retweetedStatus.getText() : this.getText();
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public String getFromUser() {
		return this.fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getId() {
		return this.id;
	}

	public String getProfileImageUrl() {
		return this.profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Long getToUserId() {
		return this.toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public long getFromUserId() {
		return this.fromUserId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public Long getInReplyToStatusId() {
		return this.inReplyToStatusId;
	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	/**
	 * The number of times this tweet has been retweeted. Only available in timeline
	 * results. getRetweetCount() will return null for Tweet objects returned in
	 * search results.
	 *
	 * @return the number of times the tweet has been retweeted or null if that
	 *         information is unavailable
	 */
	public Integer getRetweetCount() {
		return this.retweetCount;
	}

	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}

	public boolean isRetweeted() {
		return this.retweeted;
	}

	public Tweet getRetweetedStatus() {
		return this.retweetedStatus;
	}

	public void setRetweetedStatus(final Tweet tweet) {
		this.retweetedStatus = tweet;
	}

	public boolean isRetweet() {
		return this.retweetedStatus != null;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean isFavorited() {
		return this.favorited;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Integer getFavoriteCount() {
		return this.favoriteCount;
	}

	public Entities getEntities() {
		return this.entities;
	}

	public void setEntities(final Entities ent) {
		this.entities = ent;
	}

	public ExtendedEntities getExtendedEntities() {
		return this.extendedEntities;
	}

	public void setExtendedEntities(final ExtendedEntities extendedEntities) {
		this.extendedEntities = extendedEntities;
	}

	public boolean hasMentions() {
		if (this.entities == null)
			return false;
		return !this.entities.getMentions().isEmpty();
	}

	public boolean hasMedia() {
		if (this.entities == null)
			return false;
		return !this.entities.getMedia().isEmpty();
	}

	public boolean hasUrls() {
		if (this.entities == null)
			return false;
		return !this.entities.getUrls().isEmpty();
	}

	public boolean hasTags() {
		if (this.entities == null)
			return false;
		return !this.entities.getHashTags().isEmpty();
	}

	public TwitterProfile getUser() {
		return this.user;
	}

	public void setUser(final TwitterProfile prof) {
		this.user = prof;
	}

	public Long getInReplyToUserId() {
		return this.inReplyToUserId;
	}

	public void setInReplyToUserId(final Long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public String getInReplyToScreenName() {
		return this.inReplyToScreenName;
	}

	public void setInReplyToScreenName(final String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (this.getClass() != o.getClass()))
			return false;

		final Tweet tweet = (Tweet) o;

		if (this.fromUserId != tweet.fromUserId)
			return false;
		if (this.id != tweet.id)
			return false;
		if (this.retweeted != tweet.retweeted)
			return false;
		if (this.createdAt != null ? !this.createdAt.equals(tweet.createdAt) : tweet.createdAt != null)
			return false;
		if (this.entities != null ? !this.entities.equals(tweet.entities) : tweet.entities != null)
			return false;
		if (this.extendedEntities != null ? !this.extendedEntities.equals(tweet.extendedEntities)
				: tweet.extendedEntities != null)
			return false;
		if (this.fromUser != null ? !this.fromUser.equals(tweet.fromUser) : tweet.fromUser != null)
			return false;
		if (this.inReplyToScreenName != null ? !this.inReplyToScreenName.equals(tweet.inReplyToScreenName)
				: tweet.inReplyToScreenName != null)
			return false;
		if (this.inReplyToStatusId != null ? !this.inReplyToStatusId.equals(tweet.inReplyToStatusId)
				: tweet.inReplyToStatusId != null)
			return false;
		if (this.inReplyToUserId != null ? !this.inReplyToUserId.equals(tweet.inReplyToUserId)
				: tweet.inReplyToUserId != null)
			return false;
		if (this.languageCode != null ? !this.languageCode.equals(tweet.languageCode) : tweet.languageCode != null)
			return false;
		if (this.profileImageUrl != null ? !this.profileImageUrl.equals(tweet.profileImageUrl)
				: tweet.profileImageUrl != null)
			return false;
		if (this.retweetCount != null ? !this.retweetCount.equals(tweet.retweetCount) : tweet.retweetCount != null)
			return false;
		if (this.retweetedStatus != null ? !this.retweetedStatus.equals(tweet.retweetedStatus)
				: tweet.retweetedStatus != null)
			return false;
		if (this.source != null ? !this.source.equals(tweet.source) : tweet.source != null)
			return false;
		if (this.text != null ? !this.text.equals(tweet.text) : tweet.text != null)
			return false;
		if (this.toUserId != null ? !this.toUserId.equals(tweet.toUserId) : tweet.toUserId != null)
			return false;
		if (this.user != null ? !this.user.equals(tweet.user) : tweet.user != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (this.id != null ? this.id.hashCode() : 0);
		result = (31 * result) + (this.text != null ? this.text.hashCode() : 0);
		result = (31 * result) + (this.createdAt != null ? this.createdAt.hashCode() : 0);
		result = (31 * result) + (this.fromUser != null ? this.fromUser.hashCode() : 0);
		result = (31 * result) + (this.profileImageUrl != null ? this.profileImageUrl.hashCode() : 0);
		result = (31 * result) + (this.toUserId != null ? this.toUserId.hashCode() : 0);
		result = (31 * result) + (this.inReplyToStatusId != null ? this.inReplyToStatusId.hashCode() : 0);
		result = (31 * result) + (this.inReplyToUserId != null ? this.inReplyToUserId.hashCode() : 0);
		result = (31 * result) + (this.inReplyToScreenName != null ? this.inReplyToScreenName.hashCode() : 0);
		result = (31 * result) + (int) (this.fromUserId ^ (this.fromUserId >>> 32));
		result = (31 * result) + (this.languageCode != null ? this.languageCode.hashCode() : 0);
		result = (31 * result) + (this.source != null ? this.source.hashCode() : 0);
		result = (31 * result) + (this.retweetCount != null ? this.retweetCount.hashCode() : 0);
		result = (31 * result) + (this.retweeted ? 1 : 0);
		result = (31 * result) + (this.retweetedStatus != null ? this.retweetedStatus.hashCode() : 0);
		result = (31 * result) + (this.entities != null ? this.entities.hashCode() : 0);
		result = (31 * result) + (this.extendedEntities != null ? this.extendedEntities.hashCode() : 0);
		result = (31 * result) + (this.user != null ? this.user.hashCode() : 0);
		return result;
	}
}
