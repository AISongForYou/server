package org.ktmiracle100.songforyou.application.song;

import lombok.Getter;
import org.ktmiracle100.songforyou.ui.CustomException;
import org.springframework.http.HttpStatus;

@Getter
public class SongGenerationException extends CustomException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NO_CONTENT;
    private static final String MESSAGE = "Failed to generate song";

    public SongGenerationException() {
        super(HTTP_STATUS, MESSAGE);
    }

    public SongGenerationException(String message) {
        super(HTTP_STATUS, message);
    }
}
