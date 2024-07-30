package org.ktmiracle100.songforyou.application.prompt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.domain.prompt.PromptGenerationException;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class ChatGptPromptGenerator implements PromptGenerator {

    @Value("${openai.gpt.api-url}")
    private String apiUrl;

    @Value("${openai.gpt.api-key}")
    private String apiKey;

    public String generatePrompt(PromptTemplate promptTemplate, Map<String, Object> values) {
        String business = summary((String) values.get("BUSINESS"));
        String emphasis = summary((String) values.get("EMPHASIS"));

        values.put("BUSINESS", business);
        values.put("EMPHASIS", emphasis);

        return promptTemplate.fillTemplate(values);
    }

    private String summary(String sentence) {
        if (sentence.length() < 50) {
            return sentence;
        }

        String content = sentence + "\n이 내용을 명사형으로 끝나게 요약해줘. 50자 이내로 요약해줘";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> message = new HashMap<>();
        message.put("role", "system");
        message.put("content", content);
        messages.add(message);
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> stringResponseEntity = restTemplate.postForEntity(apiUrl, entity,
                Map.class);

        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) stringResponseEntity
                    .getBody()
                    .get("choices");

            Map<String, String> responseMessage = (Map<String, String>) choices.get(0)
                    .get("message");

            return responseMessage.get("content");
        } catch (Exception e) {
            throw new PromptGenerationException(e.getMessage());
        }
    }
}
