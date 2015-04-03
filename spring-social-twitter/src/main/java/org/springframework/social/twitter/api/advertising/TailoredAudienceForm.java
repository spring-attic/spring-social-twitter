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
 * Defines the contract for the input form builder of {@link TailoredAudience}.
 * 
 * @author Hudson Mendes
 */
public interface TailoredAudienceForm extends TwitterForm {

    /**
     * Defines the type of the list for the {@link TailoredAudience}
     * 
     * @param listType is one amongst the set out in {@link TailoredAudienceListType}
     * @return the fluent builder
     */
    public TailoredAudienceForm ofListType(TailoredAudienceListType listType);

    /**
     * Defines the name of the tailored {@link TailoredAudience}
     * 
     * @param name of the {@link TailoredAudience}.
     * @return the fluent builder
     */
    public TailoredAudienceForm named(String name);
}
