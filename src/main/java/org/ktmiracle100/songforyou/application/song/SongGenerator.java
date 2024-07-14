package org.ktmiracle100.songforyou.application.song;

import java.util.List;
import org.ktmiracle100.songforyou.application.response.SongResponse;

public interface SongGenerator {

    List<SongResponse> generateByPrompt(String prompt);
}
