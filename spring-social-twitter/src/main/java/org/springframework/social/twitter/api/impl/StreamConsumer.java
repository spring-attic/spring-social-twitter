/*
 * Copyright 2011 the original author or authors.
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

import org.springframework.social.twitter.api.StreamingException;

abstract class StreamConsumer extends Thread {
		
	private volatile boolean open;

	private Stream stream;

	public StreamConsumer() {
		this.open = true;
	}
	
	@Override
	public void run() {
		long timeToSleep = 250;
		stream = null;
		
		while(open) {
			try {
				if(stream == null) {
					stream = getStream();
					timeToSleep = NO_WAIT;
				}
				stream.next();
			} catch (StreamingException e) {
				// if a valid connection drops, reconnect immediately
				stream = null;
			} catch (StreamCreationException e) {
				if(e.getHttpStatus() != null) {
					// Back off exponentially
					if(timeToSleep == NO_WAIT) {
						timeToSleep = 10000;
					}
					sleepBeforeRetry(timeToSleep);
					timeToSleep = timeToSleep * 2;				
					// TODO: Should eventually fail...repeated tries could cause ban
					if(timeToSleep > HTTP_ERROR_SLEEP_MAX) {
						close();
					}
				} else {
					if(open) {
						// Back off linearly
						if(timeToSleep == NO_WAIT) {
							timeToSleep = 250;
						}
						sleepBeforeRetry(timeToSleep);
						timeToSleep = Math.min(timeToSleep + 250, NETWORK_ERROR_SLEEP_MAX);
						stream = null;
					}
				}
			}
		}		
	}

	public void close() {
		open = false;
		if(stream != null) {
			stream.close();
		}
	}

	// subclass hook
	protected abstract Stream getStream() throws StreamCreationException;

	protected void sleepBeforeRetry(long timeToSleep) {
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e1) {}
	}

	static final long HTTP_ERROR_SLEEP_MAX = 250000;

	static final long NETWORK_ERROR_SLEEP_MAX = 16000;

	private static final long NO_WAIT = 0;

}
