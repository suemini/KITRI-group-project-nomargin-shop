package EZ.nomargin.controller;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageController {

    // 의존성 주입을 받으려는 객체(빈)
    @Autowired
    private final MyPageService myPageService;


    //마이페이지 메인
    @GetMapping("/me")
    public String myPage() {
        return "member/myPage";
    }

    //마이페이지: 정보수정
    @GetMapping("/edit-my-info")
    public String editMyInfo(Principal principal, Model model) {
        Member member = myPageService.findByLoginId(principal.getName());
        model.addAttribute("member", member);
        return "member/editMyInfo";
    }

    //정보수정 했으면 서버로 보냄
    @PostMapping("/edit-my-info")
    public String editMyInfo(@ModelAttribute Member member) {
        myPageService.updateAccount(member.getLoginId(), member);
        return "member/myPage";
    }

    @GetMapping("/delete")
    public String deleteMyInfo(Principal principal, Model model) {
        Member member = myPageService.findByLoginId(principal.getName());
        model.addAttribute("member", member);
        return "member/deleteMyInfo";
    }

    @PostMapping("/delete")
    public String deleteMyInfo(String loginId) {
        myPageService.deleteAccount(loginId);
        return "redirect:/members/logout";
    }

}