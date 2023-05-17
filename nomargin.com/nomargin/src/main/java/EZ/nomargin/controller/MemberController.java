package EZ.nomargin.controller;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.domain.member.Role;
import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.repository.MemberRepository;
import EZ.nomargin.service.ItemService;
import EZ.nomargin.service.MemberService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;



    //0517 안되면 이거 쓰기
    // 회원 가입 버튼 눌렀을 때
//    @GetMapping("/new")
//    public String memberJoin(JoinDto joinDto, Model model) {
//        model.addAttribute("joinDto", joinDto);
//        List<Member> memberList = memberService.findAll();
//        model.addAttribute("memberList",memberList);
//        return "members/joinForm";
//    }



    //0517
    @GetMapping("/new")
    public String memberJoin(JoinDto joinDto, Model model) throws JsonProcessingException {
        model.addAttribute("joinDto", joinDto);
        List<Member> memberList = memberService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String memberListJson = objectMapper.writeValueAsString(memberList);
        model.addAttribute("memberListJson", memberListJson);
        return "members/joinForm";
    }





    //회원 가입 입력 창
    @PostMapping("/new")
    public String add(@Valid JoinDto joinDto, BindingResult bindingResult, Model model) {
        memberService.validate(joinDto, bindingResult); //아이디 중복 유효성 검사

        if (bindingResult.hasErrors()) {
            return "/members/joinForm";
        }
        try {
            Member member = Member.createMember(joinDto, passwordEncoder);
            memberService.joinMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/members/joinForm";
        }
        return "members/loginForm";
    }


    //로그인 창으로 이동////get
    @GetMapping ("/login")
    public String memberLogin() {
        return "/members/loginForm";
    }


    // 로그인 실패
    @GetMapping("/login/fail")
    public String memberLoginFail(Model model) {
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "/members/loginForm";
    }

    //로그아웃 버튼
    @GetMapping("/logout")
    public String memberLogout(){
        return "redirect:/";
    }




//    @PostConstruct
//    @Transactional
//    public void init() {
//        Member member = new Member();
//        member.setName("kitri");
//        member.setPassword(passwordEncoder.encode("kitri"));
//        member.setLoginId("kitri");
//        member.setRole(Role.ADMIN);
//        member.setPhoneNumber("010-0000-0000");
//        member.setFullAddr("서울특별시 용산구 유엔빌리지길 7");
//
//        memberService.joinMember(member);
//
//
//
//        Member member1 = new Member();
//        member1.setName("USER");
//        member1.setPassword(passwordEncoder.encode("user"));
//        member1.setLoginId("user");
//        member1.setRole(Role.USER);
//        member1.setPhoneNumber("010-0000-0000");
//        member1.setFullAddr("서울특별시 용산구 유엔빌리지길 7");
//
//        memberService.joinMember(member1);
////
//
//        Member member2 = new Member();
//        member2.setName("test");
//        member2.setPassword(passwordEncoder.encode("use1"));
//        member2.setLoginId("user1");
//        member2.setRole(Role.USER);
//        member2.setPhoneNumber("010-0000-0000");
//        member2.setFullAddr("서울특별시 동대문구 천호대로 129");
//
//        memberService.joinMember(member2);
//
//    }


    // 로그인이 성공되면 메인으로 이동
    @GetMapping("/index")
    public String login() {
        return "redirect:/";
    }

    @GetMapping("/denied")
    public String showAccessDeniedPage() {
        return "/members/notAdmin";
    }


    @RequestMapping(value = "/checkDuplicateId", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateId(@RequestBody Map<String, String> request) {
        String loginId = request.get("loginId");
        boolean isDuplicate = memberRepository.findByLoginId(loginId).isPresent();

        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicate", isDuplicate);
        return response;
    }



}
