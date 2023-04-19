package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.dto.JoinDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@TestPropertySource(properties = {"spring.config.location=classpath:application.properties"})
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member joinMember() {
        JoinDto memberDto = JoinDto.builder()
                .loginId("kitri")
                .name("현덕")
                .password("kitri123456!")
                .build();
        return Member.createMember(memberDto, passwordEncoder);
    }

    @Test
    public void saveMember() {
        Member member = joinMember();
        Member savedMember = memberService.joinMember(member);

        assertEquals(member.getLoginId(), savedMember.getLoginId());
    }
}