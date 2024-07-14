package org.ktmiracle100.songforyou.domain.prompt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Property {
    PRODUCT("{product}"),
    BUSINESS("{business}"),
    EMPHASIS("{emphasis}"),
    GENRE("{genre}"),
    WORDS("{words}"),
    ATMOSPHERE("{atmosphere}");

    private final String value;
}
