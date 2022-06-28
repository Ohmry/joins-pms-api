package joins.pms.api.v1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;
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
public class ProjectControllerTests {
    @Autowired
    MockMvc mockMvc;
    
    @BeforeAll
    @Rollback(value = false)
    public void PROJECT_생성을_위한_BOARD_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 Board");
        request.put("description", "새롭게 생성한 Board");
    
        mockMvc.perform(post("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(header().stringValues("Location", "/api/v1/board/1"));
    }
    
    @Test
    @Order(1)
    public void PROJECT_새로_생성시_제목_없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "새롭게 생성한 Project");
        
        mockMvc.perform(post("/api/v1/board/1/project")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request.toString()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(2)
    @Rollback(value = false)
    public void PROJECT_새로_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 Project");
        request.put("description", "새롭게 생성한 Project");
    
        mockMvc.perform(post("/api/v1/board/1/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(header().stringValues("Location", "/api/v1/board/1/project/1"));
    }
    
    @Test
    @Order(3)
    @Rollback(value = false)
    public void PROJECT_30개_한번에_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("boardId", 1);
        request.put("description", "새롭게 생성한 Project");
        
        for (int index = 0; index < 30; index++) {
            request.put("title", "새롭세 생성한 Project (" + (index + 1) + ")");
            mockMvc.perform(post("/api/v1/board/1/project")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().stringValues("Location", "/api/v1/board/1/project/" + (index + 2)));
        }
    }
    
    @Test
    @Order(4)
    public void 새로_생성한_PROJECT_정보조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1/project/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.title").value("새로운 Project"))
            .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Project"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andDo(print());
    }
    
    @Test
    @Order(5)
    public void PROJECT_리스트로_조회() throws Exception {
        int pageNo = 0;
        int recordCount = 20;
    
        MvcResult result = mockMvc.perform(get("/api/v1/board/1/project?pageNo=" + pageNo + "&recordCount=" + recordCount))
            .andExpect(status().isOk())
            .andReturn();
    
        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONArray data = response.getJSONArray("data");
        Assertions.assertEquals(data.length(), 20);
    }
    
    @Test
    @Order(6)
    @Rollback(value = false)
    public void PROJECT_정보_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("title", "수정된 Project");
        request.put("description", "정보가 수정된 Project");
        request.put("startDateTime", "202207010000");
        request.put("endDateTime", "202207312359");
        request.put("progress", "COMPLETED");
        
        mockMvc.perform(put("/api/v1/board/1/project")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", "/api/v1/board/1/project/" + request.getLong("id")))
            .andExpect(jsonPath("$.data.title").value("수정된 Project"))
            .andExpect(jsonPath("$.data.description").value("정보가 수정된 Project"));
    }
    
    @Test
    @Order(7)
    public void PROJECT_삭제_시_Board가_없을_경우() throws Exception {
        mockMvc.perform(delete("/api/v1/board/99/project/1"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    @Order(8)
    public void PROJECT_삭제_시_PROJECT가_없을_경우() throws Exception {
        mockMvc.perform(delete("/api/v1/board/1/project/100"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    @Order(9)
    @Rollback(value = false)
    public void PROJECT_삭제() throws Exception {
        mockMvc.perform(delete("/api/v1/board/1/project/1"))
            .andExpect(status().isNoContent());
    }
    
    @Test
    @Order(10)
    public void PROJECT_삭제_후_조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1/project/1"))
            .andExpect(status().isNotFound());
    }
}
