package org.ktmiracle100.songforyou.domain.prompt;

import org.ktmiracle100.songforyou.domain.CustomException;
import org.springframework.http.HttpStatus;

public class PromptGenerationException extends CustomException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NO_CONTENT;
    private static final String MESSAGE = "Failed to generate prompt";

    public PromptGenerationException() {
        super(HTTP_STATUS, MESSAGE);
    }

    public PromptGenerationException(String message) {
        super(HTTP_STATUS, message);
    }
}
