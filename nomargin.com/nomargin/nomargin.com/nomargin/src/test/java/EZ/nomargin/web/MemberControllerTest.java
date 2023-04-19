//package EZ.nomargin.web;
//
//import EZ.nomargin.domain.member.Member;
//import EZ.nomargin.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.config.authentication.PasswordEncoderParser;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.transaction.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//class MemberControllerTest {
//
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    MockMvc MockMvc ;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//
//    public Member createMember(String loginId, String password){
//        MemberFormDto memberFormDto = new MemberFormDto();
//        memberFormDto.setEmail(email);
//        memberFormDto.setName("홍길동");
//        memberFormDto.setAddress("서울시 마포구 합정동");
//        memberFormDto.setPassword(password);
//        Member member = Member.createMember(memberFormDto, passwordEncoder);
//        return memberService.saveMember(member);
//    }
//
//
//}