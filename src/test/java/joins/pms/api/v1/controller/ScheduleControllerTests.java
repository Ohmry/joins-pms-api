package joins.pms.api.v1.controller;

import joins.pms.core.api.ApiStatus;
import joins.pms.core.test.ApiInvoker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

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

    private JSONObject getDataTranferObject () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "테스트 스케줄정보 생성");
        jsonObject.put("description", "테스트로 생성한 스케줄 정보입니다.");
        jsonObject.put("startDe", "20220606");
        jsonObject.put("endDe", "20220606");
        jsonObject.put("status", "READY");
        jsonObject.put("progress", 0);

        JSONArray tags = new JSONArray();
        JSONObject tag = new JSONObject();
        tag.put("name", "태그1");
        tags.put(tag);
        tag = new JSONObject();
        tag.put("name", "태그2");
        tags.put(tag);

        jsonObject.put("tags", tags);

        return jsonObject;
    }

    @Test
    @WithMockUser(roles = "USER")
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
    @WithMockUser(roles = "USER")
    @Order(2)
    void 새로운_스케줄_정보_생성 () throws Exception {
        JSONObject request = this.getDataTranferObject();

        MvcResult result = apiInvoker.post(API_URL, request.toString())
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
                .andExpect(jsonPath("$.data.name").value(request.get("name")))
                .andExpect(jsonPath("$.data.description").value(request.get("description")))
                .andExpect(jsonPath("$.data.startDe").value(request.get("startDe")))
                .andExpect(jsonPath("$.data.endDe").value(request.get("endDe")))
                .andExpect(jsonPath("$.data.status").value(request.get("status")))
                .andExpect(jsonPath("$.data.progress").value(request.get("progress")))
                .andExpect(jsonPath("$.data.tags").isArray())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
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
    @WithMockUser(roles = "USER")
    @Order(4)
    void 스케줄_정보_수정 () throws Exception {
        MvcResult result = apiInvoker.get(API_URL + "/1").andReturn();
        String contents = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(contents).getJSONObject("data");
        jsonObject.put("name", "테스트 스케줄정보 생성_수정");
        jsonObject.put("description", "테스트로 생성한 스케줄 정보입니다_수정");
        jsonObject.put("startDe", "20220606");
        jsonObject.put("endDe", "20221231");

        result = apiInvoker.put(API_URL, jsonObject.toString())
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
    @WithMockUser(roles = "USER")
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

    @Test
    @WithMockUser(roles = "USER")
    @Order(6)
    void 스케줄_생성_시_Status_값이_없음 () throws Exception {
        JSONObject request = this.getDataTranferObject();
        request.remove("status");

        apiInvoker.post(API_URL, request.toString())
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Order(7)
    void 스케줄_생성_시_시작일자가_종료일자보다_늦음 () throws Exception {
        JSONObject request = this.getDataTranferObject();
        request.put("startDe", "20221231");
        request.put("endDe", "20220101");

        apiInvoker.post(API_URL, request.toString())
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
