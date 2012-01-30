/*
 * Copyright 2010 the original author or authors.
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.InternalServerErrorException;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.ServerDownException;
import org.springframework.social.ServerOverloadedException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.twitter.api.InvalidMessageRecipientException;
import org.springframework.social.twitter.api.MessageTooLongException;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * Subclass of {@link DefaultResponseErrorHandler} that handles errors from Twitter's
 * REST API, interpreting them into appropriate exceptions.
 * @author Craig Walls
 */
class TwitterErrorHandler extends DefaultResponseErrorHandler {
		
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = response.getStatusCode();		
		if (statusCode.series() == Series.SERVER_ERROR) {
			handleServerErrors(statusCode);
		} else if (statusCode.series() == Series.CLIENT_ERROR) {
			handleClientErrors(response);
		}
		
		// if not otherwise handled, do default handling and wrap with UncategorizedApiException
		try {
			super.handleError(response);
		} catch(Exception e) {
			throw new UncategorizedApiException("Error consuming Twitter REST API", e);
		}
	}
	
	private void handleClientErrors(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = response.getStatusCode();		
		Map<String, Object> errorMap = extractErrorDetailsFromResponse(response);
		if (errorMap == null) {
			return; // unexpected error body, can't be handled here
		}

		String errorText = null;
		if (errorMap.containsKey("error")) {
			errorText = (String) errorMap.get("error");
		} else if(errorMap.containsKey("errors")) {
			Object errors = errorMap.get("errors");			
			if (errors instanceof List) {
				@SuppressWarnings("unchecked")
				List<Map<String, String>> errorsList = (List<Map<String, String>>) errors;
				errorText = errorsList.get(0).get("message");
			} else if (errors instanceof String ) {
				errorText = (String) errors;
			}
		}

		if (statusCode == HttpStatus.BAD_REQUEST) {
			if (errorText.contains("Rate limit exceeded.")) {
				throw new RateLimitExceededException();
			}
		} else if (statusCode == HttpStatus.UNAUTHORIZED) {
			if (errorText == null) {
				throw new NotAuthorizedException(response.getStatusText());
			} else if (errorText.equals("Could not authenticate you.")) {
				throw new MissingAuthorizationException();
			} else if (errorText.equals("Could not authenticate with OAuth.")) { // revoked token
				throw new RevokedAuthorizationException();
			} else if (errorText.equals("Invalid / expired Token")) { // Note that Twitter doesn't actually expire tokens
				throw new InvalidAuthorizationException(errorText);
			} else {
				throw new NotAuthorizedException(errorText);
			}
		} else if (statusCode == HttpStatus.FORBIDDEN) {
			if (errorText.equals(DUPLICATE_STATUS_TEXT) || errorText.contains("You already said that")) {
				throw new DuplicateStatusException(errorText);
			} else if (errorText.equals(STATUS_TOO_LONG_TEXT) || errorText.contains(MESSAGE_TOO_LONG_TEXT)) {
				throw new MessageTooLongException(errorText);
			} else if (errorText.equals(INVALID_MESSAGE_RECIPIENT_TEXT)) {
				throw new InvalidMessageRecipientException(errorText);
			} else if (errorText.equals(DAILY_RATE_LIMIT_TEXT)) {
				throw new RateLimitExceededException();
			} else {
				throw new OperationNotPermittedException(errorText);
			}
		} else if (statusCode == HttpStatus.NOT_FOUND) {
			throw new ResourceNotFoundException(errorText);
		} else if (statusCode == HttpStatus.valueOf(ENHANCE_YOUR_CALM)) {
			throw new RateLimitExceededException();
		}

	}

	private void handleServerErrors(HttpStatus statusCode) throws IOException {
		if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
			throw new InternalServerErrorException("Something is broken at Twitter. Please see http://dev.twitter.com/pages/support to report the issue.");
		} else if (statusCode == HttpStatus.BAD_GATEWAY) {
			throw new ServerDownException("Twitter is down or is being upgraded.");
		} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE) {
			throw new ServerOverloadedException("Twitter is overloaded with requests. Try again later.");
		}
	}

	private Map<String, Object> extractErrorDetailsFromResponse(ClientHttpResponse response) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		try {
			return mapper.<Map<String, Object>>readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonParseException e) {
			return null;
		}
	}

	private static final String INVALID_MESSAGE_RECIPIENT_TEXT = "You cannot send messages to users who are not following you.";
	private static final String STATUS_TOO_LONG_TEXT = "Status is over 140 characters.";
	private static final String MESSAGE_TOO_LONG_TEXT = "The text of your direct message is over 140 characters";
	private static final String DUPLICATE_STATUS_TEXT = "Status is a duplicate.";
	private static final String DAILY_RATE_LIMIT_TEXT = "User is over daily status update limit.";

	
	private static final int ENHANCE_YOUR_CALM = 420;
}
