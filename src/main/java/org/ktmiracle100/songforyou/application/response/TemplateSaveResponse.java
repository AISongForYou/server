package org.ktmiracle100.songforyou.application.response;

import org.ktmiracle100.songforyou.domain.prompt.Media;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;

public record TemplateSaveResponse(
        Long id,
        Media media,
        String template
) {

    public static TemplateSaveResponse from(PromptTemplate promptTemplate) {
        return new TemplateSaveResponse(
                promptTemplate.id(),
                promptTemplate.media(),
                promptTemplate.template()
        );
    }
}
