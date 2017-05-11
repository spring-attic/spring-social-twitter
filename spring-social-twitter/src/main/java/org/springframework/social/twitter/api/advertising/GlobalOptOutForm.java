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

import org.springframework.social.twitter.api.TwitterForm;

/**
 * Describes the contract for the builder of {@link GlobalOptOut}'s
 * data that will be posted / patched to the endpoint.
 * 
 * @author Hudson Mendes
 */
public interface GlobalOptOutForm extends TwitterForm {

    /**
     * The TON file path that will be linked with the opt-out operation.
     * 
     * @param tonFilePath in TON.
     * @return the fluent builder.
     */
    public abstract GlobalOptOutForm withInputFilePath(String tonFilePath);

    /**
     * The list type of the data in the TON file.
     * 
     * @param listType of the data in the TON file.
     * @return the fluent builder.
     */
    public abstract GlobalOptOutForm withListType(TailoredAudienceListType listType);

}
