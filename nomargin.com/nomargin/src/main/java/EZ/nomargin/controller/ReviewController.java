package EZ.nomargin.controller;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.review.Review;
import EZ.nomargin.dto.ReviewDto;
import EZ.nomargin.service.ItemService;
import EZ.nomargin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    @Autowired
    private final ItemService itemService;

    @GetMapping("/save")
    public String saveForm(Model model) {
        List<Item> item = itemService.findAll();
        model.addAttribute("items", item);
        model.addAttribute("reviewDto", new ReviewDto());
        return "review/saveForm";
    }

    // 리뷰 저장
    @PostMapping("/save")
    public String save(@ModelAttribute ReviewDto reviewDto,
                       RedirectAttributes redirectAttributes, Model model) {
        Review review = reviewService.save(reviewDto);

        redirectAttributes.addAttribute("id", review.getId());
        redirectAttributes.addAttribute("status", true);

        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);

        return "redirect:/review/{id}";
    }

    // 리뷰 목록
    @GetMapping("")
    public String findAll(Model model) {
        List<ReviewDto> reviewDtoList = reviewService.findAll();
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        model.addAttribute("reviewList", reviewDtoList);
        return "review/list";
    }

    // 리뷰 상세
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        reviewService.updateHits(id);
        ReviewDto reviewDto = reviewService.findById(id);
        model.addAttribute("review", reviewDto);
        return "review/detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        ReviewDto reviewDto = reviewService.findById(id);
        model.addAttribute("reviewUpdate", reviewDto);
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "review/updateForm";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute ReviewDto reviewDto, Model model) {
        ReviewDto review = reviewService.updateReview(reviewDto);
        model.addAttribute("review", review);
        return "review/detail";
    }


    @GetMapping("/delete/{id}")
    private String delete(@PathVariable Long id) {
        reviewService.delete(id);
        return "redirect:/review";
    }

}
