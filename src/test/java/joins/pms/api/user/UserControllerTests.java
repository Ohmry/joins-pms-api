package joins.pms.api.user;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

    static String accessToken;
    static String refreshToken;
    static String oldAccessToken;

    @Test
    @Rollback(value = false)
    @Order(1)
    public void 사용자_회원가입 () throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "이병훈");
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "oohmry");

        MvcResult result = mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                        .andExpect(status().isCreated())
                        .andExpect(header().exists("Location"))
                        .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        Assertions.assertEquals(request.getString("name"), data.getString("name"));
        Assertions.assertEquals(request.getString("email"), data.getString("email"));
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void 사용자_로그인 () throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "oohmry");

        MvcResult result = mockMvc.perform(post("/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        accessToken = data.getString("accessToken");
        refreshToken = data.getString("refreshToken");
        Assertions.assertNotNull(accessToken);
        Assertions.assertNotNull(refreshToken);
    }

    @Test
    @Order(3)
    public void 사용자_정보조회() throws Exception {
        mockMvc.perform(get("/api/user/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("o.ohmry@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("이병훈"));
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void JWT_토큰_재생성() throws Exception {
        oldAccessToken = accessToken;
        Thread.sleep(1000);
        JSONObject request = new JSONObject();
        request.put("refreshToken", refreshToken);
        MvcResult result = mockMvc.perform(post("/api/resign")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        String newAccessToken = data.getString("accessToken");
        String newRefreshToken = data.getString("refreshToken");

        Assertions.assertNotEquals(accessToken, newAccessToken);
        Assertions.assertNotEquals(refreshToken, newRefreshToken);
        accessToken = newAccessToken;
        refreshToken = newRefreshToken;
    }

    @Test
    @Order(5)
    public void 예전_토큰으로_정보조회() throws Exception {
        mockMvc.perform(get("/api/user/1")
                        .header("Authorization", "Bearer " + oldAccessToken))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void 사용자_정보_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("name", "이병훈_수정");

        mockMvc.perform(put("/api/user/1")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("이병훈_수정"))
                .andDo(print());
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void 사용자_비밀번호_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("password", "oohmry");
        request.put("newPassword", "oohmry2");

        mockMvc.perform(put("/api/user/1/credential")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    public void 사용자_비밀번호_변경_후_기존_토큰으로_API_조회() throws Exception {
        mockMvc.perform(get("/api/user/1")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @Order(9)
    @Rollback(value = false)
    public void 사용자_재로그인 () throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "oohmry2");

        MvcResult result = mockMvc.perform(post("/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        accessToken = data.getString("accessToken");
        refreshToken = data.getString("refreshToken");
        Assertions.assertNotNull(accessToken);
        Assertions.assertNotNull(refreshToken);
    }

    @Test
    @Order(10)
    public void 사용자_정보재조회() throws Exception {
        mockMvc.perform(get("/api/user/1")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("o.ohmry@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("이병훈_수정"));
    }
}
