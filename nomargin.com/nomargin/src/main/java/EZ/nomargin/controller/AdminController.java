package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;

import EZ.nomargin.domain.item.ItemMapper;
import EZ.nomargin.domain.item.ItemType;
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
        model.addAttribute("item", new Item());
        model.addAttribute("ItemType", ItemType.values());
        return "/admin/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        item = itemService.save(item);
        redirectAttributes.addAttribute("itemId", item.getItemId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/admin/items/{itemId}";
    }

//    @PostMapping("/add")
//    public String addItem(@ModelAttribute ItemDto itemDto, RedirectAttributes redirectAttributes) throws IOException {
//        UploadFile attachFile = fileStore.storeFile(itemDto.getAttachFile());
//        List<UploadFile> imageFiles = fileStore.storeFiles(itemDto.getImageFiles());
//
//        Item item = ItemMapper.toEntity(itemDto, attachFile, imageFiles);
//        Item savedItem = itemService.save(item);
//
//        redirectAttributes.addAttribute("itemId", savedItem.getItemId());
//        redirectAttributes.addAttribute("attachFile", savedItem.getAttachFile()
//                .getStoreFileName());
//
//        // 각 이미지 파일에 대한 파일 이름을 리스트로 변환하여 전달합니다.
//        List<String> imageFileNames = savedItem.getImageFiles()
//                .stream()
//                .map(file -> file.getStoreFileName())
//                .collect(Collectors.toList());
//        redirectAttributes.addAttribute("imageFiles", imageFileNames);
//        redirectAttributes.addAttribute("status", true);
//
//        return "redirect:/admin/items/{itemId}";
//    }





    @GetMapping("/edit/{itemId}")
    public String editItem(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/admin/editForm";
    }

    @PostMapping("/edit/{itemId}")
    public String edit(@ModelAttribute Item item, Model model) {
        itemService.update(item.getItemId(), item);
        model.addAttribute("item",item);
        return "redirect:/admin/items/{itemId}";
    }


//    @PostMapping("/edit/{itemId}")
//    public String edit(@PathVariable Long itemId,
//                       @RequestPart(value = "attachFile", required = false) MultipartFile attachFile,
//                       @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
//                       RedirectAttributes redirectAttributes) throws IOException {
//        // 1. DB에서 수정하려는 Item 객체를 가져옵니다.
//        Item item = itemService.findById(itemId);
//
//        // 2. 새로운 첨부 파일이 있는 경우, 저장하고 Item 객체에 저장합니다.
//        if (attachFile != null && !attachFile.isEmpty()) {
//            UploadFile storeAttachFile = fileStore.storeFile(attachFile);
//            item.setAttachFile(storeAttachFile);
//        }
//
//        // 3. 새로운 이미지 파일이 있는 경우, 저장하고 Item 객체에 저장합니다.
//        if (imageFiles != null && !imageFiles.isEmpty()) {
//            List<UploadFile> storeImageFiles = fileStore.storeFiles(imageFiles);
//            item.getImageFiles().addAll(storeImageFiles);
//        }
//
//        // 4. DB에서 삭제할 이미지 파일들을 가져옵니다.
//        List<UploadFile> deleteImageFiles = item.getImageFiles()
//                .stream()
//                .filter(file -> file.getUploadFileId() != null && !storeImageFiles.contains(file))
//                .collect(Collectors.toList());
//
//        // 5. 삭제할 이미지 파일들을 DB와 Item 객체에서 모두 삭제합니다.
//        item.getImageFiles().removeAll(deleteImageFiles);
//        deleteImageFiles.forEach(fileStore::deleteFile);
//
//        // 6. 수정된 ItemDto 정보를 사용하여 Item 객체를 업데이트합니다.
//        itemService.update(itemId, item);
//
//        // 7. 업데이트된 Item 객체를 DB에 저장합니다.
//        Item updatedItem = itemService.save(item);
//
//        redirectAttributes.addAttribute("itemId", updatedItem.getItemId());
//        redirectAttributes.addAttribute("attachFile", updatedItem.getAttachFile().getStoreFileName());
//
//        List<String> imageFileNames = updatedItem.getImageFiles()
//                .stream()
//                .map(file -> file.getStoreFileName())
//                .collect(Collectors.toList());
//
//        redirectAttributes.addAttribute("imageFiles", imageFileNames);
//        redirectAttributes.addAttribute("status", true);
//
//        return "redirect:/admin/items/{itemId}";
//    }


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

