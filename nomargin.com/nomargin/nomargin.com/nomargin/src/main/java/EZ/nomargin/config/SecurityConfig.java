package EZ.nomargin.config;


import EZ.nomargin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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


        // 권한 설정
        http.authorizeRequests()
                .mvcMatchers("/", "/members/**","/form/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        // 인증 후에 로그인, 로그아웃 시 경로 설정
        http.formLogin()
                .loginPage("/members/loginFrom") // 로그인 기능이 있는 html파일 지정
                .loginProcessingUrl("/members/login") // 로그인 기능이 있는 html파일에서 정한 action ="" 경로
                .defaultSuccessUrl("/members/index") //로그인되면 가는곳
                .failureUrl("/members/login/fail") //로그인 실패시
                .usernameParameter("loginId") //username대신 쓸 값, id/password 을 username/password으로 표시하기 때문에 username으로 안쓸거면 지정해야 함.
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


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
