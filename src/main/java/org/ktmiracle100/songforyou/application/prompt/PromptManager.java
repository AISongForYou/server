package org.ktmiracle100.songforyou.application.prompt;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktmiracle100.songforyou.application.request.MediaGenerateRequest;
import org.ktmiracle100.songforyou.application.request.TemplateSaveRequest;
import org.ktmiracle100.songforyou.application.response.TemplateSaveResponse;
import org.ktmiracle100.songforyou.domain.prompt.Media;
import org.ktmiracle100.songforyou.domain.prompt.PromptGenerationException;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplateRepository;
import org.ktmiracle100.songforyou.domain.prompt.TemplateNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PromptManager {

    private final PromptGenerator promptGenerator;
    private final PromptTemplateRepository promptTemplateRepository;

    public String generateSongPrompt(MediaGenerateRequest request) {
        return generatePrompt(request, Media.SONG)
                .orElseThrow(() -> new PromptGenerationException("Failed to generate song prompt"));
    }

    public Optional<String> generateImagePrompt(MediaGenerateRequest request) {
        if (request.imageStyle() == null && request.addPhrases() == null) {
            return Optional.empty();
        }

        return generatePrompt(request, Media.IMAGE);
    }

    private Optional<String> generatePrompt(MediaGenerateRequest request, Media media) {
        PromptTemplate promptTemplate = promptTemplateRepository.findRecentByCategory(media)
                .orElseThrow(TemplateNotFoundException::new);

        Map<String, Object> values = getValuesFromRequest(request);

        return Optional.of(promptGenerator.generatePrompt(promptTemplate, values));
    }

    private Map<String, Object> getValuesFromRequest(MediaGenerateRequest request) {
        Map<String, Object> values = new HashMap<>();

        for (Field field : MediaGenerateRequest.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(request);
                values.put(field.getName().toUpperCase(), value);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Failed to access field: " + field.getName(), e);
            } finally {
                field.setAccessible(false);
            }
        }

        return values;
    }

    public TemplateSaveResponse saveTemplate(TemplateSaveRequest request) {
        Media media = Media.valueOf(request.category().toUpperCase());
        String template = request.template();

        PromptTemplate promptTemplate = promptTemplateRepository.findRecentByCategory(media)
                .orElseGet(
                        () -> promptTemplateRepository.save(new PromptTemplate(media, template)));

        promptTemplate.updateTemplate(template);

        return TemplateSaveResponse.from(promptTemplate);
    }
}
