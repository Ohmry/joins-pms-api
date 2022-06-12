package joins.pms.core.test;

import com.sun.istack.NotNull;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class ApiInvoker {
    private final MockMvc mockMvc;
    public ApiInvoker (MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions get (@NotNull String url) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions post (@NotNull String url, String body) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.post(url)
                .content(body).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions put (@NotNull String url, String body) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.put(url)
                .content(body).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions delete (@NotNull String url, String body) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON).content(body));
    }

    public ResultActions delete (@NotNull String url) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON));
    }
}
