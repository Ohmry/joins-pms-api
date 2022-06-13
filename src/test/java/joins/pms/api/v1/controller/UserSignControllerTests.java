package joins.pms.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joins.pms.core.test.ApiInvoker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserSignControllerTests {
    @Autowired
    ApiInvoker apiInvoker;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    public void 사용자_생성 () throws Exception {

    }

    @Test
    @Order(1)
    public void 사용자_비밀번호_오류 () throws Exception {

    }

    @Test
    @Order(2)
    public void 사용자_로그인 () throws Exception {

    }

    @Test
    @Order(3)
    public void 사용자_로그아웃 () throws Exception {

    }
}
