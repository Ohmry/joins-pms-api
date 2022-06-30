package joins.pms.api;

import joins.pms.api.http.ApiStatus;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Rollback(value = true)
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

    static String oldAccessToken;
    static String accessToken;
    static String refreshToken;

    @Test
    @Order(1)
    public void 사용자_생성_시_이메일없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("password", "admin");
        request.put("name", "이병훈");

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    public void 사용자_생성_시_비밀번호없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("name", "이병훈");

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void 사용자_생성_시_이름_없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "admin");

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void 사용자_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "admin");
        request.put("name", "이병훈");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/user/1"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("o.ohmry@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("이병훈"))
                .andExpect(jsonPath("$.data.role").value("USER"));
    }

    @Test
    @Order(5)
    public void 사용자_로그인() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "admin");

        MvcResult result = mockMvc.perform(post("/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists())
                .andDo(print())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        accessToken = data.getString("accessToken");
        refreshToken = data.getString("refreshToken");
    }

    @Test
    @Order(6)
    public void 사용자_정보조회() throws Exception {
        mockMvc.perform(get("/api/user/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("o.ohmry@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("이병훈"))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andDo(print());
    }

    @Test
    @Order(7)
    public void ROLE이_USER일때_사용자목록_조회() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(forwardedUrl("/error"));
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    public void 사용자의_ROLE을_ADMIN으로_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("name", "이병훈");
        request.put("role", "ADMIN");

        mockMvc.perform(put("/api/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(ApiStatus.USER_NEED_RESIGNIN.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.USER_NEED_RESIGNIN.getMessage()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("o.ohmry@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("이병훈"))
                .andExpect(jsonPath("$.data.role").value("ADMIN"));
    }

    @Test
    @Order(9)
    @Rollback(value = false)
    public void 사용자_재로그인() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "admin");

        MvcResult result = mockMvc.perform(post("/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists())
                .andDo(print())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        accessToken = data.getString("accessToken");
        refreshToken = data.getString("refreshToken");
    }

    @Test
    @Order(10)
    public void ROLE이_ADMIN일때_사용자목록_조회시_파라미터가_없는경우() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(11)
    public void ROLE이_ADMIN일때_사용자목록_조회() throws Exception {
        mockMvc.perform(get("/api/user?pageNo=0&recordCount=20")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(12)
    public void 인증된_계정이_아닌_계정정보를_변경하려고_할때() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 2);
        request.put("name", "이병훈");
        request.put("role", "ADMIN");

        mockMvc.perform(put("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(13)
    @Rollback(value = false)
    public void 사용자_비밀번호_변경() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("password", "admin");
        request.put("newPassword", "oohmry2");

        mockMvc.perform(put("/api/user/credential")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/user/1"))
                .andExpect(jsonPath("$.code").value(ApiStatus.USER_NEED_RESIGNIN.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.USER_NEED_RESIGNIN.getMessage()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("o.ohmry@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("이병훈"))
                .andExpect(jsonPath("$.data.role").value("ADMIN"));
    }

    @Test
    @Order(14)
    @Rollback(value = false)
    public void 사용자_비밀번호_변경후_로그인() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "o.ohmry@gmail.com");
        request.put("password", "oohmry2");

        MvcResult result = mockMvc.perform(post("/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists())
                .andDo(print())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        oldAccessToken = accessToken;
        accessToken = data.getString("accessToken");
        refreshToken = data.getString("refreshToken");
    }

    @Test
    @Order(15)
    public void 이전에_발급된_토큰으로_접속_시() throws Exception {
        mockMvc.perform(get("/api/user?pageNo=0&recordCount=20")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + oldAccessToken))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
