package org.ktmiracle100.songforyou.domain.prompt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromptProperty {
    PRODUCT("{product}"),
    BUSINESS("{business}"),
    EMPHASIS("{emphasis}"),
    GENRE("{genre}"),
    ADDPHRASES("{addPhrases}"),
    IMAGESTYLE("{imageStyle}");

    private final String value;
}
