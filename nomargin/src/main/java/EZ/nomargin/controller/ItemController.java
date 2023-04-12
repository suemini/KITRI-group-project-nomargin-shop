package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.item.ItemColor;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.text.html.parser.Entity;
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
    private final EntityManagerFactory emf;

    @ModelAttribute("ItemType")
    public ItemType[] ItemTypes() {
        return ItemType.values();
    }


    @GetMapping("/type/top")
    public String itemsTop(Model model) {
        List<Item> items = itemService.findTop();
        model.addAttribute("items", items);
        return "/form/itemList";
    }

    @GetMapping("/type/bottom")
    public String itemsBottom(Model model) {
        List<Item> items = itemService.findBottom();
        model.addAttribute("items", items);
        return "/form/itemList";
    }
    @GetMapping("/type/outer")
    public String itemsOuter(Model model) {
        List<Item> items = itemService.findOuter();
        model.addAttribute("items", items);
        return "/form/itemList";
    }

    @GetMapping("/type/{itemType}")
    public String typeTitle(@PathVariable("itemType") String type, Model model) {
        List<Item> items = new ArrayList<>();

        switch (type) {
            case "Top":
                items = itemService.findTop();
                model.addAttribute("items", items);
                model.addAttribute("type", "Top");
                break;
            case "Bottom":
                items = itemService.findBottom();
                model.addAttribute("items", items);
                model.addAttribute("type", "Bottom");
                break;
            case "Outer":
                items = itemService.findOuter();
                model.addAttribute("items", items);
                model.addAttribute("type", "Outer");
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

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/form/item";
    }

//    @GetMapping("/{itemId}")
//    public String item(Model model, @PathVariable Long itemId) {
//        Item item = itemService.findById(itemId);
//        model.addAttribute("item", item);
//
//        List<ItemColor> colors = new ArrayList<>();
//        colors.add(new ItemColor("Red"));
//        colors.add(new ItemColor("Green"));
//        colors.add(new ItemColor("Blue"));
//        model.addAttribute("colors", colors);
//
//        return "/form/item";
//    }
//
//    @ModelAttribute("colors")
//    public List<ItemColor> colors() {
//        List<ItemColor> colors = new ArrayList<>();
//        colors.add(new ItemColor("Red"));
//        colors.add(new ItemColor("Green"));
//        colors.add(new ItemColor("Blue"));
//        return colors;
//    }


    @PostConstruct  // 생성 이후 얘를 실행
    public void initProducts() {
        Item item1 = new Item("탑 티셔츠", "Shop1102717919 Store", 19000, 100, ItemType.Top,"1.main");
        Item item2 = new Item("프린트 후드 티", "Shop1102733813 Store", 12000, 100, ItemType.Top,"2.main");
        Item item3 = new Item("패치워크 스웨터", "LBL LEADING THE BETTER Store", 11700, 100, ItemType.Top,"3.main");
        Item item4 = new Item("코튼 긴팔 티셔츠", "MrValloon Store", 22600, 100, ItemType.Top,"4.main");
        Item item5 = new Item("하라주쿠 레터 후드 스웨터", "DUOFIER Dropshipping Store", 13000, 100, ItemType.Top,"5.main");
        Item item6 = new Item("스포츠 티셔츠", "shop5831243", 21000, 100, ItemType.Top,"6.main");
        Item item7 = new Item("오버사이즈 코튼 루즈핏 티셔츠", "FOGESSENTIALSLOT", 27000, 100, ItemType.Top,"7.main");
        Item item8 = new Item("클래식 긴 소매 티셔츠", "Fashion", 29000, 100, ItemType.Top,"8.main");
        Item item9 = new Item("캐주얼 소프트 통기성 스웨트 팬츠", "TOLOER", 28000, 100, ItemType.Bottom,"9.main");
        Item item10 = new Item("오버사이즈 루즈핏 캐주얼 바지", "I need a billion", 36700, 100, ItemType.Bottom,"10.main");
        Item item11 = new Item("코튼 캐주얼 바지", "neverfunction", 38300, 100, ItemType.Bottom,"11.main");
        Item item12 = new Item("비즈니스 캐주얼 바지", "Isfriday Aelisa", 17000, 100, ItemType.Bottom,"12.main");
        Item item13 = new Item("비즈니스 스트레이트 바지", "Brother Wang", 44200, 100, ItemType.Bottom,"13.main");
        Item item14 = new Item("캐주얼 스웨트 팬츠", "TOLOER", 70000, 100, ItemType.Bottom,"14.main");
        Item item15 = new Item("영국 비즈니스 패션 바지", "JEYWOOD", 42000, 100, ItemType.Bottom,"15.main");
        Item item16 = new Item("3D 패턴 슬림 스키니 프린트 청바지", "Vogue 333 Store", 720000, 100, ItemType.Bottom,"16.main");
        Item item17 = new Item("루즈핏 코튼 데님 재킷", "Vogue 333 Store", 58400, 100, ItemType.Outer,"17.main");
        Item item18 = new Item("하이 스트리트 데님 자켓", "Vogue 333 Store", 183000, 100, ItemType.Outer,"18.main");
        Item item19 = new Item("곰 자수 가디건 스웨터", "Phoenixce Store", 16700, 100, ItemType.Outer,"19.main");
        Item item20 = new Item("양모 캐시미어 스웨터", "Phoenixce Store", 69000, 100, ItemType.Outer,"20.main");
        Item item21 = new Item("야구 칼라 재킷", "Phoenixce Store", 18400, 100, ItemType.Outer,"21.main");
        Item item22 = new Item("골프 지퍼 재킷", "Vogue 333 Store", 23000, 100, ItemType.Outer,"22.main");
        Item item23 = new Item("대학 캠퍼스 무지 캐주얼 재킷", "HKAZ Official Store", 18000, 100, ItemType.Outer,"23.main");
        Item item24 = new Item("코듀로이 단색 다목적 코트", "FURCW Store", 11700, 100, ItemType.Outer,"24.main");
        Item item25 = new Item("후드 풀오버 재킷", "My Lord Store", 15000, 100, ItemType.Outer,"25.main");

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);
            em.persist(item5);
            em.persist(item6);
            em.persist(item7);
            em.persist(item8);
            em.persist(item9);
            em.persist(item10);
            em.persist(item11);
            em.persist(item12);
            em.persist(item13);
            em.persist(item14);
            em.persist(item15);
            em.persist(item16);
            em.persist(item17);
            em.persist(item18);
            em.persist(item19);
            em.persist(item20);
            em.persist(item21);
            em.persist(item22);
            em.persist(item23);
            em.persist(item24);
            em.persist(item25);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
