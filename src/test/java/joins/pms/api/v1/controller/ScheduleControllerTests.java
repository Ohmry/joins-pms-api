package joins.pms.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joins.pms.api.v1.dto.ScheduleDto;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScheduleControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    private final String API_URL = "/api/v1";

    private final int DUMMY_COUNT = 50;

    @Test
    @Order(1)
    void 스케줄_더미데이터_생성 () throws Exception {
        for (int index = 0; index < DUMMY_COUNT; index++) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setName("스케줄 더미데이터 " + (index + 1));

            mockMvc.perform(post(API_URL + "/schedule")
                    .content(objectMapper.writeValueAsString(scheduleDto))
                    .contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Test
    @Order(2)
    void 스케줄_리스트_조회 () throws Exception {
        mockMvc.perform(get(API_URL + "/schedule")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.count").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(DUMMY_COUNT))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print());
    }

    @Test
    @Order(3)
    void 스케줄_생성 () throws Exception {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setName("스케줄 생성 테스트");
        MvcResult result = mockMvc.perform(post(API_URL + "/schedule")
                        .content(objectMapper.writeValueAsString(scheduleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNumber())
                .andDo(print())
                .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        long scheduleId = jsonObject.getLong("data");

        mockMvc.perform(get(API_URL + "/schedule/" + scheduleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.data.id").value(scheduleId))
                .andDo(print());
    }
}
