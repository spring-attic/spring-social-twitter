package org.springframework.social.twitter.api.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpHeaders;
import org.springframework.social.RateLimitExceededException;

public class TwitterRateLimitExceededException extends RateLimitExceededException {
    private static final String X_RATE_LIMIT_RESET = "x-rate-limit-reset";
    private static final String X_RATE_LIMIT_REMAINING = "x-rate-limit-remaining";
    private static final String X_RATE_LIMIT_LIMIT = "x-rate-limit-limit";
    private static final long serialVersionUID = 1L;
    private final Long rateLimitTotal;
    private final Long rateLimitRemaining;
    private final Long rateLimitResetTimeStamp;

    public TwitterRateLimitExceededException(
            final Long rateLimitTotal,
            final Long rateLimitRemaining,
            final Long rateLimitResetTimeStamp) {
        super("twitter");

        this.rateLimitTotal = rateLimitTotal;
        this.rateLimitRemaining = rateLimitRemaining;
        this.rateLimitResetTimeStamp = rateLimitResetTimeStamp;
    }

    public TwitterRateLimitExceededException(final HttpHeaders headers) {
        this(
            readRateLimitTotal(headers),
            readRateLimitRemaining(headers),
            readRateLimitResetTimeStamp(headers));
    }

    public Long getRateLimitTotal() {
        return rateLimitTotal;
    }

    public Long getRateLimitRemaining() {
        return rateLimitRemaining;
    }

    public Long getRateLimitResetTimeStamp() {
        return rateLimitResetTimeStamp;
    }

    public LocalDateTime getRateLimitReset() {
        return fromUnixTimeStamp(rateLimitResetTimeStamp);
    }

    private static Long readRateLimitTotal(final HttpHeaders headers) {
        return returnHeaderAsLongIfAvailable(headers, X_RATE_LIMIT_LIMIT);
    }

    private static Long readRateLimitRemaining(final HttpHeaders headers) {
        return returnHeaderAsLongIfAvailable(headers, X_RATE_LIMIT_REMAINING);
    }

    private static Long readRateLimitResetTimeStamp(final HttpHeaders headers) {
        return returnHeaderAsLongIfAvailable(headers, X_RATE_LIMIT_RESET);
    }

    private static Long returnHeaderAsLongIfAvailable(final HttpHeaders headers, final String headerName) {
        return headers.containsKey(headerName)
            ? Long.valueOf(headers.getFirst(headerName))
            : null;
    }

    private static LocalDateTime fromUnixTimeStamp(final Long rateLimitResetTimeStamp) {
        final Instant i = Instant.ofEpochMilli(rateLimitResetTimeStamp * 1000L);
        return LocalDateTime.ofInstant(i, ZoneOffset.UTC);
    }
}
