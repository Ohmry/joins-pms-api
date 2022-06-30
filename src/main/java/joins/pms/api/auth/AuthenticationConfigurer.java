package joins.pms.api.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AuthenticationConfigurer implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final RequestDenyInterceptor requestDenyInterceptor;
    
    private final List<String> DENY_INTERCEPTOR_WHITELIST = Arrays.asList(
        "/api/**", "/api/signin", "/api/signup", "/console", "/error", "/unauthorized"
    );
    
    public AuthenticationConfigurer(AuthenticationInterceptor authenticationInterceptor,
                                    RequestDenyInterceptor requestDenyInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.requestDenyInterceptor = requestDenyInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
            .excludePathPatterns("/api/signin", "/api/signup")
            .addPathPatterns("/api/**");
        registry.addInterceptor(requestDenyInterceptor)
            .excludePathPatterns(DENY_INTERCEPTOR_WHITELIST);
    }
}
