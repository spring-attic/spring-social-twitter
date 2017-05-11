/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl.ton;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withCreatedEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.time.ZonedDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;

/**
 * @author Chris Latko
 */
public class TonTemplateTest extends AbstractTwitterApiTest {
	@SuppressWarnings("unused")
	private final static Log logger = LogFactory.getLog(TonTemplateTest.class);
	private final static String BUCKET_NAME = "ta_partner";
	private final static String RESPONSE_URI = "https://ton.twitter.com/1.1/ton/data/ta_partner/390472547/ffs.txt";
	
    @Test
    public void uploadChunk() throws IOException {
        mockServer
                .expect(requestTo("https://ton.twitter.com/1.1/ton/bucket/ta_partner"))
                .andExpect(method(POST))
                .andRespond(withCreatedEntity(URI.create(RESPONSE_URI)));

        Resource resource = dataResource("hashed_twitter.txt");
        InputStream is = resource.getInputStream();
        String contentType = URLConnection.guessContentTypeFromName(resource.getFilename());
        byte[] data = bufferObj(is);
        ZonedDateTime expiry = ZonedDateTime.now().plusDays(7);
        URI uri = twitter.tonOperations().uploadSingleChunk(BUCKET_NAME, data, contentType, expiry);
        assertEquals(uri.toString(),RESPONSE_URI);
    }

	private byte[] bufferObj(final InputStream is) throws IOException {
    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    	int count;
    	byte[] data = new byte[16384];
    	while((count = is.read(data, 0, data.length)) != -1) {
    		buffer.write(data, 0, count);
    	}
    	buffer.flush();
        return buffer.toByteArray();
    }

}
