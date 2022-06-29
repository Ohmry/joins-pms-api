package joins.pms.api.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationConfigure implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final RequestDenyInterceptor requestDenyInterceptor;

    public AuthenticationConfigure(AuthenticationInterceptor authenticationInterceptor,
                                   RequestDenyInterceptor requestDenyInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.requestDenyInterceptor = requestDenyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**");
        registry.addInterceptor(requestDenyInterceptor)
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/console")
                .excludePathPatterns("/error")
                .excludePathPatterns("/unauthorized");
    }
}
