package org.ktmiracle100.songforyou.application.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(Exception e) {

    public String message() {
        return e.getMessage();
    }

    public HttpStatus httpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
