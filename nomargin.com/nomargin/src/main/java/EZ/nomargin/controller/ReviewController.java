package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.order.OrderItem;
import EZ.nomargin.domain.review.Review;
import EZ.nomargin.dto.ReviewDto;
import EZ.nomargin.repository.OrderItemRepository;
import EZ.nomargin.repository.OrderRepository;
import EZ.nomargin.service.ItemService;
import EZ.nomargin.service.OrderService;
import EZ.nomargin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ItemService itemService;
    private final OrderService orderService;


    @GetMapping("/review/save")
    public String saveForm(Model model, Authentication authentication) {
        String loginId = authentication.getName();
        List<OrderItem> orderItemList = orderService.findMemberOrdersItems(loginId);

        model.addAttribute("orderitems", orderItemList);
        model.addAttribute("reviewDto", new ReviewDto());
        return "review/saveForm";
    }

    // 리뷰 저장
    @PostMapping("/review/save")
    public String save(@ModelAttribute ReviewDto reviewDto,
                       RedirectAttributes redirectAttributes, Model model) {
        Review review = reviewService.save(reviewDto);

        redirectAttributes.addAttribute("id", review.getId());
        redirectAttributes.addAttribute("status", true);

        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);

        return "redirect:/reviews/{id}";
    }

    // 리뷰 목록
    @GetMapping("/reviews")
    public String findAll(Model model) {
        List<ReviewDto> reviewDtoList = reviewService.findAll();
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        model.addAttribute("reviewList", reviewDtoList);
        return "review/list";
    }

    @GetMapping("/reviews/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session) {
        reviewService.updateHits(id, session);
        ReviewDto reviewDto = reviewService.findById(id);
        model.addAttribute("review", reviewDto);
        return "review/detail";
    }


    @GetMapping("/review/update/{id}")
    public String updateForm(@PathVariable Long id, Model model, Authentication authentication) {

        ReviewDto reviewDto = reviewService.findById(id);
        model.addAttribute("reviewUpdate", reviewDto);

        String loginId = authentication.getName();
        List<OrderItem> orderItemList = orderService.findMemberOrdersItems(loginId);

        model.addAttribute("orderitems", orderItemList);
        return "review/updateForm";
    }


    @PostMapping("/review/update")
    public String update(@ModelAttribute ReviewDto reviewDto, Model model) {
        ReviewDto review = reviewService.updateReview(reviewDto);
        model.addAttribute("review", review);
        return "review/detail";
    }


    @GetMapping("/review/delete/{id}")
    private String delete(@PathVariable Long id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }

}
