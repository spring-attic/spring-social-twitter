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
package org.springframework.social.twitter.api.impl.upload;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.upload.ChunkCommandType;
import org.springframework.social.twitter.api.upload.UploadedEntity;

/**
 * @author Chris Latko
 */
public class UploadTemplateTest extends AbstractTwitterApiTest {
    @SuppressWarnings("unused")
    private final static Log logger = LogFactory.getLog(UploadTemplateTest.class);
    private final static String MEDIA_ID = "623694170260049925";
    
    
    @Test
    public void uploadSimple_test() throws IOException {
        mockServer
                .expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
                .andExpect(method(POST))
                .andRespond(withSuccess(jsonResource("upload"), APPLICATION_JSON));

        final Resource resource = dataResource("profilepic.gif");
        final InputStream is = resource.getInputStream();
        final byte[] data = bufferObj(is);

        final UploadedEntity uploadedEntity = twitter.uploadOperations().uploadSimple(data);
        assertEquals(12302, uploadedEntity.getSize());
        assertEquals("image/png", uploadedEntity.getImage().getImageType());
    }

    @Test
    public void uploadChunkedInit_test() {
        final String command = ChunkCommandType.INIT.name();
        final String contentType = "video/mp4";
        final int totalSize = 383631;

        mockServer
                .expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
                .andExpect(method(POST))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"command\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"media_type\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"total_bytes\"")))
                .andExpect(content().string(containsString(command)))
                .andExpect(content().string(containsString(contentType)))
                .andExpect(content().string(containsString(String.valueOf(totalSize))))
                .andRespond(withSuccess(jsonResource("init"), APPLICATION_JSON));

        final UploadedEntity uploadedEntity = twitter.uploadOperations().uploadChunkedInit(totalSize, contentType);
        assertEquals(MEDIA_ID, uploadedEntity.getMediaIdString());
        assertEquals(3599, uploadedEntity.getExpiresAfterSecs());
    }

    @Test
    public void uploadChunkedAppend_test() throws IOException {
        final String command = ChunkCommandType.APPEND.name();
        final Resource resource = dataResource("small.mp4");
        final InputStream is = resource.getInputStream();
        final byte[] data = bufferObj(is);

        mockServer
                .expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
                .andExpect(method(POST))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"command\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"media_id\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"segment_index\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"media\"")))
                .andExpect(content().string(containsString(command)))
                .andExpect(content().string(containsString(MEDIA_ID)))
                .andRespond(withNoContent());

        HttpStatus status = twitter.uploadOperations().uploadChunkedAppend(MEDIA_ID, data, 0);
        assertEquals(204, status.value());
    }

    @Test
    public void uploadChunkedFinalize_test() throws IOException {
        final String command = ChunkCommandType.FINALIZE.name();

        mockServer
                .expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
                .andExpect(method(POST))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"command\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"media_id\"")))
                .andExpect(content().string(containsString(command)))
                .andExpect(content().string(containsString(MEDIA_ID)))
                .andRespond(withSuccess(jsonResource("finalize"), APPLICATION_JSON));

        final UploadedEntity uploadedEntity = twitter.uploadOperations().uploadChunkedFinalize(MEDIA_ID);
        assertEquals(MEDIA_ID, uploadedEntity.getMediaIdString());
        assertEquals(3600, uploadedEntity.getExpiresAfterSecs());
        assertEquals(383631, uploadedEntity.getSize());
        assertEquals("video/mp4", uploadedEntity.getVideo().getVideoType());
    }


    private byte[] bufferObj(final InputStream is) throws IOException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int count;
        final byte[] data = new byte[16384];
        while ((count = is.read(data, 0, data.length)) != -1)
            buffer.write(data, 0, count);
        buffer.flush();
        return buffer.toByteArray();
    }

}
