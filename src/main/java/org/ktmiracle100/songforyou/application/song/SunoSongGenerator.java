package org.ktmiracle100.songforyou.application.song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.domain.song.Song;
import org.ktmiracle100.songforyou.domain.song.SongGenerationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:security.properties")
@RequiredArgsConstructor
@Component
public class SunoSongGenerator implements SongGenerator {

    @Value("${suno.api-url}")
    private String apiUrl;

    public List<Song> generateByPrompt(String prompt) {
        String url = apiUrl + "/api/generate";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("make_instrumental", false);
        requestBody.put("wait_audio", true);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        List<Map<String, Object>> responses;
        try {
            responses = restTemplate.postForObject(url, entity, List.class);
        } catch (Exception e) {
            throw new SongGenerationException(e.getMessage());
        }

        if (responses == null) {
            throw new SongGenerationException();
        }

        return responses.stream()
                .map(response -> new Song(
                        (String) response.get("id"),
                        (String) response.get("title"),
                        (String) response.get("lyric"),
                        (String) response.get("audio_url"),
                        (String) response.get("img_url"),
                        (String) response.get("tags"),
                        prompt))
                .toList();
    }
}
