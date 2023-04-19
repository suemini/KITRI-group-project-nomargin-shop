package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;

import EZ.nomargin.domain.item.ItemMapper;
import EZ.nomargin.domain.item.UploadFile;
import EZ.nomargin.dto.ItemDto;
import EZ.nomargin.dto.MemberManagementDto;
import EZ.nomargin.file.FileStore;
import EZ.nomargin.service.AdminService;
import EZ.nomargin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ItemService itemService;
    private final FileStore fileStore;


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
        return "/admin/memberManagement";
    }

    @GetMapping("deleteMember/{id}")
    public String deleteMember(@PathVariable Long id, @ModelAttribute MemberManagementDto MemberManagementDto, Model model) {

        adminService.deleteById(id);
        List<MemberManagementDto> allMemberManagementDto = adminService.findByMmDto();
        model.addAttribute("memberManagementDto", allMemberManagementDto);
        return "/admin/memberManagement";

    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "/admin/addForm";
    }

//    @PostMapping("/admin/add")
//    public String addItem(@ModelAttribute ItemDto itemDto, RedirectAttributes redirectAttributes) throws IOException {
//        UploadFile attachFile = fileStore.storeFile(itemDto.getAttachFile());
//        List<UploadFile> imageFiles = fileStore.storeFiles(itemDto.getImageFiles());
//
//        Item item = ItemMapper.toEntity(itemDto, attachFile, imageFiles);
//        Item savedItem = itemService.save(item);
//
//        redirectAttributes.addAttribute("itemId", savedItem.getItemId());
//        redirectAttributes.addAttribute("storeFileName", savedItem.getAttachFile().getStoreFileName());
//
//        // 각 이미지 파일에 대한 파일 이름을 리스트로 변환하여 전달합니다.
//        List<String> imageFileNames = savedItem.getImageFiles()
//                .stream()
//                .map(file -> file.getStoreFileName())
//                .collect(Collectors.toList());
//        redirectAttributes.addAttribute("imageFiles", imageFileNames);
//
//        redirectAttributes.addAttribute("status", true);
//
//        return "redirect:/admin/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemDto itemDto, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile attachFile = fileStore.storeFile(itemDto.getAttachFile());
        List<UploadFile> imageFiles = fileStore.storeFiles(itemDto.getImageFiles());

        Item item = ItemMapper.toEntity(itemDto, attachFile, imageFiles);
        Item savedItem = itemService.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getItemId());
        redirectAttributes.addAttribute("attachFile", savedItem.getAttachFile().getStoreFileName());

        // 각 이미지 파일에 대한 파일 이름을 리스트로 변환하여 전달합니다.
        List<String> imageFileNames = savedItem.getImageFiles()
                .stream()
                .map(file -> file.getStoreFileName())
                .collect(Collectors.toList());
        redirectAttributes.addAttribute("imageFiles", imageFileNames);

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

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemService.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }





}
