package EZ.nomargin.web;

import EZ.nomargin.domain.Item;
import EZ.nomargin.domain.ItemColor.ItemColor;
import EZ.nomargin.domain.ItemType;
import EZ.nomargin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/form/itemList")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemService itemService;


    @ModelAttribute("ItemType")
    public ItemType[] ItemTypes() {
        return ItemType.values();
    }


    @GetMapping("/itemType/top")
    public String itemsTop(Model model) {
        List<Item> items = itemService.findTop();
        model.addAttribute("items", items);
        return "/form/itemList";
    }

    @GetMapping("/itemType/bottom")
    public String itemsBottom(Model model) {
        List<Item> items = itemService.findBottom();
        model.addAttribute("items", items);
        return "/form/itemList";
    }

    @GetMapping("/itemType/outer")
    public String itemsOuter(Model model) {
        List<Item> items = itemService.findOuter();
        model.addAttribute("items", items);
        return "/form/itemList";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable Long itemId) {
        Item items = itemService.findById(itemId);
        model.addAttribute("items", items);
        return "/form/item";
    }


    @ModelAttribute("colors")
    public List<ItemColor> colors() {
        List<ItemColor> colors = new ArrayList<>();
        colors.add(new ItemColor("Red", "적색"));
        colors.add(new ItemColor("White", "흰색"));
        colors.add(new ItemColor("Black", "검은색"));
        return colors;
    }


    // 공부하자...PathVariable
    @GetMapping("/itemType/{itemType}")
    public String itemTypeName(@PathVariable("itemType") String itemType, Model model) {
        List<Item> items = new ArrayList<>();

        switch (itemType) {
            case "TOP":
                items = itemService.findTop();
                model.addAttribute("items", items);
                model.addAttribute("itemType", "TOP");
                break;
            case "BOTTOM":
                items = itemService.findBottom();
                model.addAttribute("items", items);
                model.addAttribute("itemType", "BOTTOM");
                break;
            case "OUTER":
                items = itemService.findOuter();
                model.addAttribute("items", items);
                model.addAttribute("itemType", "OUTER");
                break;
        }
        return "/form/itemList";
    }

    @PostConstruct  // 생성 이후 얘를 실행
    public void initProducts() {
        itemService.save(new Item("티샤스", "이것은 흰티",10000,100, ItemType.Top ,"1"));
        itemService.save(new Item("줄무늬 남방", "공대생 전용 교복",5000,10, ItemType.Top ,"2"));
        itemService.save(new Item("바지", "키커보이는 바지",25000, 50,ItemType.Bottom, "3"));
        itemService.save(new Item("찢어진 청바지", "거지 아님 오해 ㄴ",25000, 40,ItemType.Bottom, "4"));
        itemService.save(new Item("아우터", "예쁜 가디건",35000, 200, ItemType.Outer, "5"));
        itemService.save(new Item("바막", "방수 짱짱맨",800000,20, ItemType.Outer, "6"));
    }


}
