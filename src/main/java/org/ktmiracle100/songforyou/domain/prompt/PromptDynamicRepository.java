package org.ktmiracle100.songforyou.domain.prompt;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface PromptDynamicRepository {

    @Query("SELECT pt FROM PromptTemplate pt WHERE pt.media = :media ORDER BY pt.createdAt DESC LIMIT 1")
    Optional<PromptTemplate> findRecentByCategory(Media media);
}
