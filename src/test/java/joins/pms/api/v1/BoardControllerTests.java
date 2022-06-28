package joins.pms.api.v1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BoardControllerTests {
    @Autowired
    MockMvc mockMvc;

    static String accessToken;

    @BeforeAll
    public void 사용자_생성_후_토큰발급() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "이병훈");
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "oohmry");

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        MvcResult result = mockMvc.perform(post("/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        accessToken = data.getString("accessToken");
    }

    @Test
    @Order(1)
    public void 새로운_Board_생성_시_제목없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "새롭게 생성한 Board");

        mockMvc.perform(post("/api/v1/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void 새로운_Board_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 Board");
        request.put("description", "새롭게 생성한 Board");

        mockMvc.perform(post("/api/v1/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/board/1"))
                .andExpect(jsonPath("$.data.title").value("새로운 Board"))
                .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Board"));
    }

    @Test
    @Order(3)
    public void 생성된_Board_정보_조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("새로운 Board"))
                .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Board"));
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void Borad_30개_일괄_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "새롭게 생성한 Board");

        for (int index = 0; index < 30; index++) {
            request.put("title", "새로운 Board (" + (index + 1) + ")");
            mockMvc.perform(post("/api/v1/board")
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request.toString()))
                    .andExpect(status().isCreated())
                    .andExpect(header().stringValues("Location", "/api/v1/board/" + (index + 2)))
                    .andExpect(jsonPath("$.data.title").value("새로운 Board (" + (index + 1) + ")"))
                    .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Board"));
        }
    }

    @Test
    @Order(5)
    public void Board_목록_조회() throws Exception {
        int pageNo = 0;
        int recordCount = 20;

        MvcResult result = mockMvc.perform(get("/api/v1/board?pageNo=" + pageNo + "&recordCount=" + recordCount)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONArray data = response.getJSONArray("data");
        Assertions.assertEquals(recordCount, data.length());
    }

    @Test
    @Order(6)
    public void Board_정보_수정_시_ID누락() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "수정된 Board");
        request.put("description", "정보가 수정된 Board");

        mockMvc.perform(put("/api/v1/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void Board_정보_수정_시_TITLE_누락() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("description", "정보가 수정된 Board");

        mockMvc.perform(put("/api/v1/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    public void Board_정보_수정_시_Board가_없는경우() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 9999);
        request.put("title", "수정된 Board");
        request.put("description", "정보가 수정된 Board");

        mockMvc.perform(put("/api/v1/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    @Rollback(value = false)
    public void Board_정보_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("title", "수정된 Board");
        request.put("description", "정보가 수정된 Board");

        mockMvc.perform(put("/api/v1/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/board/1"))
                .andExpect(jsonPath("$.data.title").value("수정된 Board"))
                .andExpect(jsonPath("$.data.description").value("정보가 수정된 Board"));
    }

    @Test
    @Order(10)
    public void 수정한_Board_정보_조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("수정된 Board"))
                .andExpect(jsonPath("$.data.description").value("정보가 수정된 Board"));
    }

    @Test
    @Order(11)
    public void Board_삭제_시_Board가_없는_경우() throws Exception {
        mockMvc.perform(delete("/api/v1/board/999")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(12)
    @Rollback(value = false)
    public void Board_삭제() throws Exception {
        mockMvc.perform(delete("/api/v1/board/1")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(13)
    public void 삭제된_Board_조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }

}
