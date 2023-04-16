package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.item.ItemType;
import EZ.nomargin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/itemList")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/form/item";
    }


    @ModelAttribute("ItemType")
    public ItemType[] ItemTypes() {
        return ItemType.values();
    }


    @GetMapping("/type/{type}")
    public String itemsTop(@PathVariable("type") String type, Model model) {
        List<Item> items = new ArrayList<>();

        switch (type) {
            case "top":
                items = itemService.findTop();
                model.addAttribute("items", items);
                model.addAttribute("type", "TOP");
                break;
            case "bottom":
                items = itemService.findBottom();
                model.addAttribute("items", items);
                model.addAttribute("type", "BOTTOM");
                break;
            case "outer":
                items = itemService.findOuter();
                model.addAttribute("items", items);
                model.addAttribute("type", "OUTER");
                break;
        }

        return "/form/itemList";
    }


    @ModelAttribute("itemSize")
    public Map<Integer, String> size() {
        Map<Integer, String> sizes = new LinkedHashMap<>();
        sizes.put(90, "S");
        sizes.put(95, "M");
        sizes.put(100, "L");
        return sizes;
    }


//
//    @GetMapping("top")
//    public String itemsTop(Model model) {
//        List<Item> items = itemService.findTop();
//        model.addAttribute("items", items);
//        return "/form/itemList";
//    }
//
//    @GetMapping("bottom")
//    public String itemsBottom(Model model) {
//        List<Item> items = itemService.findBottom();
//        model.addAttribute("items", items);
//        return "/form/itemList";
//    }
//    @GetMapping("outer")
//    public String itemsOuter(Model model) {
//        List<Item> items = itemService.findOuter();
//        model.addAttribute("items", items);
//        return "/form/itemList";
//    }


    @PostConstruct  // 생성 이후 얘를 실행
    public void initProducts() {
        itemService.save(new Item("탑 티셔츠", "Shop1102717919 Store", 19000, 100, ItemType.Top,"1.main"));
        itemService.save(new Item("프린트 후드 티", "Shop1102733813 Store", 12000, 100, ItemType.Top,"2.main"));
        itemService.save(new Item("패치워크 스웨터", "LBL LEADING THE BETTER Store", 11700, 100, ItemType.Top,"3.main"));
        itemService.save(new Item("코튼 긴팔 티셔츠", "MrValloon Store", 22600, 100, ItemType.Top,"4.main"));
        itemService.save(new Item("하라주쿠 레터 후드 스웨터", "DUOFIER Dropshipping Store", 13000, 100, ItemType.Top,"5.main"));
        itemService.save(new Item("스포츠 티셔츠", "shop5831243", 21000, 100, ItemType.Top,"6.main"));
        itemService.save(new Item("오버사이즈 코튼 루즈핏 티셔츠", "FOGESSENTIALSLOT", 27000, 100, ItemType.Top,"7.main"));
        itemService.save(new Item("클래식 긴 소매 티셔츠", "Fashion", 29000, 100, ItemType.Top,"8.main"));
        itemService.save(new Item("캐주얼 소프트 통기성 스웨트 팬츠", "TOLOER", 28000, 100, ItemType.Bottom,"9.main"));
        itemService.save(new Item("오버사이즈 루즈핏 캐주얼 바지", "I need a billion", 36700, 100, ItemType.Bottom,"10.main"));
        itemService.save(new Item("코튼 캐주얼 바지", "neverfunction", 38300, 100, ItemType.Bottom,"11.main"));
        itemService.save(new Item("비즈니스 캐주얼 바지", "Isfriday Aelisa", 17000, 100, ItemType.Bottom,"12.main"));
        itemService.save(new Item("비즈니스 스트레이트 바지", "Brother Wang", 44200, 100, ItemType.Bottom,"13.main"));
        itemService.save(new Item("캐주얼 스웨트 팬츠", "TOLOER", 70000, 100, ItemType.Bottom,"14.main"));
        itemService.save(new Item("영국 비즈니스 패션 바지", "JEYWOOD", 42000, 100, ItemType.Bottom,"15.main"));
        itemService.save(new Item("3D 패턴 슬림 스키니 프린트 청바지", "Vogue 333 Store", 720000, 100, ItemType.Bottom,"16.main"));
        itemService.save(new Item("루즈핏 코튼 데님 재킷", "Vogue 333 Store", 58400, 100, ItemType.Outer,"17.main"));
        itemService.save(new Item("하이 스트리트 데님 자켓", "Vogue 333 Store", 183000, 100, ItemType.Outer,"18.main"));
        itemService.save(new Item("곰 자수 가디건 스웨터", "Phoenixce Store", 16700, 100, ItemType.Outer,"19.main"));
        itemService.save(new Item("양모 캐시미어 스웨터", "Phoenixce Store", 69000, 100, ItemType.Outer,"20.main"));
        itemService.save(new Item("야구 칼라 재킷", "Phoenixce Store", 18400, 100, ItemType.Outer,"21.main"));
        itemService.save(new Item("골프 지퍼 재킷", "Vogue 333 Store", 23000, 100, ItemType.Outer,"22.main"));
        itemService.save(new Item("대학 캠퍼스 무지 캐주얼 재킷", "HKAZ Official Store", 18000, 100, ItemType.Outer,"23.main"));
        itemService.save(new Item("코듀로이 단색 다목적 코트", "FURCW Store", 11700, 100, ItemType.Outer,"24.main"));
        itemService.save(new Item("후드 풀오버 재킷", "My Lord Store", 15000, 100, ItemType.Outer,"25.main"));

    }


}
