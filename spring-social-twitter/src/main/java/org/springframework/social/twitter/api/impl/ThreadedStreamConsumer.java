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
package org.springframework.social.twitter.api.impl;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.StreamingException;

abstract class ThreadedStreamConsumer extends Thread implements Stream {
		
	private AtomicBoolean open;

	private StreamReader streamReader;

	public ThreadedStreamConsumer() {
		this.open = new AtomicBoolean(true);		
	}
	
	@Override
	public void run() {
		long timeToSleep = 250;
		streamReader = null;
		
		while(open.get()) {
			try {
				if(streamReader == null) {
					streamReader = getStreamReader();
					timeToSleep = MIN_WAIT;
				}
				streamReader.next();
			} catch (StreamingException e) {
				// if a valid connection drops, reconnect immediately
				streamReader = null;
			} catch (StreamCreationException e) {
				if(e.getHttpStatus() != null) {
					// Back off exponentially
					if(timeToSleep == MIN_WAIT) {
						timeToSleep = 5000;
					}
					sleepBeforeRetry(timeToSleep);
					timeToSleep = timeToSleep * 2;				
					// TODO: Should eventually fail...repeated tries could cause ban
					if(timeToSleep > HTTP_ERROR_SLEEP_MAX) {
						close();
					}
				} else {
					if(open.get()) {
						// Back off linearly
						if(timeToSleep == MIN_WAIT) {
							timeToSleep = 250;
						}
						sleepBeforeRetry(timeToSleep);
						timeToSleep = Math.min(timeToSleep + 250, NETWORK_ERROR_SLEEP_MAX);
					}
				}
			}
		}		
	}
	
	public void open() {
		this.start();
	}

	public void close() {
		open.set(false);
		if(streamReader != null) {
			streamReader.close();
		}
	}

	// subclass hook
	protected abstract StreamReader getStreamReader() throws StreamCreationException;

	protected void sleepBeforeRetry(long timeToSleep) {
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e1) {}
	}

	static final long HTTP_ERROR_SLEEP_MAX = 320000;

	static final long NETWORK_ERROR_SLEEP_MAX = 16000;

	private static final long MIN_WAIT = 250;

}
