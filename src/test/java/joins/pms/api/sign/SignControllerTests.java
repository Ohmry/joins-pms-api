package joins.pms.api.sign;

import joins.pms.core.test.ApiInvoker;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignControllerTests {

    @Autowired
    ApiInvoker apiInvoker;

    @Test
    @Order(1)
    public void 사용자_가입 () throws Exception {
        JSONObject request = this.getUserInfo();
        apiInvoker.post("/api/signup", request.toString())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/signin"))
                .andDo(print());
    }

    @Test
    @Order(2)
    public void 사용자_로그인 () throws Exception {
        JSONObject request = this.getUserInfo();
        String email = request.getString("email");
        String password = request.getString("password");
        apiInvoker.post("/api/signin", "email=" + email + "&password=" + password, MediaType.APPLICATION_FORM_URLENCODED)
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    private JSONObject getUserInfo () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "o.ohmry@gmail.com");
        jsonObject.put("password", "oohmry");
        jsonObject.put("name", "홍길동");
        jsonObject.put("userRole", "USER");
        jsonObject.put("userStatus", "ACTIVATED");
        return jsonObject;
    }
}
