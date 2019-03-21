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

import java.util.ArrayList;
import java.util.Collection;

/**
 * List that includes previous and next cursors for paging through items returned from Twitter in cursored pages.
 * @author Craig Walls
 * @param <T> the list element type
 */
@SuppressWarnings("serial")
public class CursoredList<T> extends ArrayList<T> {
	
	private final long previousCursor;

	private final long nextCursor;

	public CursoredList(Collection<? extends T> collection, long previousCursor, long nextCursor) {
		super(collection);
		this.previousCursor = previousCursor;
		this.nextCursor = nextCursor;
	}

	public CursoredList(int initialCapacity, long previousCursor, long nextCursor) {
		super(initialCapacity);
		this.previousCursor = previousCursor;
		this.nextCursor = nextCursor;
	}

	/**
	 * @return the cursor to retrieve the previous page of results.
	 */
	public long getPreviousCursor() {
		return previousCursor;
	}

	/**
	 * @return the cursor value to retrieve the next page of results.
	 */
	public long getNextCursor() {
		return nextCursor;
	}

	/**
	 * @return true if there is a previous page of results.
	 */
	public boolean hasPrevious() {
		return previousCursor > 0;
	}

	/**
	 * @return true if there is a next page of results.
	 */
	public boolean hasNext() {
		return nextCursor > 0;
	}

}
