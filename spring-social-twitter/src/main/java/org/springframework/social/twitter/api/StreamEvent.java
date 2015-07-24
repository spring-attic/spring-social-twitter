package org.springframework.social.twitter.api;

public class StreamEvent extends TwitterObject {
	private final String event;
	private final TwitterProfile source;
	private final TwitterProfile target;
	
	public StreamEvent(String event, TwitterProfile source,
			TwitterProfile target) {
		super();
		this.event = event;
		this.source = source;
		this.target = target;
	}

	public String getEvent() {
		return event;
	}

	public TwitterProfile getSource() {
		return source;
	}

	public TwitterProfile getTarget() {
		return target;
	}
}
