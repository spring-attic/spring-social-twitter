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
package org.springframework.social.twitter.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the video info of a particular media
 * brought back in the entities or extended_entities.
 *
 * @author Hudson Mendes
 *
 */
public class VideoInfoMediaEntity extends TwitterObject {
    private static final long serialVersionUID = 1L;

    private final List<Integer> aspectRatio = new ArrayList<>();
    private final List<VideoInfoVariantMediaEntity> variants = new ArrayList<>();

    public VideoInfoMediaEntity(
            List<Integer> aspectRatio,
            List<VideoInfoVariantMediaEntity> variants) {

        if (aspectRatio != null)
            this.aspectRatio.addAll(aspectRatio);

        if (variants != null)
            this.variants.addAll(variants);
    }

    public List<Integer> getAspectRatio() {
        return aspectRatio;
    }

    public List<VideoInfoVariantMediaEntity> getVariants() {
        return variants;
    }
}
