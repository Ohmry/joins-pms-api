package joins.pms.api;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Rollback(value = true)
public class GroupControllerTests {
    @Autowired
    MockMvc mockMvc;

    static String accessToken;

    @BeforeAll
    public void 사용자_생성_및_로그인() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "admin");
        request.put("name", "이병훈");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()));

        MvcResult result = mockMvc.perform(post("/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        accessToken = data.getString("accessToken");
    }

    @Test
    @Order(1)
    public void 새로운_그룹_생성_시_이름없음() throws Exception {
        JSONObject request = new JSONObject();

        mockMvc.perform(post("/api/group")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void 새로운_그룹_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "새로운 Group");

        mockMvc.perform(post("/api/group")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues(HttpHeaders.LOCATION, "/api/group/1"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("새로운 Group"))
                .andExpect(jsonPath("$.data.ownerId").value(1))
                .andExpect(jsonPath("$.data.ownerName").value("이병훈"))
                .andExpect(jsonPath("$.data.users").isArray())
                .andExpect(jsonPath("$.data.boards").isArray());
    }

    @Test
    @Order(3)
    public void 새로운_그룹_정보조회() throws Exception {
        mockMvc.perform(get("/api/group/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("새로운 Group"))
                .andExpect(jsonPath("$.data.ownerId").value(1))
                .andExpect(jsonPath("$.data.ownerName").value("이병훈"))
                .andExpect(jsonPath("$.data.users").isArray())
                .andExpect(jsonPath("$.data.boards").isArray());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void 그룹_이름_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("name", "수정된 Group");

        mockMvc.perform(put("/api/group")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues(HttpHeaders.LOCATION, "/api/group/1"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("수정된 Group"))
                .andExpect(jsonPath("$.data.ownerId").value(1))
                .andExpect(jsonPath("$.data.ownerName").value("이병훈"))
                .andExpect(jsonPath("$.data.users").isArray())
                .andExpect(jsonPath("$.data.boards").isArray());
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void 그룹_내_사용자_추가() throws Exception {
        JSONObject request = new JSONObject();
        request.put("userId", 1);

        mockMvc.perform(post("/api/group/1/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("수정된 Group"))
                .andExpect(jsonPath("$.data.ownerId").value(1))
                .andExpect(jsonPath("$.data.ownerName").value("이병훈"))
                .andExpect(jsonPath("$.data.users").isArray())
                .andExpect(jsonPath("$.data.users[0].id").value(1))
                .andExpect(jsonPath("$.data.users[0].name").value("이병훈"))
                .andExpect(jsonPath("$.data.users[0].email").value("o.ohmry@gmail.com"))
                .andExpect(jsonPath("$.data.boards").isArray());
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void 그룹_내_사용자_제거() throws Exception {
        mockMvc.perform(delete("/api/group/1/user/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(7)
    public void 최종_그룹_조회() throws Exception {
        mockMvc.perform(get("/api/group/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("수정된 Group"))
                .andExpect(jsonPath("$.data.ownerId").value(1))
                .andExpect(jsonPath("$.data.ownerName").value("이병훈"))
                .andExpect(jsonPath("$.data.users").isArray())
                .andExpect(jsonPath("$.data.boards").isArray());
    }
}
