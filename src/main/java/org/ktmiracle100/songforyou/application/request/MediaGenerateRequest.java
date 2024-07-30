package org.ktmiracle100.songforyou.application.request;

public record MediaGenerateRequest(
        String product,
        String business,
        String emphasis,
        String genre,
        String imageStyle,
        String addPhrases
) {

    public MediaGenerateRequest reset(String business, String emphasis) {
        return new MediaGenerateRequest(
                this.product,
                business,
                emphasis,
                this.genre,
                this.imageStyle,
                this.addPhrases
        );
    }
}
