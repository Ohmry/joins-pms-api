package joins.pms.core.test;

import org.springframework.test.web.servlet.ResultActions;

public interface ApiResultActions extends ResultActions {
    public ApiResultActions isSuccess () throws Exception;
}
