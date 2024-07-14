package org.ktmiracle100.songforyou.application.prompt;

import java.util.Optional;
import org.ktmiracle100.songforyou.domain.prompt.Category;
import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;

public interface PromptDynamicRepository {

    Optional<PromptTemplate> findRecentByCategory(Category category);

}
