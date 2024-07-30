package org.ktmiracle100.songforyou.ui;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.image.ImageService;
import org.ktmiracle100.songforyou.application.prompt.PromptManager;
import org.ktmiracle100.songforyou.application.request.MediaGenerateRequest;
import org.ktmiracle100.songforyou.application.request.TemplateSaveRequest;
import org.ktmiracle100.songforyou.application.response.ImageResponse;
import org.ktmiracle100.songforyou.application.response.MediaGenerateResponse;
import org.ktmiracle100.songforyou.application.response.SongResponse;
import org.ktmiracle100.songforyou.application.response.TemplateSaveResponse;
import org.ktmiracle100.songforyou.application.song.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SongForYouController {

    private final PromptManager promptManager;
    private final SongService songService;
    private final ImageService imageService;

    @PostMapping("/generate")
    public ResponseEntity<MediaGenerateResponse> generate(
            @RequestBody MediaGenerateRequest request
    ) {
        String songPrompt = promptManager.generateSongPrompt(request);

        return promptManager.generateImagePrompt(request)
                .map(imagePrompt -> getResponse(songPrompt, imagePrompt))
                .orElseGet(() -> getResponseWithoutImage(songPrompt));
    }

    private ResponseEntity<MediaGenerateResponse> getResponseWithoutImage(String songPrompt) {
        List<SongResponse> songResponses = songService.generateByPrompt(songPrompt);

        String id = songResponses.get(0).id();

        return ResponseEntity.created(URI.create(id))
                .body(new MediaGenerateResponse(songResponses, null));
    }

    private ResponseEntity<MediaGenerateResponse> getResponse(
            String songPrompt,
            String imagePrompt
    ) {
        CompletableFuture<List<SongResponse>> generatedSongs =
                CompletableFuture.supplyAsync(() -> songService.generateByPrompt(songPrompt));
        CompletableFuture<ImageResponse> generatedImage =
                CompletableFuture.supplyAsync(() -> imageService.generateByPrompt(imagePrompt));

        CompletableFuture<MediaGenerateResponse> response =
                generatedSongs.thenCombine(generatedImage, MediaGenerateResponse::new);

        String id = response.join().songs().get(0).id();

        return ResponseEntity.created(URI.create(id)).body(response.join());
    }

    @PostMapping("/generate/prompts/templates")
    public ResponseEntity<TemplateSaveResponse> generatePromptsTemplates(
            @RequestBody TemplateSaveRequest request
    ) {
        TemplateSaveResponse response = promptManager.saveTemplate(request);

        String id = response.id().toString();

        return ResponseEntity.created(URI.create(id)).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Void> health() {
        return ResponseEntity.ok().build();
    }
}
