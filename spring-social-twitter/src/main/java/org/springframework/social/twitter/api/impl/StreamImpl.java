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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamingException;

class StreamImpl implements Stream {
	
	private volatile boolean open;
		
	private final InputStream inputStream;

	private final BufferedReader reader;
	
	private final Queue<String> queue;

	private final StreamDispatcher dispatcher;
	
	public StreamImpl(InputStream inputStream, List<StreamListener> listeners) {
		this.inputStream = inputStream;
		this.reader = new BufferedReader(new InputStreamReader(inputStream));
		queue = new ConcurrentLinkedQueue<String>();
		dispatcher = new StreamDispatcher(queue, listeners);
		new Thread(dispatcher).start();
		open = true;		
	}
	
	public void next() {
		try {
			String line = reader.readLine();
			if(line == null) {
				throw new IOException("Stream closed");
			}			
			queue.add(line);
		} catch (IOException e) {
			if(open) {
				close();
				throw new StreamingException("The Stream is closed", e);
			}
		}
	}

	public void close() {
		try {
			open = false;
			dispatcher.stop();
			inputStream.close();
		} catch(IOException ignore) {}
	}

}
