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

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

@Ignore
public class StreamDispatcherTest {

	@Test
	public void activeWithItemsInQueue() throws Exception {
		StreamListener mockListener = mock(StreamListener.class);		
		Queue<String> queue = createQueueWithItems();
		StreamDispatcher dispatcher = new StreamDispatcher(queue, Arrays.asList(mockListener));
		runAndAssert(mockListener, dispatcher, 4, 2, 1, 3);
//		dispatcher.stop();
	}

	@Test
	public void activeWithEmptyQueue() throws Exception {
		StreamListener mockListener = mock(StreamListener.class);		
		Queue<String> queue = new ConcurrentLinkedQueue<String>();
		StreamDispatcher dispatcher = new StreamDispatcher(queue, Arrays.asList(mockListener));
		runAndAssert(mockListener, dispatcher, 0, 0, 0, 0);
//		dispatcher.stop();
	}
	
	@Test
	public void stoppedWithItemsInQueue() throws Exception {
		StreamListener mockListener = mock(StreamListener.class);		
		Queue<String> queue = createQueueWithItems();
		StreamDispatcher dispatcher = new StreamDispatcher(queue, Arrays.asList(mockListener));
//		dispatcher.stop();
		runAndAssert(mockListener, dispatcher, 4, 2, 1, 3);
	}
	
	@Test
	public void stoppedWithEmptyQueue() throws Exception {
		StreamListener mockListener = mock(StreamListener.class);		
		Queue<String> queue = new ConcurrentLinkedQueue<String>();
		StreamDispatcher dispatcher = new StreamDispatcher(queue, Arrays.asList(mockListener));
//		dispatcher.stop();
		runAndAssert(mockListener, dispatcher, 0, 0, 0, 0);
	}	
	
	@Test
	public void ignoreUnrecognizedEvent() throws Exception {
		StreamListener mockListener = mock(StreamListener.class);		
		Queue<String> queue = new ConcurrentLinkedQueue<String>();
		queue.add("BOGUS LINE");
		queue.add("{\"unrecognized\":\"event\"}");
		StreamDispatcher dispatcher = new StreamDispatcher(queue, Arrays.asList(mockListener));
		runAndAssert(mockListener, dispatcher, 0, 0, 0, 0);		
//		dispatcher.stop();
	}

	private void runAndAssert(StreamListener mockListener, StreamDispatcher dispatcher, int tweetEvents, int deleteEvents, int limitEvents, int warningEvents) throws Exception {
		new Thread(dispatcher).start();
		Thread.sleep(1000); // pause to give thread opportunity to do its job
		verify(mockListener, times(tweetEvents)).onTweet(any(Tweet.class));
		verify(mockListener, times(deleteEvents)).onDelete(any(StreamDeleteEvent.class));
		verify(mockListener, times(limitEvents)).onLimit(369);
		verify(mockListener, times(warningEvents)).onWarning(any(StreamWarningEvent.class));
	}

	private Queue<String> createQueueWithItems() {
		InputStream inputStream = null;
		Queue<String> queue = new ConcurrentLinkedQueue<String>();
		try {
			inputStream = new ClassPathResource("filter-stream-track.json", getClass()).getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			while (reader.ready()) {
				queue.add(reader.readLine());
			}
		} catch (IOException e) {
			try {
				inputStream.close();
			} catch (IOException ignore) {}
		}
		return queue;
	}
}
