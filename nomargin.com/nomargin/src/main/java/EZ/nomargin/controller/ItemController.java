package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.item.ItemType;
import EZ.nomargin.domain.review.Review;
import EZ.nomargin.file.FileStore;
import EZ.nomargin.service.ItemService;
import EZ.nomargin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final FileStore fileStore;
    private final ReviewService reviewService;

    // 상품 상세페이지(해당하는 리뷰 추가)
    @GetMapping("/form/itemList/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("stock", item.getStock());
        List<Review> reviewList = reviewService.getReviewsByItem(item);
        model.addAttribute("reviewList", reviewList);
        return "/form/item";
    }

    @ModelAttribute("ItemType")
    public ItemType[] ItemTypes() {
        return ItemType.values();
    }


    @GetMapping("/form/itemList/type/{type}")
    public String itemsTop(@PathVariable("type") String type,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(name = "pageSize", defaultValue = "9") int pageSize,
                           Model model) {
        List<Item> items = new ArrayList<>();
        switch (type) {
            case "top":
                items = itemService.findTop();
                model.addAttribute("type", type.toLowerCase());
                break;
            case "bottom":
                items = itemService.findBottom();
                model.addAttribute("type", type.toLowerCase());
                break;
            case "outer":
                items = itemService.findOuter();
                model.addAttribute("type", type.toLowerCase());
                break;
        }

        // 전체 아이템 개수
        int itemCount = items.size();

        // 전체 페이지 수
        int pageCount = (itemCount + pageSize - 1) / pageSize;

        // 현재 페이지의 아이템
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, itemCount);
        List<Item> pageItems = items.subList(start, end);

        // 페이지 링크를 구성하기 위한 로직
        int visiblePages = 5;
        int firstPage = Math.max(1, Math.min(pageCount - visiblePages + 1, page - 2));
        int lastPage = Math.min(pageCount, Math.max(visiblePages, page + 2));

        model.addAttribute("items", pageItems);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);

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

    @GetMapping("/form/itemList/search")
    public String itemList(@RequestParam("keyword") String keyword, Model model) {
        List<Item> items = itemService.searchItems(keyword);
        if(items.isEmpty()) {
            model.addAttribute("message", "검색 결과가 없습니다.");
        } else {
            model.addAttribute("items", items);
        }
        return "/form/itemList";
    }

    // 상품 이미지 등록
    @ResponseBody
    @GetMapping("/item/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

//    @PostConstruct  // 생성 이후 얘를 실행
//    public void initProducts() {
//        itemService.save(new Item("탑 티셔츠", "Shop1102717919 Store", 19000, 100, ItemType.Top,"1.main", "1.1", "1.2", "1.3", "topSize",0));
//        itemService.save(new Item("프린트 후드 티", "Shop1102733813 Store", 12000, 100, ItemType.Top,"2.main", "2.1", "2.2.", "2.3", "topSize",0));
//        itemService.save(new Item("패치워크 스웨터", "LBL LEADING THE BETTER Store", 11700, 100, ItemType.Top,"3.main", "3.1", "3.2", "3.3", "topSize",0));
//        itemService.save(new Item("코튼 긴팔 티셔츠", "MrValloon Store", 22600, 100, ItemType.Top,"4.main", "4.1", "4.2", "4.3", "topSize",0));
//        itemService.save(new Item("하라주쿠 레터 후드 스웨터", "DUOFIER Dropshipping Store", 13000, 100, ItemType.Top,"5.main", "5.1", "5.2", "5.3", "topSize",0));
//        itemService.save(new Item("스포츠 티셔츠", "shop5831243", 21000, 100, ItemType.Top,"6.main", "6.1", "6.2", "6.3", "topSize",0));
//        itemService.save(new Item("오버사이즈 코튼 루즈핏 티셔츠", "FOGESSENTIALSLOT", 27000, 100, ItemType.Top,"7.main", "7.1", "7.2", "7.3", "topSize",0));
//        itemService.save(new Item("클래식 긴 소매 티셔츠", "Fashion", 29000, 100, ItemType.Top,"8.main", "8.1", "8.2", "8.3", "topSize",0));
//        itemService.save(new Item("캐주얼 소프트 통기성 스웨트 팬츠", "TOLOER", 28000, 100, ItemType.Bottom,"9.main", "9.1", "9.2", "9.3", "bottomSize",0));
//        itemService.save(new Item("오버사이즈 루즈핏 캐주얼 바지", "I need a billion", 36700, 100, ItemType.Bottom,"10.main", "10.1", "10.2", "10.3", "bottomSize",0));
//        itemService.save(new Item("코튼 캐주얼 바지", "neverfunction", 38300, 100, ItemType.Bottom,"11.main", "11.1", "11.2", "11.3", "bottomSize",0));
//        itemService.save(new Item("비즈니스 캐주얼 바지", "Isfriday Aelisa", 17000, 100, ItemType.Bottom,"12.main", "12.1", "12.2", "12.3", "bottomSize",0));
//        itemService.save(new Item("비즈니스 스트레이트 바지", "Brother Wang", 44200, 100, ItemType.Bottom,"13.main", "13.1", "13.2", "13.3", "bottomSize",0));
//        itemService.save(new Item("캐주얼 스웨트 팬츠", "TOLOER", 70000, 100, ItemType.Bottom,"14.main", "14.1", "14.2", "14.3", "bottomSize",0));
//        itemService.save(new Item("영국 비즈니스 패션 바지", "JEYWOOD", 42000, 100, ItemType.Bottom,"15.main", "15.1", "15.2", "15.3", "bottomSize",0));
//        itemService.save(new Item("3D 패턴 슬림 스키니 프린트 청바지", "Vogue 333 Store", 720000, 100, ItemType.Bottom,"16.main", "16.1", "16.2", "16.3", "bottomSize",0));
//        itemService.save(new Item("루즈핏 코튼 데님 재킷", "Vogue 333 Store", 58400, 100, ItemType.Outer,"17.main", "17.1", "17.2", "17.3", "outerSize",0));
//        itemService.save(new Item("하이 스트리트 데님 자켓", "Vogue 333 Store", 183000, 100, ItemType.Outer,"18.main", "18.1", "18.2", "18.3", "outerSize",0));
//        itemService.save(new Item("곰 자수 가디건 스웨터", "Phoenixce Store", 16700, 100, ItemType.Outer,"19.main", "19.1", "19.2", "19.3", "outerSize",0));
//        itemService.save(new Item("양모 캐시미어 스웨터", "Phoenixce Store", 69000, 100, ItemType.Outer,"20.main", "20.1", "20.2", "20.3", "outerSize",0));
//        itemService.save(new Item("야구 칼라 재킷", "Phoenixce Store", 18400, 100, ItemType.Outer,"21.main", "21.1", "21.2", "21.3", "outerSize",0));
//        itemService.save(new Item("골프 지퍼 재킷", "Vogue 333 Store", 23000, 100, ItemType.Outer,"22.main", "22.1", "22.2", "22.3", "outerSize",0));
//        itemService.save(new Item("대학 캠퍼스 무지 캐주얼 재킷", "HKAZ Official Store", 18000, 100, ItemType.Outer,"23.main", "23.1", "23.2", "23.3", "outerSize",0));
//        itemService.save(new Item("코듀로이 단색 다목적 코트", "FURCW Store", 11700, 100, ItemType.Outer,"24.main", "24.1", "24.2", "24.3", "outerSize",0));
//        itemService.save(new Item("후드 풀오버 재킷", "My Lord Store", 15000, 100, ItemType.Outer,"25.main", "25.1", "25.2", "25.3", "outerSize",0));
//    }
}