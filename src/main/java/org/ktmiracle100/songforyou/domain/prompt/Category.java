package org.ktmiracle100.songforyou.domain.prompt;

import static org.ktmiracle100.songforyou.domain.prompt.Property.IMAGESTYLE;
import static org.ktmiracle100.songforyou.domain.prompt.Property.BUSINESS;
import static org.ktmiracle100.songforyou.domain.prompt.Property.EMPHASIS;
import static org.ktmiracle100.songforyou.domain.prompt.Property.GENRE;
import static org.ktmiracle100.songforyou.domain.prompt.Property.PRODUCT;
import static org.ktmiracle100.songforyou.domain.prompt.Property.ADDPHRASES;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    SONG(List.of(PRODUCT, BUSINESS, EMPHASIS, GENRE)),
    IMAGE(List.of(PRODUCT, BUSINESS, ADDPHRASES, IMAGESTYLE));

    private final List<Property> properties;
}
