package joins.pms.api.v1.controller;

import joins.pms.core.api.ApiStatus;
import joins.pms.core.test.ApiInvoker;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests {
    @Autowired
    ApiInvoker apiInvoker;
    private final String API_URL = "/api/user";
    private final String API_SIGNIN = "/api/signin";
    private final String API_SIGNOUT = "/api/signout";

    private static String userId = "";

    private JSONObject getDefaultUserMap () throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "홍길동");
        jsonObject.put("email", "o.ohmry@gmail.com");
        jsonObject.put("password", "oohmry");
        jsonObject.put("userRole", "USER");
        jsonObject.put("userStatus", "ACTIVATED");
        return jsonObject;
    }

    @Test
    @Order(1)
    public void 사용자_생성 () throws Exception {
        JSONObject userInfo = this.getDefaultUserMap();
        MvcResult result = apiInvoker.post(API_URL, this.getDefaultUserMap().toString())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(print())
                .andReturn();
        String uri = result.getResponse().getHeader("Location");
        assert uri != null;
        String userId = uri.substring(6);
        UserControllerTests.userId = userId;
        apiInvoker.get(API_URL + "/" + userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.name").value(userInfo.get("name")))
                .andExpect(jsonPath("$.data.email").value(userInfo.get("email")))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.userRole").value(userInfo.get("userRole")))
                .andExpect(jsonPath("$.data.userStatus").value(userInfo.get("userStatus")))
                .andDo(print());
    }
    @Test
    @Order(2)
    public void 사용자_조회 () throws Exception {
        JSONObject userInfo = this.getDefaultUserMap();
        apiInvoker.get(API_URL + "/" + UserControllerTests.userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data.id").value(UserControllerTests.userId))
                .andExpect(jsonPath("$.data.name").value(userInfo.get("name")))
                .andExpect(jsonPath("$.data.email").value(userInfo.get("email")))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.userRole").value(userInfo.get("userRole")))
                .andExpect(jsonPath("$.data.userStatus").value(userInfo.get("userStatus")))
                .andDo(print());
    }
    @Test
    @Order(3)
    public void 사용자_로그인 () throws Exception {
        Assertions.fail();
    }
    @Test
    @Order(4)
    public void 사용자_로그아웃 () throws Exception {
        Assertions.fail();
    }
    @Test
    @Order(5)
    public void 사용자_탈퇴 () throws Exception {
        Assertions.fail();
    }
}
