package org.ktmiracle100.songforyou.application.prompt;

import org.ktmiracle100.songforyou.domain.prompt.PromptTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptTemplateRepository extends JpaRepository<PromptTemplate, Long>,
        PromptDynamicRepository {

}
