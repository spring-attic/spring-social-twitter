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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>A json representation of entities found within twitter status objects.<p>
 * @author bowen
 */
public class Entities extends TwitterObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<UrlEntity> urls = new LinkedList<UrlEntity>();
	
	private List<HashTagEntity> tags = new LinkedList<HashTagEntity>();
	
	private List<MentionEntity> mentions = new LinkedList<MentionEntity>();
	
	private List<MediaEntity> media = new LinkedList<MediaEntity>();

	private List<TickerSymbolEntity> tickerSymbols = new LinkedList<TickerSymbolEntity>();

	public Entities(List<UrlEntity> urls, List<HashTagEntity> tags, List<MentionEntity> mentions, List<MediaEntity> media) {
		this.urls = urls;
		this.tags = tags;
		this.mentions = mentions;
		this.media = media;
	}
	
	public Entities(List<UrlEntity> urls, List<HashTagEntity> tags, List<MentionEntity> mentions, List<MediaEntity> media, List<TickerSymbolEntity> tickerSymbols) {
		this(urls, tags, mentions, media);
		this.tickerSymbols = tickerSymbols;
	}

	public List<UrlEntity> getUrls() {
		if (this.urls == null) {
			return Collections.emptyList();
		}
		return this.urls;
	}
	
	
	public List<HashTagEntity> getHashTags() {
		if (this.tags == null) {
			return Collections.emptyList();
		}
		return this.tags;
	}
	
	
	public List<MentionEntity> getMentions() {
		if (this.mentions == null) {
			return Collections.emptyList();
		}
		return this.mentions;
	}
	
	
	public List<MediaEntity> getMedia() {
		if (this.media == null) {
			return Collections.emptyList();
		}
		return this.media;
	}
	
	public List<TickerSymbolEntity> getTickerSymbols() {
		if (this.tickerSymbols == null) {
			return Collections.emptyList();
		}
		return this.tickerSymbols;
	}
	
	public boolean hasUrls() {
		return this.urls != null && !this.urls.isEmpty();
	}
	
	
	public boolean hasTags() {
		return this.tags != null && !this.tags.isEmpty();
	}
	
	
	public boolean hasMentions() {
		return this.mentions != null && !this.mentions.isEmpty();
	}
	
	
	public boolean hasMedia() {
		return this.media != null && !this.media.isEmpty();
	}
	
	public boolean hasTickerSymbols() {
		return this.tickerSymbols != null && !this.tickerSymbols.isEmpty();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Entities entities = (Entities) o;
		if (media != null ? !media.equals(entities.media) : entities.media != null) {
			return false;
		}
		if (mentions != null ? !mentions.equals(entities.mentions) : entities.mentions != null) {
			return false;
		}
		if (tags != null ? !tags.equals(entities.tags) : entities.tags != null) {
			return false;
		}
		if (urls != null ? !urls.equals(entities.urls) : entities.urls != null) {
			return false;
		}
		if (tickerSymbols != null ? !tickerSymbols.equals(entities.tickerSymbols) : entities.tickerSymbols != null) {
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		int result = urls != null ? urls.hashCode() : 0;
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		result = 31 * result + (mentions != null ? mentions.hashCode() : 0);
		result = 31 * result + (media != null ? media.hashCode() : 0);
		result = 31 * result + (tickerSymbols != null ? tickerSymbols.hashCode() : 0);
		return result;
	}
}
