package joins.pms.core;

import joins.pms.api.sign.SignHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfigure extends WebSecurityConfigurerAdapter {
    private final SignHandler signHandler;
    public SpringSecurityConfigure(SignHandler signHandler) {
        this.signHandler = signHandler;
    }
    @Bean
    public PasswordEncoder getPasswordEncoder () {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/api/signin")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(signHandler)
                .failureHandler(signHandler);

        http.authorizeRequests()
                .antMatchers("/api/signin/**", "/api/signup").permitAll()
                // H2 콘솔을 열기 위해서 /console/** 요청도 모두 허용한다.
                .antMatchers("/console/**/**").permitAll()
                // /api/** API로 요청이 올 경우 모두 허용한다.
                .antMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().denyAll();

        // H2 데이터베이스 콘솔에 접속하기 위해 예외를 처리한다.
        http.csrf().ignoringAntMatchers("/console/**/**").disable();
        http.headers().frameOptions().sameOrigin();

        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) {
        // 정적 리소스(static/**)에 대한 필터는 예외로 등록하여 접근이 가능하도록 설정한다.
        web.ignoring().antMatchers("/static/**");
    }
}
