package org.springframework.social.twitter.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExtendedEntities implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ExtendedMedia> media = new ArrayList<>(1);

	public ExtendedEntities() {
	}

	public ExtendedEntities(List<ExtendedMedia> media) {
		this.media = media;
	}

	public void setMedia(List<ExtendedMedia> media) {
		this.media = media;
	}

	public List<ExtendedMedia> getMedia() {
		return this.media;
	}
}
