package org.alviel.user.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExternalCommunicationException extends ResponseStatusException {

    public ExternalCommunicationException(String reason, Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, e);
    }
}
