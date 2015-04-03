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

import java.net.URI;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForHttpEntity;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForUpload;
import org.springframework.social.twitter.api.upload.UploadOperations;
import org.springframework.social.twitter.api.upload.UploadedEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link UploadOperations}, providing single/chunked uploads.
 *
 * @author Chris Latko
 */
public class UploadTemplate extends AbstractTwitterOperations implements UploadOperations {

    private final RestTemplate restTemplate;

    public UploadTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public UploadedEntity uploadSimple(final byte[] data) {
        requireUserAuthorization();

        final MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("media", new ByteArrayResource(data));

        final HttpEntity<?> entity = new TwitterApiBuilderForHttpEntity<>(form)
                .multipart(true)
                .build();

        return restTemplate.exchange(getUri(), HttpMethod.POST, entity, UploadedEntity.class).getBody();
    }

    @Override
    public UploadedEntity uploadChunkedInit(final int totalSize, String contentType) {
        requireUserAuthorization();
        return uploadChunkedInit(totalSize, contentType, null);
    }

    @Override
    public UploadedEntity uploadChunkedInit(final int totalSize, String contentType, List<String> ownerIds) {
        requireUserAuthorization();

        final MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("command", "INIT");
        form.add("total_bytes", String.valueOf(totalSize));
        form.add("media_type", contentType);
        if (null != ownerIds && ownerIds.size() > 0)
            form.add("additional_owners", join(ownerIds, ","));

        final HttpEntity<?> entity = new TwitterApiBuilderForHttpEntity<>(form)
                .multipart(true)
                .build();

        return restTemplate.exchange(
                getUri(),
                HttpMethod.POST,
                entity,
                UploadedEntity.class).getBody();
    }

    @Override
    public HttpStatus uploadChunkedAppend(String mediaId, byte[] data, int segmentId) {
        requireUserAuthorization();

        final MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("command", "APPEND");
        form.add("media_id", mediaId);
        form.add("media", new ByteArrayResource(data));
        form.add("segment_index", String.valueOf(segmentId));

        final HttpEntity<?> entity = new TwitterApiBuilderForHttpEntity<>(form)
                .multipart(true)
                .build();

        final ResponseEntity<UploadedEntity> response = restTemplate.exchange(getUri(), HttpMethod.POST, entity, UploadedEntity.class);
        return response.getStatusCode();
    }

    @Override
    public UploadedEntity uploadChunkedFinalize(String mediaId) {
        requireUserAuthorization();

        final MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("command", "FINALIZE");
        form.add("media_id", mediaId);

        final HttpEntity<?> entity = new TwitterApiBuilderForHttpEntity<>(form)
                .multipart(true)
                .build();

        final ResponseEntity<UploadedEntity> response = restTemplate.exchange(getUri(), HttpMethod.POST, entity, UploadedEntity.class);
        return response.getBody();
    }

    private URI getUri() {
        return new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForUpload.UPLOAD)
                .build();
    }

    private String join(List<String> list, String delim) {
        final StringBuilder sb = new StringBuilder();
        String loopDelim = "";
        for (final String s : list) {
            sb.append(loopDelim);
            sb.append(s);
            loopDelim = delim;
        }
        return sb.toString();
    }

}
