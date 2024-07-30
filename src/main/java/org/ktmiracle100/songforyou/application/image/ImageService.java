package org.ktmiracle100.songforyou.application.image;

import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.image.ImageGenerator;
import org.ktmiracle100.songforyou.application.response.ImageResponse;
import org.ktmiracle100.songforyou.domain.image.Image;
import org.ktmiracle100.songforyou.domain.image.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ImageService {

    private final ImageGenerator imageGenerator;
    private final ImageRepository imageRepository;

    public ImageResponse generateByPrompt(String imagePrompt) {
        Image image = imageGenerator.generateByPrompt(imagePrompt);

        imageRepository.save(image);

        return ImageResponse.from(image);
    }
}
