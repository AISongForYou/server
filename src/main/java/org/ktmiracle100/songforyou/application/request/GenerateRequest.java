package org.ktmiracle100.songforyou.application.request;

import lombok.Setter;

public record GenerateRequest(
        String product,
        String business,
        String emphasis,
        String genre,
        String imageStyle,
        String addPhrases
) {

    public GenerateRequest reset(String business, String emphasis) {
        return new GenerateRequest(
                this.product,
                business,
                emphasis,
                this.genre,
                this.imageStyle,
                this.addPhrases
        );
    }
}
