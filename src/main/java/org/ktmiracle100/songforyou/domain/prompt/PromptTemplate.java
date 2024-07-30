package org.ktmiracle100.songforyou.domain.prompt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.ktmiracle100.songforyou.domain.BaseEntity;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class PromptTemplate extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Media media;

    @Column(nullable = false)
    private String template;

    public String fillTemplate(Map<String, Object> values) {
        String filledTemplate = this.template;

        for (PromptProperty promptProperty : media.promptProperties()) {
            filledTemplate = filledTemplate.replace(
                    promptProperty.getValue(),
                    String.valueOf(values.get(promptProperty.name()))
            );
        }

        return filledTemplate;
    }

    public void updateTemplate(String template) {
        this.template = template;
    }
}
