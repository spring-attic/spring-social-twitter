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
package org.springframework.social.twitter.api.impl;

import java.net.URI;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.util.MultiValueMap;

public abstract class AbstractTwitterOperations {

    private final boolean isUserAuthorized;

    private boolean isAppAuthorized;

    public AbstractTwitterOperations(boolean isUserAuthorized, boolean isAppAuthorized) {
        this.isUserAuthorized = isUserAuthorized;
        this.isAppAuthorized = isAppAuthorized;
    }

    protected void requireUserAuthorization() {
        if (!isUserAuthorized) {
            throw new MissingAuthorizationException("twitter");
        }
    }

    protected void requireAppAuthorization() {
        if (!isAppAuthorized) {
            throw new MissingAuthorizationException("twitter");
        }
    }

    protected void requireEitherUserOrAppAuthorization() {
        if (!isUserAuthorized && !isAppAuthorized) {
            throw new MissingAuthorizationException("twitter");
        }
    }

    /*
     * @deprecated use the {@link TwitterApiUriBuilder} instead. with the builder, you may build URIs for the AdCampaign API as well.
     */
    @Deprecated
    protected URI buildUri(String path) {
        return new TwitterApiBuilderForUri()
                .withResource(path)
                .build();
    }

    /*
     * @deprecated use the {@link TwitterApiUriBuilder} instead. with the builder, you may build URIs for the AdCampaign API as well.
     */
    @Deprecated
    protected URI buildUri(String path, String parameterName, String parameterValue) {
        return new TwitterApiBuilderForUri()
                .withResource(path)
                .withArgument(parameterName, parameterValue)
                .build();
    }

    /*
     * @deprecated use the {@link TwitterApiUriBuilder} instead. with the builder, you may build URIs for the AdCampaign API as well.
     */
    @Deprecated
    protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
        return new TwitterApiBuilderForUri()
                .withResource(path)
                .withArgument(parameters)
                .build();
    }
}
