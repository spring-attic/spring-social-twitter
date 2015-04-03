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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Represents a time stamp in twitter standard.
 * It's basically a ISO LocalDateTime without the fraction and with the Z.
 *
 * @author Hudson Mendes
 *
 */
public class TwitterTimeStamp {
    private final Instant translated;

    public TwitterTimeStamp(LocalDateTime original) {
        translated = translate(original);
    }

    private Instant translate(LocalDateTime original) {
        return LocalDateTime.of(
                original.getYear(),
                original.getMonth(),
                original.getDayOfMonth(),
                original.getHour(),
                original.getMinute(),
                original.getSecond()).toInstant(ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        return translated.toString();
    }

    public Instant toInstant() {
        return translated;
    }
}
