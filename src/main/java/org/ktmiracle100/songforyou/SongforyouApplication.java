package org.ktmiracle100.songforyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SongforyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongforyouApplication.class, args);
    }

}
