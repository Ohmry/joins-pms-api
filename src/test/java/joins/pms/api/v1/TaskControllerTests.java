package joins.pms.api.v1;

import joins.pms.api.http.ApiStatus;
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
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskControllerTests {
    @Autowired
    MockMvc mockMvc;
    
    @BeforeAll
    @Rollback(value = false)
    public void TASK_생성을_위한_사전작업() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 Board");
        request.put("description", "새롭게 생성한 Board");
    
        mockMvc.perform(post("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(header().stringValues("Location", "/api/v1/board/1"));
    
        request = new JSONObject();
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
    @Order(1)
    public void TASK_새로_생성_시_제목_없음() throws Exception {
       JSONObject request = new JSONObject();
       request.put("boradId", 1);
       request.put("projectId", 1);
       request.put("description", "새롭게 생성한 Task");
       
       mockMvc.perform(post("/api/v1/board/1/project/1/task")
           .contentType(MediaType.APPLICATION_JSON)
           .content(request.toString()))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.code").value(ApiStatus.ILLEGAL_PARAMETERS.getCode()))
           .andExpect(jsonPath("$.message").value(ApiStatus.ILLEGAL_PARAMETERS.getMessage()));
    }
    
    @Test
    @Order(2)
    public void TASK_새로_생성_시_BOARD가_없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("projectId", 1);
        request.put("title", "새로운 TASK");
        request.put("description", "새롭게 생성한 Task");
    
        mockMvc.perform(post("/api/v1/board/999/project/1/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(ApiStatus.DOMAIN_NOT_FOUND.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.DOMAIN_NOT_FOUND.getMessage()));
    }
    
    @Test
    @Order(3)
    public void TASK_새로_생성_시_PROJECT가_없음() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 TASK");
        request.put("description", "새롭게 생성한 Task");
    
        mockMvc.perform(post("/api/v1/board/1/project/998/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(ApiStatus.DOMAIN_NOT_FOUND.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.DOMAIN_NOT_FOUND.getMessage()));
    }
    
    @Test
    @Order(4)
    @Rollback(value = false)
    public void TASK_새로_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("title", "새로운 TASK");
        request.put("description", "새롭게 생성한 Task");
    
        mockMvc.perform(post("/api/v1/board/1/project/1/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", "/api/v1/board/1/project/1/task/1"))
            .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.title").value("새로운 TASK"))
            .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Task"));
    }
    
    @Test
    @Order(5)
    @Rollback(value = false)
    public void TASK_한번에_30개_생성() throws Exception {
        JSONObject request = new JSONObject();
        request.put("description", "새롭게 생성한 Task");
    
        for (int index = 0; index < 30; index++) {
            request.put("title", "새로운 TASK (" + (index + 1) + ")");
            mockMvc.perform(post("/api/v1/board/1/project/1/task")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/board/1/project/1/task/" + (index + 2)))
                .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data.id").value((index + 2)))
                .andExpect(jsonPath("$.data.title").value("새로운 TASK (" + (index + 1) + ")"))
                .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Task"));
        }
    }
    
    @Test
    @Order(6)
    public void TASK_정보_조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1/project/1/task/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.title").value("새로운 TASK"))
            .andExpect(jsonPath("$.data.description").value("새롭게 생성한 Task"));
    }
    
    @Test
    @Order(7)
    public void TASK_리스트로_조회() throws Exception {
        int pageNo = 0;
        int recordCount = 15;
    
        MvcResult result = mockMvc.perform(get("/api/v1/board/1/project/1/task?pageNo=" + pageNo + "&recordCount=" + recordCount))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
            .andReturn();
        
        JSONObject response = new JSONObject(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JSONArray data = response.getJSONArray("data");
        Assertions.assertEquals(recordCount, data.length());
    }
    
    @Test
    @Order(8)
    public void TASK_정보_수정_필수_항목이_없을때() throws Exception {
        List<String> requiredKeys = Arrays.asList("id", "title", "startDateTime", "endDateTime", "progress");
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("title", "수정된 TASK");
        request.put("description", "정보가 수정된 Task");
        request.put("startDateTime", "202207010000");
        request.put("endDateTime", "202207312359");
        request.put("progress", "COMPLETED");
        
        for (String requiredKey : requiredKeys) {
            request.remove(requiredKey);
            
            mockMvc.perform(put("/api/v1/board/1/project/1/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ApiStatus.ILLEGAL_PARAMETERS.getCode()))
                .andExpect(jsonPath("$.message").value(ApiStatus.ILLEGAL_PARAMETERS.getMessage()));
        }
    }
    
    @Test
    @Order(9)
    @Rollback(value = false)
    public void TASK_정보_수정() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1);
        request.put("title", "수정된 TASK");
        request.put("description", "정보가 수정된 Task");
        request.put("startDateTime", "202207010000");
        request.put("endDateTime", "202207312359");
        request.put("progress", "COMPLETED");
    
        mockMvc.perform(put("/api/v1/board/1/project/1/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", "/api/v1/board/1/project/1/task/1"))
            .andExpect(jsonPath("$.code").value(ApiStatus.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.title").value("수정된 TASK"))
            .andExpect(jsonPath("$.data.description").value("정보가 수정된 Task"));
    }
    
    @Test
    @Order(10)
    public void TASK_삭제_시_BOARD가_없을_경우() throws Exception {
        mockMvc.perform(delete("/api/v1/board/199/project/1/task/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(ApiStatus.DOMAIN_NOT_FOUND.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.DOMAIN_NOT_FOUND.getMessage()));
    }
    
    @Test
    @Order(11)
    public void TASK_삭제_시_PROJECT가_없을_경우() throws Exception {
        mockMvc.perform(delete("/api/v1/board/1/project/199/task/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(ApiStatus.DOMAIN_NOT_FOUND.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.DOMAIN_NOT_FOUND.getMessage()));
    }
    
    @Test
    @Order(12)
    public void TASK_삭제_시_TASK가_없을_경우() throws Exception {
        mockMvc.perform(delete("/api/v1/board/1/project/1/task/155"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(ApiStatus.DOMAIN_NOT_FOUND.getCode()))
            .andExpect(jsonPath("$.message").value(ApiStatus.DOMAIN_NOT_FOUND.getMessage()));
    }
    
    @Test
    @Order(13)
    @Rollback(value = false)
    public void TASK_삭제() throws Exception {
        mockMvc.perform(delete("/api/v1/board/1/project/1/task/1"))
            .andExpect(status().isNoContent());
    }
    
    @Test
    @Order(14)
    public void TASK_삭제_후_조회() throws Exception {
        mockMvc.perform(get("/api/v1/board/1/project/1/task/1"))
            .andExpect(status().isNotFound());
    }
}
