package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.service.AdminService;
import EZ.nomargin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ItemService itemService;



    @GetMapping("/members")
    public String allMember(Model model) {
        List<Member> allMember = adminService.findAll();
        model.addAttribute("allMember", allMember);
        return "/admin/memberManagement";
    }



    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "/admin/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        item = itemService.save(item);
        redirectAttributes.addAttribute("itemId", item.getItemId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/admin/items/{itemId}";
    }

    @GetMapping("/edit/{itemId}")
    public String editItem(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/admin/editForm";
    }



    @GetMapping("/items/{itemId}")
    public String items(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/admin/item";
    }


    @PostMapping("/edit/{itemId}")
    public String edit(@ModelAttribute Item item, Model model) {
        itemService.update(item.getItemId(), item);
        model.addAttribute("item",item);
        return "/admin/item";
    }

    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return  "redirect:/admin/items";
    }

    @GetMapping("/items")
    public ModelAndView items() {
        List<Item> items = itemService.findAll();
        ModelAndView mav = new ModelAndView("/admin/items")
                .addObject("items", items);
        return mav;
    }





}
