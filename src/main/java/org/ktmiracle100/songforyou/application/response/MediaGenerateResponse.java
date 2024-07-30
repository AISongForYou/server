package org.ktmiracle100.songforyou.application.response;

import java.util.List;

public record MediaGenerateResponse(
        List<SongResponse> songs,
        ImageResponse image
) {

}
