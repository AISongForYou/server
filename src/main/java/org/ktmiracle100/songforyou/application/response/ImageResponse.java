package org.ktmiracle100.songforyou.application.response;

import org.ktmiracle100.songforyou.domain.image.Image;

public record ImageResponse(
        Long id,
        String url
) {

    public static ImageResponse from(Image image) {
        return new ImageResponse(image.imageId(), image.url());
    }
}
