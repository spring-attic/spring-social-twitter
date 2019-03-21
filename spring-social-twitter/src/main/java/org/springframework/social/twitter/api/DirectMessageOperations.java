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

import java.util.List;

import org.springframework.social.ApiException;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.MissingAuthorizationException;


/**
 * Interface defining the Twitter operations for working with direct messages.
 * @author Craig Walls
 */
public interface DirectMessageOperations {

	/**
	 * Retrieve the 20 most recently received direct messages for the authenticating user. The most recently received messages are listed first.
	 * @return a collection of {@link DirectMessage} with the authenticating user as the recipient.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<DirectMessage> getDirectMessagesReceived();

	/**
	 * Retrieve received direct messages for the authenticating user. The most recently received messages are listed first.
	 * @param page The page to return
	 * @param pageSize The number of {@link DirectMessage}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link DirectMessage} with the authenticating user as the recipient.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<DirectMessage> getDirectMessagesReceived(int page, int pageSize);

	/**
	 * Retrieve received direct messages for the authenticating user. The most recently received messages are listed first.
	 * @param page The page to return
	 * @param pageSize The number of {@link DirectMessage}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link DirectMessage} ID to return in the results
	 * @param maxId The maximum {@link DirectMessage} ID to return in the results
	 * @return a collection of {@link DirectMessage} with the authenticating user as the recipient.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<DirectMessage> getDirectMessagesReceived(int page, int pageSize, long sinceId, long maxId);

	/**
	 * Retrieve the 20 most recent direct messages sent by the authenticating user. The most recently sent messages are listed first.
	 * @return a collection of {@link DirectMessage} with the authenticating user as the sender.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<DirectMessage> getDirectMessagesSent();

	/**
	 * Retrieve direct messages sent by the authenticating user. The most recently sent messages are listed first.
	 * @param page The page to return
	 * @param pageSize The number of {@link DirectMessage}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link DirectMessage} with the authenticating user as the sender.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<DirectMessage> getDirectMessagesSent(int page, int pageSize);

	/**
	 * Retrieve direct messages sent by the authenticating user. The most recently sent messages are listed first.
	 * @param page The page to return
	 * @param pageSize The number of {@link DirectMessage}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link DirectMessage} ID to return in the results
	 * @param maxId The maximum {@link DirectMessage} ID to return in the results
	 * @return a collection of {@link DirectMessage} with the authenticating user as the sender.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<DirectMessage> getDirectMessagesSent(int page, int pageSize, long sinceId, long maxId);

	/**
	 * Gets a direct message by its ID. The message must be readable by the authenticating user.
	 * @param id the message ID
	 * @return the direct message
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	DirectMessage getDirectMessage(long id);
	
	/**
	 * Sends a direct message to another Twitter user. The recipient of the
	 * message must follow the authenticated user in order for the message to be
	 * delivered. If the recipient is not following the authenticated user, an
	 * {@link InvalidMessageRecipientException} will be thrown.
	 * 
	 * @param toScreenName the screen name of the recipient of the messages.
	 * @param text the message text.
	 * @return the {@link DirectMessage}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws InvalidMessageRecipientException if the recipient is not following the authenticating user.
	 * @throws DuplicateStatusException if the message duplicates a previously sent message.
	 * @throws MessageTooLongException if the message length exceeds Twitter's 140 character limit.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	DirectMessage sendDirectMessage(String toScreenName, String text);

	/**
	 * Sends a direct message to another Twitter user.
	 * The recipient of the message must follow the authenticated user in order
	 * for the message to be delivered. If the recipient is not following the
	 * authenticated user, an {@link InvalidMessageRecipientException} will be thrown.
	 * @param toUserId the Twitter user ID of the recipient of the messages.
	 * @param text the message text.
	 * @return the {@link DirectMessage}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws InvalidMessageRecipientException if the recipient is not following the authenticating user.
	 * @throws DuplicateStatusException if the message duplicates a previously sent message.
	 * @throws MessageTooLongException if the message length exceeds Twitter's 140 character limit.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	DirectMessage sendDirectMessage(long toUserId, String text);
	
	/**
	 * Deletes a direct message for the authenticated user.
	 * @param messageId the ID of the message to be removed.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void deleteDirectMessage(long messageId);
}
