package joins.pms.core.test;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

public interface ApiResultActions extends ResultActions {
    ApiResultActions isStatus (HttpStatus httpStatus) throws Exception;
}
