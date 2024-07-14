package org.ktmiracle100.songforyou.application.prompt;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.request.GenerateRequest;
import org.ktmiracle100.songforyou.application.request.TemplateGenerateRequest;
import org.ktmiracle100.songforyou.application.response.TemplateGenerateResponse;
import org.ktmiracle100.songforyou.domain.prompt.Category;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;
import org.ktmiracle100.songforyou.domain.prompt.Property;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PromptGenerator {

    private final PromptTemplateRepository promptTemplateRepository;

    public String generate(GenerateRequest request, Category category) {
        PromptTemplate promptTemplate = promptTemplateRepository.findRecentByCategory(category)
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
