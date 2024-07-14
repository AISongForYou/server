package org.ktmiracle100.songforyou.domain.prompt;

import static org.ktmiracle100.songforyou.domain.prompt.Property.ATMOSPHERE;
import static org.ktmiracle100.songforyou.domain.prompt.Property.BUSINESS;
import static org.ktmiracle100.songforyou.domain.prompt.Property.EMPHASIS;
import static org.ktmiracle100.songforyou.domain.prompt.Property.GENRE;
import static org.ktmiracle100.songforyou.domain.prompt.Property.PRODUCT;
import static org.ktmiracle100.songforyou.domain.prompt.Property.WORDS;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    SONG(List.of(PRODUCT, BUSINESS, EMPHASIS, GENRE)),
    IMAGE(List.of(PRODUCT, BUSINESS, EMPHASIS, WORDS, ATMOSPHERE));

    private final List<Property> properties;
}
