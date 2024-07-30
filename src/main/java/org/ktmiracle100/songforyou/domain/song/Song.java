package org.ktmiracle100.songforyou.domain.song;

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
public class Song extends BaseEntity {

    @Column(nullable = false)
    private String songId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String lyric;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String tags;

    @Column(nullable = false)
    private String prompt;
}
