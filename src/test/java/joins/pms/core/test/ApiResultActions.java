package joins.pms.core.test;

import org.springframework.test.web.servlet.ResultActions;

public interface ApiResultActions extends ResultActions {
    ApiResultActions isSuccess () throws Exception;
    ApiResultActions hasCount (int count) throws Exception;
}
