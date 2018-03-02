package org.springframework.social.twitter.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ExtendedMedia implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String idStr;
	private List<Integer> indices;
	private String mediaUrl;
	private String mediaUrlHttps;
	private String url;
	private String displayUrl;
	private String expandedUrl;
	private Object sizes;
	private Map<String, Object> videoInfo;
	private Object additionalMediaInfo;

	public ExtendedMedia() {
		super();
	}

	public ExtendedMedia(Long id, String idStr, List<Integer> indices, String mediaUrl, String mediaUrlHttps,
			String url, String displayUrl, String expandedUrl, String type, Object sizes, Map<String, Object> videoInfo,
			Object additionalMediaInfo) {
		this();
		this.id = id;
		this.idStr = idStr;
		this.indices = indices;
		this.mediaUrl = mediaUrl;
		this.mediaUrlHttps = mediaUrlHttps;
		this.url = url;
		this.displayUrl = displayUrl;
		this.expandedUrl = expandedUrl;
		this.sizes = sizes;
		this.videoInfo = videoInfo;
		this.additionalMediaInfo = additionalMediaInfo;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdStr() {
		return this.idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public List<Integer> getIndices() {
		return this.indices;
	}

	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

	public String getMediaUrl() {
		return this.mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getMediaUrlHttps() {
		return this.mediaUrlHttps;
	}

	public void setMediaUrlHttps(String mediaUrlHttps) {
		this.mediaUrlHttps = mediaUrlHttps;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDisplayUrl() {
		return this.displayUrl;
	}

	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}

	public String getExpandedUrl() {
		return this.expandedUrl;
	}

	public void setExpandedUrl(String expandedUrl) {
		this.expandedUrl = expandedUrl;
	}

	public Object getSizes() {
		return this.sizes;
	}

	public void setSizes(Object sizes) {
		this.sizes = sizes;
	}

	public Map<String, Object> getVideoInfo() {
		return this.videoInfo;
	}

	public void setVideoInfo(Map<String, Object> videoInfo) {
		this.videoInfo = videoInfo;
	}

	public Object getAdditionalMediaInfo() {
		return this.additionalMediaInfo;
	}

	public void setAdditionalMediaInfo(Object additionalMediaInfo) {
		this.additionalMediaInfo = additionalMediaInfo;
	}
}
