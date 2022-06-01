package joins.pms.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // /api/** API로 요청이 올 경우 모두 허용한다.
                .antMatchers("/api/**").permitAll()
                // H2 콘솔을 열기 위해서 /console/** 요청도 모두 허용한다.
                .antMatchers("/console/**/**").permitAll()
                .anyRequest().denyAll();

        // H2 데이터베이스 콘솔에 접속하기 위해 예외를 처리한다.
        http.csrf().ignoringAntMatchers("/console/**").disable();
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 리소스(static/**)에 대한 필터는 예외로 등록하여 접근이 가능하도록 설정한다.
        web.ignoring().antMatchers("/static/**");
    }
}
