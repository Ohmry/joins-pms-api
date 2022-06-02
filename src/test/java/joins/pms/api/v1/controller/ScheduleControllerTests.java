package joins.pms.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joins.pms.api.v1.dto.ScheduleDto;
import joins.pms.core.test.ApiInvoker;
import joins.pms.core.test.ApiResultActionsFactory;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScheduleControllerTests {
    @Autowired
    ApiInvoker apiInvoker;
    @Autowired
    ObjectMapper objectMapper;
    private final String API_URL = "/api/v1/schedule";
    private Long scheduleId = null;

    @Test
    @Order(1)
    void 스케줄_리스트_조회 () throws Exception {
        ApiResultActionsFactory.of(this.getMethod())
        this.getMethod()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.count").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(50))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print());
    }

    @Test
    @Order(2)
    void 스케줄_생성 () throws Exception {
        final String scheduleName = "스케줄 생성 테스트";
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setName(scheduleName);
        MvcResult result = this.postMethod(scheduleDto)
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
        this.scheduleId = jsonObject.getLong("data");

        this.getMethod(this.scheduleId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.data.id").value(this.scheduleId))
                .andExpect(jsonPath("$.data.name").value(scheduleName))
                .andDo(print());
    }

    @Test
    @Order(3)
    void 스케줄_수정 () throws Exception {
        MvcResult result = mockMvc.perform(get(API_URL + "/schedule/" + this.scheduleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(this.scheduleId))
                .andReturn();

        ScheduleDto scheduleDto = objectMapper.convertValue(result.getResponse().getContentAsString(), ScheduleDto.class);
        final String newName = scheduleDto.getName() + "_수정";
        scheduleDto.setName(newName);

        mockMvc.perform(put(API_URL + "/schedule/" + this.scheduleId)
                .content(objectMapper.writeValueAsString(scheduleDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.data").value(this.scheduleId))
                .andDo(print());

        mockMvc.perform(get(API_URL + "/schedule"))
    }

    private ResultActions getMethod () throws Exception {
        ResultActions resultActions = mockMvc.perform(get(API_URL).contentType(MediaType.APPLICATION_JSON));
        return new ResultActions() {
            @Override
            public ResultActions andExpect(ResultMatcher matcher) throws Exception {
                return resultActions.andExpect(matcher);
            }

            @Override
            public ResultActions andDo(ResultHandler handler) throws Exception {
                return null;
            }

            @Override
            public MvcResult andReturn() {
                return null;
            }
        }
    }

    private ResultActions getMethod (Long id) throws Exception {
        return mockMvc.perform(get(API_URL + "/" + id).contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions postMethod (ScheduleDto scheduleDto) throws Exception {
        return mockMvc.perform(post(API_URL)
                .content(objectMapper.writeValueAsString(scheduleDto))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions putMethod (ScheduleDto scheduleDto) throws Exception {
        return mockMvc.perform(put(API_URL)
                .content(objectMapper.writeValueAsString(scheduleDto))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions deleteMethod (Long id) throws Exception {
        return mockMvc.perform(delete(API_URL + "/" + id).contentType(MediaType.APPLICATION_JSON));
    }
}
