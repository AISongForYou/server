package org.ktmiracle100.songforyou.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SongForYouControllerTest {

    @InjectMocks
    private SongForYouController songForYouController;

    private MockMvc mockMvc;

    @Test
    void generate() throws Exception {
        mockMvc.perform(get("/api/songforyou/generate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.title").exists())
                .andExpect(jsonPath("$.data.lyrics").exists())
                .andExpect(jsonPath("$.data.image").exists())
                .andExpect(jsonPath("$.data.image.id").exists())
                .andExpect(jsonPath("$.data.image.url").exists());
    }

    @Test
    void generatePromptsTemplates() throws Exception {
        mockMvc.perform(get("/api/songforyou/generate/prompts/templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.prompts").exists())
                .andExpect(jsonPath("$.data.templates").exists());
    }
}