package org.ktmiracle100.songforyou.application.image;

import org.ktmiracle100.songforyou.application.response.ImageResponse;

public interface ImageGenerator {

    ImageResponse generateByPrompt(String prompt);
}
