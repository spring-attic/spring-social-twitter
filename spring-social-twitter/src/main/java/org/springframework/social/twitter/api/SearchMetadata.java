package org.springframework.social.twitter.api;

/**
 * Represents the metadata associated with a search query for the use of retrieving further results
 * via Twitter's search API
 * @author Jeremy Appel
 */
public class SearchMetadata {
	
	private final long max_id;
	private final long since_id;
	
	public SearchMetadata(long max_id, long since_id) {
		this.max_id = max_id;
		this.since_id = since_id;
	}

	/**
	 * The maximum ID of any tweet in the search results.
	 * Typically the ID of the most recent tweet in the search results.
	 */
	public long getMax_id() {
		return max_id;
	}

	/**
	 * The value of the sinceId parameter specified in the search.
	 * If not specified, it will be zero (not the minimum ID of the tweets in the search results). 
	 */
	public long getSince_id() {
		return since_id;
	}


}
