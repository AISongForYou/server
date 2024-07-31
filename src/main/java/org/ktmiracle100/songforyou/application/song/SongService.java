package org.ktmiracle100.songforyou.application.song;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.response.SongResponse;
import org.ktmiracle100.songforyou.domain.song.Song;
import org.ktmiracle100.songforyou.domain.song.SongRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SongService {

    private final SongGenerator songGenerator;
    private final SongRepository songRepository;

    public List<SongResponse> generateByPrompt(String songPrompt) {
        List<Song> songs = songGenerator.generateByPrompt(songPrompt);

        songRepository.saveAll(songs);

        return songs.stream()
                .map(SongResponse::from)
                .toList();
    }
}
