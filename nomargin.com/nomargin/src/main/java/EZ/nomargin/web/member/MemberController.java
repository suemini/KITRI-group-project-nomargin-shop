package EZ.nomargin.web.member;

import EZ.nomargin.domain.member.Member;

import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.repository.MemberRepository;
import EZ.nomargin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {


    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final MemberService memberService;


//    @GetMapping("/add")
//    public String addForm(@ModelAttribute("member") Member member) {
//        return "members/addMemberForm";
//    }


    @GetMapping("/add")
    public String addForm(JoinDto joinDto, Model model) {
        model.addAttribute("joinDto", joinDto);
        return "members/addMemberForm";
    }


//    @PostMapping("/add")
//    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "members/addMemberForm";
//        }
//
//        memberRepository.save(member);
//        return "redirect:/";
//    }

    @PostMapping("/add")
    public String add(@Valid JoinDto joinDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }


        Member member;
        try {
            member = Member.createMember(joinDto, passwordEncoder);
            memberService.joinMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/addMemberForm";
        }


        memberRepository.save(member);
        return "redirect:/";
    }

//    @GetMapping("/members/login")
//    public String memberLoginForm(Model model){
//        return "/loginHome";
//    }
//
//    @GetMapping("/members/login/error")
//    public String LoginError(Model model){
//        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호 확인해주세요");
//        return "/home";
//    }





}
