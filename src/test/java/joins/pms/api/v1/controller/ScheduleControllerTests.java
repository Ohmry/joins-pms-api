package joins.pms.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joins.pms.api.v1.model.dto.ScheduleDto;
import joins.pms.core.test.ApiInvoker;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScheduleControllerTests {
    @Autowired
    ApiInvoker apiInvoker;
    @Autowired
    ObjectMapper objectMapper;
    private final String API_URL = "/api/v1/schedule";
    private static Long scheduleId = null;

    @Test
    @Order(1)
    void 스케줄_생성 () throws Exception {
        final String scheduleName = "스케줄 생성 테스트";
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setName(scheduleName);
        MvcResult result = apiInvoker.post(API_URL, objectMapper.writeValueAsString(scheduleDto))
                .isSuccess()
                .hasCount(1)
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNumber())
                .andDo(print())
                .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        scheduleId = jsonObject.getLong("data");

        apiInvoker.get(API_URL + "/" + scheduleId)
                .isSuccess()
                .hasCount(1)
                .andExpect(jsonPath("$.data.id").value(scheduleId))
                .andExpect(jsonPath("$.data.name").value(scheduleName))
                .andDo(print());
    }

    @Test
    @Order(2)
    void 스케줄_조회 () throws Exception {
        apiInvoker.get(API_URL)
                .isSuccess()
                .hasCount(1)
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print());
    }

    @Test
    @Order(3)
    void 스케줄_수정 () throws Exception {
        MvcResult result = apiInvoker.get(API_URL + "/" + scheduleId)
                .isSuccess()
                .andExpect(jsonPath("$.data.id").value(scheduleId))
                .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));

        ScheduleDto scheduleDto = objectMapper.readValue(jsonObject.getString("data"), ScheduleDto.class);
        final String newName = scheduleDto.getName() + "_수정";
        scheduleDto.setName(newName);

        apiInvoker.put(API_URL, objectMapper.writeValueAsString(scheduleDto))
                .isSuccess()
                .hasCount(1)
                .andExpect(jsonPath("$.data").value(scheduleId))
                .andDo(print());

        apiInvoker.get(API_URL + "/" + scheduleId)
                .isSuccess()
                .hasCount(1)
                .andExpect(jsonPath("$.data.id").value(scheduleId))
                .andExpect(jsonPath("$.data.name").value(newName))
                .andDo(print());
    }

    @Test
    @Order(4)
    void 스케줄_삭제 () throws Exception {
        apiInvoker.delete(API_URL + "/" + scheduleId)
                .isSuccess()
                .andDo(print());

        apiInvoker.get(API_URL + "/" + scheduleId)
                .isSuccess()
                .hasCount(0)
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }
}
