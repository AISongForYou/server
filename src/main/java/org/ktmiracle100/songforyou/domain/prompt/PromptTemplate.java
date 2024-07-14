package org.ktmiracle100.songforyou.domain.prompt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.ktmiracle100.songforyou.domain.BaseEntity;

@Accessors(fluent = true)
@Getter
@Entity
public class PromptTemplate extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private String text;

    public PromptTemplate() {
    }

    public PromptTemplate(Category category, String text) {
        this.category = category;
        this.text = text;
    }

    public void updateText(String text) {
        this.text = text;
    }
}
