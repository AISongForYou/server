package org.ktmiracle100.songforyou.ui;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.image.ImageGenerator;
import org.ktmiracle100.songforyou.application.prompt.PromptGenerator;
import org.ktmiracle100.songforyou.application.request.GenerateRequest;
import org.ktmiracle100.songforyou.application.request.TemplateGenerateRequest;
import org.ktmiracle100.songforyou.application.response.GenerateResponse;
import org.ktmiracle100.songforyou.application.response.ImageResponse;
import org.ktmiracle100.songforyou.application.response.SongResponse;
import org.ktmiracle100.songforyou.application.response.TemplateGenerateResponse;
import org.ktmiracle100.songforyou.application.song.SongGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SongForYouController {

    private final PromptGenerator promptGenerator;
    private final SongGenerator songGenerator;
    private final ImageGenerator imageGenerator;

    @PostMapping("/generate")
    public ResponseEntity<GenerateResponse> generate(
            @RequestBody GenerateRequest request
    ) {
        String songPrompt = promptGenerator.generateSongPrompt(request);
        String imagePrompt = promptGenerator.generateImagePrompt(request);

        if (imagePrompt == null) {
            List<SongResponse> songResponses = songGenerator.generateByPrompt(songPrompt);
            return ResponseEntity.created(URI.create(""))
                    .body(new GenerateResponse(songResponses, null));
        }

        CompletableFuture<List<SongResponse>> generatedSongs =
                CompletableFuture.supplyAsync(() -> songGenerator.generateByPrompt(songPrompt));
        CompletableFuture<ImageResponse> generatedImage =
                CompletableFuture.supplyAsync(() -> imageGenerator.generateByPrompt(imagePrompt));

        CompletableFuture<GenerateResponse> response =
                generatedSongs.thenCombine(generatedImage, GenerateResponse::new);

        return ResponseEntity.created(URI.create("")).body(response.join());
    }

    @PostMapping("/generate/prompts/templates")
    public ResponseEntity<TemplateGenerateResponse> generatePromptsTemplates(
            @RequestBody TemplateGenerateRequest request
    ) {
        TemplateGenerateResponse response = promptGenerator.saveTemplate(request);

        return ResponseEntity.created(URI.create("")).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Void> health() {
        return ResponseEntity.ok().build();
    }
}
