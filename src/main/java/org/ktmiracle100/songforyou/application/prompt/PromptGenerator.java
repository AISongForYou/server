package org.ktmiracle100.songforyou.application.prompt;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.request.GenerateRequest;
import org.ktmiracle100.songforyou.application.request.TemplateGenerateRequest;
import org.ktmiracle100.songforyou.application.response.TemplateGenerateResponse;
import org.ktmiracle100.songforyou.domain.prompt.Category;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;
import org.ktmiracle100.songforyou.domain.prompt.Property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Transactional
@Service
public class PromptGenerator {

    private final PromptTemplateRepository promptTemplateRepository;

    @Value("${openai.gpt.api-url}")
    private String apiUrl;

    @Value("${openai.gpt.api-key}")
    private String apiKey;

    public String generateSongPrompt(GenerateRequest request) {
        PromptTemplate promptTemplate = promptTemplateRepository.findRecentByCategory(Category.SONG)
                .orElseThrow(TemplateNotFoundException::new);

        String prompt = fillTemplate(promptTemplate, request);

        if (prompt.length() >= 290) {
            String newPrompt =
                    prompt + "\n 위의 내용을 형식은 그대로 두고 각각의 항목을 요약해서 290자 이내로 만들어줘.\n 답변은 위의 형식대로만 줘";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("prompt", newPrompt);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> stringResponseEntity = restTemplate.postForEntity(apiUrl, entity,
                    Map.class);
            List<Map<String, String>> choices = (List<Map<String, String>>) stringResponseEntity.getBody().get("choices");

            String text = choices.get(0).get("text");

            if (text.length() <= 290) {
                prompt = text;
            }
        }

        return prompt;
    }

    public String generateImagePrompt(GenerateRequest request) {
        if (request.imageStyle() == null && request.addPhrases() == null) {
            return null;
        }

        PromptTemplate promptTemplate = promptTemplateRepository.findRecentByCategory(Category.IMAGE)
                .orElseThrow(TemplateNotFoundException::new);

        return fillTemplate(promptTemplate, request);
    }

    private String fillTemplate(PromptTemplate template, GenerateRequest request) {
        String text = template.text();

        Map<String, Object> valueMap = getValueMapFromRequest(request);

        for (Property property : Property.values()) {
            text = text.replace(property.getValue(), String.valueOf(valueMap.get(property.name())));
        }

        return text;
    }

    private Map<String, Object> getValueMapFromRequest(GenerateRequest request) {
        Map<String, Object> valueMap = new HashMap<>();

        for (Field field : GenerateRequest.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(request);
                valueMap.put(field.getName().toUpperCase(), value);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Failed to access field: " + field.getName(), e);
            } finally {
                field.setAccessible(false);
            }
        }

        return valueMap;
    }

    public TemplateGenerateResponse saveTemplate(TemplateGenerateRequest request) {
        Category category = Category.valueOf(request.category().toUpperCase());
        String text = request.text();

        PromptTemplate promptTemplate = promptTemplateRepository.findRecentByCategory(category)
                .orElseGet(() -> promptTemplateRepository.save(new PromptTemplate(category, text)));

        promptTemplate.updateText(text);

        return new TemplateGenerateResponse(
                promptTemplate.id(),
                promptTemplate.category(),
                promptTemplate.text()
        );
    }
}
