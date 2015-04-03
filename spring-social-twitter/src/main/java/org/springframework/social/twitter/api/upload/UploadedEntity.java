/*
 * Copyright 2014 the original author or authors.
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
package org.springframework.social.twitter.api.upload;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * <p>A representation of uploaded media.</p>
 * @author clatko
 */
public class UploadedEntity extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private long mediaId;
	private String mediaIdString;
	private long size;
	private long expiresAfterSecs;
	private ImageEntity image;
	private VideoEntity video;
	
	public UploadedEntity(long mediaId, String mediaIdString, long size, long expiresAfterSecs, ImageEntity image, VideoEntity video) {
		this.mediaId = mediaId;
		this.mediaIdString = mediaIdString;
		this.size = size;
		this.expiresAfterSecs = expiresAfterSecs;
		this.image = image;
		this.video = video;
	}

	public long getMediaId() {
		return mediaId;
	}

	public String getMediaIdString() {
		return mediaIdString;
	}

	public long getSize() {
		return size;
	}

	public long getExpiresAfterSecs() {
		return expiresAfterSecs;
	}

	public ImageEntity getImage() {
		return image;
	}

	public VideoEntity getVideo() {
		return video;
	}

	
}
