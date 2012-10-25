package org.springframework.social.twitter.api;

/**
 * Represents the metadata associated with a search query for the use of retrieving further results
 * via Twitter's search API
 * @author Jeremy Appel
 */
public class SearchMetadata {
	
	private final int max_id;
	private final long since_id;
	
	public SearchMetadata(int max_id, long since_id) {
		this.max_id = max_id;
		this.since_id = since_id;
	}

	public int getMax_id() {
		return max_id;
	}

	public long getSince_id() {
		return since_id;
	}


}
