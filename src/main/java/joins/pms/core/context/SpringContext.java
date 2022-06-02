package joins.pms.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 스프링 컨테이너에 있는 Bean 객체를 가져오기 위한 클래스
 * - Component가 아닌 클래스에서도 이 클래스를 이용하여 컨테이너에 있는 Bean을 가져올 수 있다.
 */
@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
