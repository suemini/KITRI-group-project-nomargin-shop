package EZ.nomargin.controller;

import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String memberJoin(JoinDto joinDto, Model model) {
        model.addAttribute("joinDto", joinDto);
        return "/admin";
    }

}
