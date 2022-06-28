package joins.pms.api.v1;

import org.json.JSONArray;
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
@Rollback(value = true)
public class BoardControllerTests {
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @Order(1)
    public void BOARD_새로_생성시_제목이_없을경우() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "새롭게 생성한 Board");
    
        mockMvc.perform(post("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
    
    @Test
    @Order(2)
    @Rollback(value = false)
    public void BOARD_새로_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 Board");
        request.put("description", "새롭게 생성한 Board");
        
        mockMvc.perform(post("/api/v1/board")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(header().stringValues("Location", "/api/v1/board/1"))
            .andDo(print());
    }
    
    @Test
    @Order(3)
    @Rollback(value = false)
    public void BOARD_30개_한번에_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "새롭게 생성한 Board");
        
        for (int index = 0; index < 30; index++) {
            request.put("title", "새롭게 생성한 Board (" + (index + 1) + ")");
            mockMvc.perform(post("/api/v1/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().stringValues("Location", "/api/v1/board/" + (index + 2)));
        }
    }
    
    @Test
    @Order(4)
    public void 새로_생성한_BOARD_정보조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.title").value("새로운 Board"))
            .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Board"));
    }
    
    @Test
    @Order(5)
    public void BOARD_리스트로_조회() throws Exception {
        int pageNo = 0;
        int recordCount = 20;
    
        MvcResult result = mockMvc.perform(get("/api/v1/board?pageNo=" + pageNo + "&recordCount=" + recordCount))
            .andExpect(status().isOk())
            .andReturn();
        
        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONArray data = response.getJSONArray("data");
        Assertions.assertEquals(data.length(), 20);
    }
    
    @Test
    @Order(6)
    public void BOARD_수정시_아이디가_없을경우() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "수정된 Board");
        request.put("description", "이 Board는 수정되었습니다.");
    
        mockMvc.perform(put("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(7)
    public void BOARD_수정시_제목이_없을경우() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("description", "이 Board는 수정되었습니다.");
    
        mockMvc.perform(put("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(8)
    public void BOARD_수정시_아이디랑_제목이_없을경우() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "이 Board는 수정되었습니다.");
    
        mockMvc.perform(put("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(9)
    @Rollback(value = false)
    public void 만들어진_BOARD_정보_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("title", "수정된 Board");
        request.put("description", "이 Board는 수정되었습니다.");
        
        mockMvc.perform(put("/api/v1/board")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.title").value("수정된 Board"))
            .andExpect(jsonPath("$.data.description").value("이 Board는 수정되었습니다."));
    }
    
    @Test
    @Order(10)
    @Rollback(value = false)
    public void BOARD_삭제() throws Exception {
        mockMvc.perform(delete("/api/v1/board/1"))
            .andExpect(status().isNoContent())
            .andDo(print());
    }
    
    @Test
    @Order(11)
    public void 삭제_후_BOARD_조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    @Order(12)
    public void 삭제_후_BOARD_리스트_조회() throws Exception {
        int pageNo = 0;
        int recordCount = 20;
    
        MvcResult result = mockMvc.perform(get("/api/v1/board?pageNo=" + pageNo + "&recordCount=" + recordCount))
            .andExpect(status().isOk())
            .andReturn();
    
        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONArray data = response.getJSONArray("data");
        Assertions.assertEquals(data.length(), 20);
        JSONObject board = data.getJSONObject(0);
        Assertions.assertEquals(board.getString("title"), "새롭게 생성한 Board (1)");
    }
    
}
