package org.ktmiracle100.songforyou.application.song;

import java.util.List;
import org.ktmiracle100.songforyou.domain.song.Song;

public interface SongGenerator {

    List<Song> generateByPrompt(String prompt);
}
