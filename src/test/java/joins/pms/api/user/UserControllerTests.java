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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

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
        mockMvc.perform(post("/api/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("email=o.ohmry@gmail.com&password=oohmry"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void 사용자_정보수정 () throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("name", "이병훈_수정");
        request.put("email", "o.ohmry@kakao.com");

        MvcResult result = mockMvc.perform(post("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONObject data = response.getJSONObject("data");
        Assertions.assertEquals(request.getString("name"), data.getString("name"));
        Assertions.assertEquals(request.getString("email"), data.getString("email"));
    }
}
