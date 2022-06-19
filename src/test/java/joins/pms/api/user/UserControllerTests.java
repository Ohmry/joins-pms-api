package joins.pms.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import joins.pms.api.http.ApiStatus;
import joins.pms.core.test.ApiInvoker;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTests {
    @Autowired
    ApiInvoker apiInvoker;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;

    private final String API_URL = "/api/user";
    private final String API_SIGNIN = "/api/signin";
    private final String API_SIGNOUT = "/api/signout";

    private static UUID userId = null;

    private JSONObject getDefaultUserMap () throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "홍길동");
        jsonObject.put("email", "o.ohmry@gmail.com");
        jsonObject.put("password", "oohmry");
        jsonObject.put("role", "USER");
        jsonObject.put("status", "ACTIVE");
        return jsonObject;
    }

    @BeforeAll
    public void 사용자_생성 () throws Exception {
        JSONObject userInfo = this.getDefaultUserMap();
        UserInfoDto userInfoDto = userService.findByEmail(userInfo.getString("email"));

        if (userInfoDto == null) {
            apiInvoker.post("/api/signup", this.getDefaultUserMap().toString())
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));
            userInfoDto = userService.findByEmail(userInfo.getString("email"));
            Assertions.assertEquals(userInfoDto.getEmail(), userInfo.getString("email"));
            Assertions.assertEquals(userInfoDto.getName(), userInfo.getString("name"));
            Assertions.assertEquals(userInfoDto.getRole(), userInfo.getString("role"));
            Assertions.assertEquals(userInfoDto.getStatus(), userInfo.getString("status"));
        }
        UserControllerTests.userId = userInfoDto.getId();
    }

    @Test
    @WithMockUser(roles = "USER")
    @Order(1)
    public void 사용자_조회 () throws Exception {
        JSONObject userInfo = this.getDefaultUserMap();
        apiInvoker.get(API_URL + "/" + UserControllerTests.userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data.id").value(UserControllerTests.userId.toString()))
                .andExpect(jsonPath("$.data.name").value(userInfo.get("name")))
                .andExpect(jsonPath("$.data.email").value(userInfo.get("email")))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.userRole").value(userInfo.get("userRole")))
                .andExpect(jsonPath("$.data.userStatus").value(userInfo.get("userStatus")))
                .andDo(print());
    }
    @Test
    @WithMockUser(roles = "USER")
    @Order(2)
    public void 사용자_정보_수정 () throws Exception {
    }

    @Test
    @WithMockUser(roles = "USER")
    @Order(3)
    public void 사용자_탈퇴 () throws Exception {
        JSONObject userInfo = this.getDefaultUserMap();
        userInfo.put("id", UserControllerTests.userId);

        apiInvoker.delete(API_URL, userInfo.toString())
                .andExpect(status().isNoContent());

        apiInvoker.get(API_URL + "/" + UserControllerTests.userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.DATA_IS_EMPTY.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.DATA_IS_EMPTY.getMessage()))
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
