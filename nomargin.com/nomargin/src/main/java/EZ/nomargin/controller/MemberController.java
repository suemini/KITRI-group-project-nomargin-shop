package EZ.nomargin.controller;

import EZ.nomargin.domain.member.Member;

import EZ.nomargin.domain.member.Role;
import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {


    private final PasswordEncoder passwordEncoder;

    private final MemberService memberService;


    // 회원 가입 버튼 눌렀을 때
    @GetMapping("/new")
    public String memberJoin(JoinDto joinDto, Model model) {
        model.addAttribute("joinDto", joinDto);
        return "members/joinForm";
    }


    //회원 가입 입력 창
    @PostMapping("/new")
    public String add(@Valid JoinDto joinDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "members/joinForm";
        }

        try {
            Member member = Member.createMember(joinDto, passwordEncoder);
            memberService.joinMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/joinForm";
        }
        return "redirect:/";
    }


    //로그인 페이지 이동////get
    @GetMapping ("login")
    public String memberLogin() {
        return "/members/loginForm";
    }


    // 로그인 실패
    @GetMapping("/login/fail")
    public String memberLoginFail(Model model) {
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "members/loginForm";
    }

    @PostConstruct
    @Transactional
    public void init() {
        Member member = new Member();
        member.setName("현덕");
        member.setPassword(passwordEncoder.encode("kitri123456!"));
        member.setLoginId("kitri");
        member.setRole(Role.ADMIN);

        memberService.joinMember(member);
    }

    // 로그인이 성공되면 메인으로 이동
    @GetMapping("/index")
    public String Test(Model model) {
        return "index";
    }





}
