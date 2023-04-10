package EZ.nomargin.web;

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
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/form/itemList")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long ItemId, Model model) {
        Item item = itemService.findById(ItemId);
        model.addAttribute("item", item);
        return "/form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "/form/addForm";
    }

    // V6 : 새로고침 문제 해결
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        item = itemService.save(item);
        // PRG(Post/Redirect/Get) 방식을 통해 새로고침 문제 해결!!!
        redirectAttributes.addAttribute("itemId", item.getItemId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@ModelAttribute Item item, Model model) {
        itemService.update(item.getItemId(), item);
        model.addAttribute("item",item);
        return "/form/item";
    }

    @GetMapping("/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return  "redirect:/form/items";
    }

    @GetMapping
    public ModelAndView items() {
        List<Item> items = itemService.findAll();
        ModelAndView mav = new ModelAndView("/form/items")
                .addObject("items", items);
        return mav;
    }


    @ModelAttribute("ItemType")
    public ItemType[] ItemTypes() {
        return ItemType.values();
    }


    @GetMapping("top")
    public String itemsTop(Model model) {
        List<Item> items = itemService.findTop();
        model.addAttribute("items", items);
        return "/form/itemList";
    }

    @GetMapping("bottom")
    public String itemsBottom(Model model) {
        List<Item> items = itemService.findBottom();
        model.addAttribute("items", items);
        return "/form/itemList";
    }
    @GetMapping("outer")
    public String itemsOuter(Model model) {
        List<Item> items = itemService.findOuter();
        model.addAttribute("items", items);
        return "/form/itemList";
    }


    @PostConstruct  // 생성 이후 얘를 실행
    public void initProducts() {
        itemService.save(new Item("티샤스", "이것은 흰티",10000,100, ItemType.Top ,"1" ));
        itemService.save(new Item("줄무늬 남방", "공대생 전용 교복",5000,10, ItemType.Top ,"2" ));
        itemService.save(new Item("바지", "키커보이는 바지",25000, 50,ItemType.Bottom, "3"));
        itemService.save(new Item("찢어진 청바지", "거지 아님 오해 ㄴ",25000, 40,ItemType.Bottom, "4"));
        itemService.save(new Item("아우터", "예쁜 가디건",35000, 200, ItemType.Outer, "5"));
        itemService.save(new Item("바막", "방수 짱짱맨",800000,20, ItemType.Outer, "6"));
    }


}
