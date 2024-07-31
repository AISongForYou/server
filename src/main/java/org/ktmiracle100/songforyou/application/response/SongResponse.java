package org.ktmiracle100.songforyou.application.response;

import org.ktmiracle100.songforyou.domain.song.Song;

public record SongResponse(
        String id,
        String title,
        String lyric,
        String url,
        String imgUrl,
        String genre
) {

    public static SongResponse from(Song song) {
        return new SongResponse(
                song.songId(),
                song.title(),
                song.lyric(),
                song.url(),
                song.imgUrl(),
                song.tags()
        );
    }
}
