package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;

import EZ.nomargin.domain.item.ItemType;
import EZ.nomargin.dto.ItemDto;
import EZ.nomargin.dto.MemberManagementDto;
import EZ.nomargin.service.AdminService;
import EZ.nomargin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ItemService itemService;

    @GetMapping("/members")
    public String allMember(Model model) {
        List<MemberManagementDto> memberManagementDto = adminService.findByMmDto();
        model.addAttribute("memberManagementDto", memberManagementDto);

        return "/admin/memberManagement";
    }

    @PostMapping("/editMember/{id}")
    public String editMember(@PathVariable Long id, @ModelAttribute MemberManagementDto memberManagementDto, Model model) {
        adminService.editByMmDto(id, memberManagementDto);
        List<MemberManagementDto> allMemberManagementDto = adminService.findByMmDto();
        model.addAttribute("memberManagementDto", allMemberManagementDto);
        return "redirect:/admin/members";
                //"/admin/memberManagement";
    }

    @GetMapping("deleteMember/{id}")
    public String deleteMember(@PathVariable Long id, @ModelAttribute MemberManagementDto MemberManagementDto, Model model) {
        adminService.deleteById(id);
        List<MemberManagementDto> allMemberManagementDto = adminService.findByMmDto();
        model.addAttribute("memberManagementDto", allMemberManagementDto);
        return "redirect:/admin/members";

    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("itemDto", new ItemDto());
        model.addAttribute("ItemType", ItemType.values());
        return "/admin/addForm";
    }

    // 상품 추가 (사진 업로드 포함)
    @RequestMapping(path = "/add", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String reviewPostPro(@ModelAttribute ItemDto itemDto, RedirectAttributes redirectAttributes) throws Exception{
        Item item = itemService.post(itemDto);
        redirectAttributes.addAttribute("itemId",item.getItemId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/admin/items/{itemId}";
    }

    @GetMapping("/edit/{itemId}")
    public String editItem(@PathVariable Long itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId));
        return "/admin/editForm";
    }

    // 상품 수정 (사진 이미지 포함)
    @RequestMapping(path = "/edit/{itemId}", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String update(@PathVariable("itemId") Long itemId,
                         @ModelAttribute ItemDto itemDto,
                         Model model) throws Exception {

        itemService.update(itemId, itemDto);

        return "redirect:/admin/items/{itemId}";
    }

    @GetMapping("/items/{itemId}")
    public String items(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/admin/item";
    }


    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return  "redirect:/admin/items";
    }

    // 상품 목록
    @GetMapping("/items")
    public ModelAndView items() {
        List<Item> items = itemService.findAll();
        ModelAndView mav = new ModelAndView("/admin/items")
                .addObject("items", items);
        return mav;
    }

    @ModelAttribute("ItemType")
    public ItemType[] ItemTypes() {
        return ItemType.values();
    }


}

