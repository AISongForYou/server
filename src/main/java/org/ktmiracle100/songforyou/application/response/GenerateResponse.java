package org.ktmiracle100.songforyou.application.response;

import java.util.List;

public record GenerateResponse(
        List<SongResponse> songs
) {

}
