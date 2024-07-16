package org.ktmiracle100.songforyou.application.request;

public record GenerateRequest(
        String product,
        String business,
        String emphasis,
        String genre,
        String imageStyle,
        String addPhrases
) {

}
