package org.ktmiracle100.songforyou.application.response;

import org.ktmiracle100.songforyou.domain.prompt.Category;

public record TemplateGenerateResponse(
        Long id,
        Category category,
        String text
) {

}
