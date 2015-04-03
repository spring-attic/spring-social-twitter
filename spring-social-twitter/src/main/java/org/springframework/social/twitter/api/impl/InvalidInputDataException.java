package org.springframework.social.twitter.api.impl;

import java.util.List;

import org.springframework.social.ApiException;

public class InvalidInputDataException extends ApiException {

    private static final long serialVersionUID = 1L;

    public InvalidInputDataException(String providerId, List<String> validationErrors) {
        super(providerId, formatValidationErrors(validationErrors));
    }

    private static String formatValidationErrors(List<String> validationErrors) {
        StringBuilder builder = new StringBuilder();
        builder.append("Some validation errors occurred:\n");
        for (String message : validationErrors)
            builder.append(" - " + message + "\n");
        return builder.toString();
    }
}
