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
package org.springframework.social.twitter.api.advertising;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents a tailored audience file, used to update the
 * lists available for twitter of the audience that the
 * advertiser whishes to advertise to.
 * 
 * @author Hudson Mendes
 */
public class TailoredAudienceChange extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
    private final String tailoredAudienceId;
    private final String inputFilePath;
    private final TailoredAudienceChangeOperation operation;
    private final TailoredAudienceChangeState state;

    public TailoredAudienceChange(
            String id,
            String tailoredAudienceId,
            String inputFilePath,
            TailoredAudienceChangeOperation operation,
            TailoredAudienceChangeState state) {

        this.id = id;
        this.tailoredAudienceId = tailoredAudienceId;
        this.inputFilePath = inputFilePath;
        this.operation = operation;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getTailoredAudienceId() {
        return tailoredAudienceId;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public TailoredAudienceChangeOperation getOperation() {
        return operation;
    }

    public TailoredAudienceChangeState getState() {
        return state;
    }
}
