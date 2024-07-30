package org.ktmiracle100.songforyou.application.prompt;

import java.util.Map;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;

public interface PromptGenerator {

    String generatePrompt(PromptTemplate promptTemplate, Map<String, Object> values);
}
