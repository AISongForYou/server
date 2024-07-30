package org.ktmiracle100.songforyou.domain.prompt;

import static org.ktmiracle100.songforyou.domain.prompt.PromptProperty.ADDPHRASES;
import static org.ktmiracle100.songforyou.domain.prompt.PromptProperty.BUSINESS;
import static org.ktmiracle100.songforyou.domain.prompt.PromptProperty.EMPHASIS;
import static org.ktmiracle100.songforyou.domain.prompt.PromptProperty.GENRE;
import static org.ktmiracle100.songforyou.domain.prompt.PromptProperty.IMAGESTYLE;
import static org.ktmiracle100.songforyou.domain.prompt.PromptProperty.PRODUCT;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
public enum Media {
    SONG(List.of(PRODUCT, BUSINESS, EMPHASIS, GENRE)),
    IMAGE(List.of(PRODUCT, BUSINESS, ADDPHRASES, IMAGESTYLE));

    private final List<PromptProperty> promptProperties;
}
