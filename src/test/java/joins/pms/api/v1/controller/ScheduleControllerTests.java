package joins.pms.api.v1.controller;

import joins.pms.core.api.ApiStatus;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScheduleControllerTests {
    @Autowired
    ApiInvoker apiInvoker;
    private final String API_URL = "/api/v1/schedule";

    @Test
    @Order(1)
    void 비어있는_DB에서_스케줄_조회 () throws Exception {
        apiInvoker.get(API_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.DATA_IS_EMPTY.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.DATA_IS_EMPTY.getMessage()))
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @Order(2)
    void 새로운_스케줄_정보_생성 () throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "테스트 스케줄정보 생성");
        jsonObject.put("description", "테스트로 생성한 스케줄 정보입니다.");
        jsonObject.put("startDe", "20220606");
        jsonObject.put("endDe", "20220606");

        MvcResult result = apiInvoker.post(API_URL, jsonObject.toString())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(print())
                .andReturn();

        String uri = result.getResponse().getHeader("Location");
        assert uri != null;
        Long id = Long.parseLong(uri.substring(uri.length() - 1));

        apiInvoker.get(API_URL + "/" + id)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value(jsonObject.get("name")))
                .andExpect(jsonPath("$.data.description").value(jsonObject.get("description")))
                .andExpect(jsonPath("$.data.startDe").value(jsonObject.get("startDe")))
                .andExpect(jsonPath("$.data.endDe").value(jsonObject.get("endDe")))
                .andDo(print());
    }

    @Test
    @Order(3)
    void 스케줄이_생성된_이후_조회 () throws Exception {
        apiInvoker.get(API_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
    }

    @Test
    @Order(4)
    void 스케줄_정보_수정 () throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "테스트 스케줄정보 생성_수정");
        jsonObject.put("description", "테스트로 생성한 스케줄 정보입니다_수정");
        jsonObject.put("startDe", "20220606");
        jsonObject.put("endDe", "20221231");

        MvcResult result = apiInvoker.put(API_URL, jsonObject.toString())
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String uri = result.getResponse().getHeader("Location");
        assert uri != null;
        Long id = Long.parseLong(uri.substring(uri.length() - 1));

        apiInvoker.get(API_URL + "/" + id)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value(jsonObject.get("name")))
                .andExpect(jsonPath("$.data.description").value(jsonObject.get("description")))
                .andExpect(jsonPath("$.data.startDe").value(jsonObject.get("startDe")))
                .andExpect(jsonPath("$.data.endDe").value(jsonObject.get("endDe")))
                .andDo(print());
    }

    @Test
    @Order(5)
    void 스케줄_삭제 () throws Exception {
        apiInvoker.delete(API_URL + "/1")
                .andExpect(status().isNoContent())
                .andDo(print());

        apiInvoker.get(API_URL + "/1")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.DATA_IS_EMPTY.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.DATA_IS_EMPTY.getMessage()))
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());

    }
}
