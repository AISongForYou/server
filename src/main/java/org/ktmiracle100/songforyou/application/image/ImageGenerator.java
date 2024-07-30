package org.ktmiracle100.songforyou.application.image;

import org.ktmiracle100.songforyou.domain.image.Image;

public interface ImageGenerator {

    Image generateByPrompt(String prompt);
}
