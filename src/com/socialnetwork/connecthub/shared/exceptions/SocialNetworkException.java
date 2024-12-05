package com.socialnetwork.connecthub.shared.exceptions;

public class SocialNetworkException extends RuntimeException {
    public SocialNetworkException(String message) {
        super(message);
    }

    public SocialNetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}