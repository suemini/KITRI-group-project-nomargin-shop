package EZ.nomargin.controller;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.dto.ReviewDto;
import EZ.nomargin.service.MyPageService;
import EZ.nomargin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageController {

    // 의존성 주입을 받으려는 객체(빈)
    @Autowired
    private final MyPageService myPageService;
    private final ReviewService reviewService;


    //마이페이지 메인
    @GetMapping("/me")
    public String myPage(Principal principal, Model model) {
        Member member = myPageService.findByLoginId(principal.getName());
        model.addAttribute("member", member);
        return "member/myPage";
    }
    @PostMapping("/me")
    public String editMyInfoInMyPage(@ModelAttribute Member member) {
        myPageService.updateAccount(member.getLoginId(), member);
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
    @GetMapping("/reviews")
    public String findUserReviews(Model model) {
        // 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        // 리뷰 서비스를 통해 해당 사용자의 리뷰 리스트 조회
        List<ReviewDto> reviewDtoList = reviewService.findByWriter(userId);
        model.addAttribute("reviewList", reviewDtoList);
        return "/members/reviews";
    }
}