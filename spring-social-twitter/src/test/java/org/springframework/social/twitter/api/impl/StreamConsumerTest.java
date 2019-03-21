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

import static org.junit.Assert.*;

import java.net.ConnectException;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class StreamConsumerTest {
	
	private int streamsToCreateBeforeFailure = 3;
	private long expectedTimeToSleep;
	
	@Test
	public void run_failWithHttpError() {		
		expectedTimeToSleep = 5000;
		ThreadedStreamConsumer consumer = new ThreadedStreamConsumer() {
			@Override
			protected StreamReader getStreamReader() throws StreamCreationException {
				return createStream(new StreamCreationException("Unable to create stream", HttpStatus.valueOf(420)));
			}
			
			@Override
			protected void sleepBeforeRetry(long timeToSleep) {
				assertSleepWithExponentialBackOff(timeToSleep);
			}
		};
		
		consumer.run();
	}

	@Test
	public void run_failWithNetworkError() {		
		expectedTimeToSleep = 250;
		ThreadedStreamConsumer consumer = new ThreadedStreamConsumer() {
			@Override
			protected StreamReader getStreamReader() throws StreamCreationException {
				return createStream(new StreamCreationException("Unable to create stream", new ConnectException()));
			}
			
			@Override
			protected void sleepBeforeRetry(long timeToSleep) {
				assertSleepWithLinearBackOff(timeToSleep);
				if(timeToSleep >= ThreadedStreamConsumer.NETWORK_ERROR_SLEEP_MAX) {
					close(); // to keep the test from running forever
				}
			}
		};
		
		consumer.run();
	}
	
	private void assertSleepWithExponentialBackOff(long timeToSleep) {
		assertEquals(expectedTimeToSleep, timeToSleep);
		expectedTimeToSleep = expectedTimeToSleep * 2;
	}

	private void assertSleepWithLinearBackOff(long timeToSleep) {
		assertEquals(expectedTimeToSleep, timeToSleep);
		expectedTimeToSleep = Math.min(expectedTimeToSleep + 250, ThreadedStreamConsumer.NETWORK_ERROR_SLEEP_MAX);
	}

	private StreamReader createStream(StreamCreationException exceptionToThrow) throws StreamCreationException {
		if(streamsToCreateBeforeFailure == 0) {
			throw exceptionToThrow;
		}
		streamsToCreateBeforeFailure--;
		return new MockStream(5);		
	}
	
}
