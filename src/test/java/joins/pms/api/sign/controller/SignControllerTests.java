package joins.pms.api.sign.controller;

import joins.pms.core.test.ApiInvoker;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "o.ohmry@gmail.com");
        jsonObject.put("password", "oohmry");
        jsonObject.put("name", "홍길동");
        jsonObject.put("userRole", "ADMIN");
        jsonObject.put("userStatus", "ACTIVATED");

        apiInvoker.post("/api/signup", jsonObject.toString())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/signin"));
    }

    @Test
    @Order(2)
    public void 사용자_로그인 () throws Exception {
        apiInvoker.post("/api/signin", "email=o.ohmry@gmail.com&password=oohmry")
                .andExpect(status().isOk());
    }

}
