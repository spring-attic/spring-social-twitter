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

/**
 * Represents the results of a Twitter search, including matching {@link Tweet}s
 * and any metadata associated with that search.
 * 
 * @author Craig Walls
 */
public class SearchResults extends TwitterObject {
	private List<Tweet> tweets;
	private SearchMetadata metadata;
	private boolean lastPage;

	public SearchResults(List<Tweet> tweets, SearchMetadata metaData) {
		this(tweets, metaData, false);
	}
	
	public SearchResults(List<Tweet> tweets, SearchMetadata metaData, boolean lastPage) {
		this.tweets = tweets;
		this.metadata = metaData;
		this.lastPage = lastPage;
	}

	/**
	 * @return the list of matching {@link Tweet}s
	 */
	public List<Tweet> getTweets() {
		return tweets;
	}
	
	/**
	 * @return the {@link SearchMetadata} associated with a particular search
	 */
	public SearchMetadata getSearchMetadata() {
		return metadata;
	}

	/**
	 * @return <code>true</code> if this is the last page of matching
	 * {@link Tweet}s; <code>false</code> if there are more pages that follow
	 * this one.
	 */
	public boolean isLastPage() {
		return lastPage;
	}

}
