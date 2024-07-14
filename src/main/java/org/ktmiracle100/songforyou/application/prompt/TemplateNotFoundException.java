package org.ktmiracle100.songforyou.application.prompt;

import lombok.Getter;
import org.ktmiracle100.songforyou.ui.CustomException;
import org.springframework.http.HttpStatus;

@Getter
public class TemplateNotFoundException extends CustomException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "No Template";

    public TemplateNotFoundException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
