package joins.pms.core.test;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiResultActionsFactory {
    public static ApiResultActions of (ResultActions resultActions) {
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
