package joins.pms.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.model.UserRole;
import joins.pms.api.user.model.UserStatus;
import joins.pms.api.user.service.UserService;
import joins.pms.core.api.ApiStatus;
import joins.pms.core.test.ApiInvoker;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    public void 사용자_정보_수정 () throws Exception {
        MvcResult result = apiInvoker.get(API_URL + "/" + UserControllerTests.userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data.id").value(UserControllerTests.userId))
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = jsonObject.getJSONObject("data");

        String modifiedName = data.getString("name") + "_수정";
        String modifiedEmail = "modified@gmail.com";
        String modifiedPassword = passwordEncoder.encode("modifiedoohmry");
        UserRole modifiedUserRole = UserRole.ADMIN;
        UserStatus modifiedUserStatus = UserStatus.LOCKED;

        UserDto userDto = new UserDto();
        userDto.setId(UUID.fromString(data.getString("id")));
        userDto.setName(modifiedName);
        userDto.setEmail(modifiedEmail);
        userDto.setPassword(modifiedPassword);
        userDto.setUserRole(modifiedUserRole);
        userDto.setUserStatus(modifiedUserStatus);

        apiInvoker.put(API_URL, objectMapper.writeValueAsString(userDto))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(print());

        userDto = userService.findById(UUID.fromString(UserControllerTests.userId));
        assert userDto.getId().equals(UUID.fromString(UserControllerTests.userId));
        assert userDto.getName().equals(modifiedName);
        assert userDto.getEmail().equals(modifiedEmail);
        assert passwordEncoder.matches(modifiedPassword, userDto.getPassword());
        assert userDto.getUserRole().equals(modifiedUserRole);
        assert userDto.getUserStatus().equals(modifiedUserStatus);
    }

    @Test
    @Order(4)
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
