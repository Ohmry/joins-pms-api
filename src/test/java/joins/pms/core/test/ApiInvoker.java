package joins.pms.core.test;

import com.sun.istack.NotNull;
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

    public ApiResultActions get (@NotNull String url) throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON));
        return new ApiResultActions() {
            @Override
            public ApiResultActions isSuccess() throws Exception {
                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.status").value(200))
                        .andExpect(jsonPath("$.message").value("OK"));
                return this;
            }

            @Override
            public ResultActions andExpect(ResultMatcher matcher) throws Exception {
                resultActions.andExpect(matcher);
                return this;
            }

            @Override
            public ResultActions andDo(ResultHandler handler) throws Exception {
                resultActions.andDo(handler);
                return this;
            }

            @Override
            public MvcResult andReturn() {
                return resultActions.andReturn();
            }
        };
    }

}
