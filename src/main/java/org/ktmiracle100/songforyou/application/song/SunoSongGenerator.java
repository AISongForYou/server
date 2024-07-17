package org.ktmiracle100.songforyou.application.song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.response.SongResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:security.properties")
@RequiredArgsConstructor
@Service
public class SunoSongGenerator implements SongGenerator {

    @Value("${suno.api-url}")
    private String apiUrl;

    public List<SongResponse> generateByPrompt(String prompt) {
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

        List<SongResponse> result = new ArrayList<>();

        for (Map<String, Object> response : responses) {
            SongResponse songResponse = new SongResponse(
                    (String) response.get("id"),
                    (String) response.get("title"),
                    (String) response.get("lyric"),
                    (String) response.get("audio_url"),
                    (String) response.get("tags")
            );

            result.add(songResponse);
        }

        return result;
    }
}
