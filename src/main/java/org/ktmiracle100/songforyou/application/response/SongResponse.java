package org.ktmiracle100.songforyou.application.response;

public record SongResponse(
        String id,
        String title,
        String lyric,
        String url,
        String genre
) {

}
