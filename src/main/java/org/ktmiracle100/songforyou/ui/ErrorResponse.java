package org.ktmiracle100.songforyou.ui;

import org.springframework.http.HttpStatus;

public record ErrorResponse(Exception e) {

        public String message() {
            return e.getMessage();
        }

        public HttpStatus httpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
}
