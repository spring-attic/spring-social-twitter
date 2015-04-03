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
package org.springframework.social.twitter.api.upload;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;

/**
 * Interface defining media uploads.
 *
 * @author Chris Latko
 */
public interface UploadOperations {

    /**
     * Media to be used with tweets need to be uploaded here. Only media (not mediat_data) is supported.
     *
     * @param media the binary data
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     * @return MediaEntity a JSON representation of metadata of the uploaded media.
     */
    UploadedEntity uploadSimple(final byte[] media);

    /**
     * Video uploads. They are chunked, this is the init stage.
     * For more info: https://dev.twitter.com/rest/reference/post/media/upload-chunked.
     *
     * @param totalSize the total size of the entire upload
     * @param contentType mime type of data, must conform to http://www.iana.org/assignments/media-types/media-types.xhtml
     * @param ownerIds Comma-separated string of user IDs to set as additional owners ; maximum of 100
     * @return UploadedEntity
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    UploadedEntity uploadChunkedInit(final int totalSize, final String contentType, final List<String> ownerIds);
    UploadedEntity uploadChunkedInit(final int totalSize, final String contentType);

    /**
     * Video uploads. They are chunked, this is the append stage.
     * For more info: https://dev.twitter.com/rest/reference/post/media/upload-chunked.
     *
     * @param mediaId returned from INIT, this is needed to resume the upload
     * @param data the binary data
     * @param segmentId need to reassemble the appends in the proper order; 0-999 inclusive
     * @return HttpStatus
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    HttpStatus uploadChunkedAppend(final String mediaId, final byte[] data, final int segmentId);

    /**
     * Video uploads. They are chunked, this is the finalize stage
     * For more info: https://dev.twitter.com/rest/reference/post/media/upload-chunked.
     *
     * @param mediaId returned from INIT, this is needed to finalize the upload
     * @return UploadedEntity
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    UploadedEntity uploadChunkedFinalize(final String mediaId);

}
