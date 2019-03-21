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

import static java.util.Arrays.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamingException;
import org.springframework.social.twitter.api.Tweet;

public class StreamImplTest {

	@Test
	@Ignore("TODO: Figure out a better way of testing this")
	public void next() throws Exception {
		StreamListener mockListener = mock(StreamListener.class);		
		InputStream inputStream = new ClassPathResource("filter-stream-track.json", getClass()).getInputStream();
		StreamReaderImpl stream = new StreamReaderImpl(inputStream, asList(mockListener));
		try {
			while(true) {
				stream.next();
			}
		} catch(StreamingException e) {}
		Thread.sleep(1000);
		verify(mockListener, times(4)).onTweet(any(Tweet.class));
		inputStream.close();
	}
}
