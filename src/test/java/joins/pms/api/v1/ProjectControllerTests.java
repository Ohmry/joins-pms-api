package joins.pms.api.v1;

import joins.pms.core.domain.Progress;
import joins.pms.core.domain.RowStatus;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProjectControllerTests {
    @Autowired
    MockMvc mockMvc;

    static String accessToken;
    static Long boardId;

    @BeforeAll
    @Rollback(value = false)
    public void 프로젝트_테스트를_위한_사전작업() throws Exception {
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

        request.clear();
        request.put("title", "새로운 Board");
        request.put("description", "새롭게 생성한 Board");

        result = mockMvc.perform(post("/api/v1/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/board/1"))
                .andReturn();

        response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        data = response.getJSONObject("data");
        boardId = data.getLong("id");
    }

    @Test
    @Order(1)
    public void 새로운_Project_생성_시_제목없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "새롭게 생성한 Project");

        mockMvc.perform(post("/api/v1/board/" + boardId + "/project")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    public void 새로운_Project_생성_제목만있음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 Project");

        mockMvc.perform(post("/api/v1/board/" + boardId + "/project")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/board/" + boardId + "/project/1"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("새로운 Project"))
                .andExpect(jsonPath("$.data.description").isEmpty())
                .andExpect(jsonPath("$.data.startDateTime").isNotEmpty())
                .andExpect(jsonPath("$.data.endDateTime").isNotEmpty())
                .andExpect(jsonPath("$.data.progress").value(Progress.READY.toString()));
    }

}
