package org.ktmiracle100.songforyou.application.image;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.response.ImageResponse;
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
public class DalleImageGenerator implements ImageGenerator {

    @Value("${openai.dalle.api-url}")
    private String apiUrl;

    @Value("${openai.dalle.api-key}")
    private String apiKey;

    public ImageResponse generateByPrompt(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("size", "1792x1024");
        requestBody.put("n", 1);
        requestBody.put("quality", "hd");
        requestBody.put("style", "vivid");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        DalleResponse response;

        try {
            response = restTemplate.postForObject(apiUrl, entity, DalleResponse.class);
        } catch (Exception e) {
            throw new ImageGenerationException(e.getMessage());
        }


        if (response == null) {
            throw new ImageGenerationException();
        }

        Long id = response.created();
        String url = (String) response.data().get(0).get("url");

        return new ImageResponse(id, url);
    }
}
