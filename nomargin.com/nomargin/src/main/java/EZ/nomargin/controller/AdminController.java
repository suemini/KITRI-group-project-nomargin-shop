package EZ.nomargin.controller;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.service.AdminService;
import EZ.nomargin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/members")
    public String allMember(Model model) {
        List<Member> allMember = adminService.findAll();
        model.addAttribute("allMember", allMember);
        return "/admin";
    }

}
