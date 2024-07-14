package org.ktmiracle100.songforyou.application.image;

import lombok.Getter;
import org.ktmiracle100.songforyou.ui.CustomException;
import org.springframework.http.HttpStatus;

@Getter
public class ImageGenerationException extends CustomException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NO_CONTENT;
    private static final String MESSAGE = "Failed to generate song";

    public ImageGenerationException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
