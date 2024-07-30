package org.ktmiracle100.songforyou.domain.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Image extends BaseEntity {

    @Column(nullable = false)
    private Long imageId;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String prompt;
}
