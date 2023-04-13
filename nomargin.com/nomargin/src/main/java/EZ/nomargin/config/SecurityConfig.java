package EZ.nomargin.config;


import EZ.nomargin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.InitBinder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .cors().disable()   //cors방지
                .csrf().disable();   //csrf방지


        // 권한 설정
        http.authorizeRequests()

                .mvcMatchers("/", "/members/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

//        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        // 인증 후에 로그인, 로그아웃 시 경로 설정
        http.formLogin()
                .loginPage("/members/loginForm")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login/fail")
                .loginProcessingUrl("/members/login")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

    }

    //암호처리
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //사용자 정보를 가져오고 인증, 인가 작업을 진행 (DB에 저장 된거랑, 사용자가 입력한 거랑 비교)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(getPasswordEncoder());
    }


    // 모든 권한(USER, ADMIN)에게 스태틱 리소스가 적용될 수 있도록 함
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/CSS/**", "/JS/**", "/clothesImg/**", "/asset/**");
    }


}
