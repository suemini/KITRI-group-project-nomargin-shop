package EZ.nomargin.service;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.review.Review;
import EZ.nomargin.dto.ReviewDto;
import EZ.nomargin.repository.ItemRepository;
import EZ.nomargin.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static EZ.nomargin.dto.ReviewDto.toReviewDto;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    public Review save(ReviewDto reviewDto) {
        Review review = Review.toSave(reviewDto);
        // 현재 인증된 사용자 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
        // 사용자 ID를 작성자로 설정합니다.
        review.setReviewWriter(userId);

        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }

    public List<ReviewDto> findAll() {
        List<Review> reviewList = reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            reviewDtoList.add(toReviewDto(review));
        }
        return reviewDtoList;
    }

    @Transactional
    public void updateHits(Long id) {
        reviewRepository.updateHits(id);
    }

    public ReviewDto findById(Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            ReviewDto reviewDto = toReviewDto(review);
            return reviewDto;
        } else {
            return null;
        }
    }

    public List<ReviewDto> findByWriter(String reviewWriter) {
        List<Review> reviewList = reviewRepository.findByReviewWriter(reviewWriter);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = toReviewDto(review);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public List<Review> getReviewsByItem(Item item) {
        return reviewRepository.findByItem(item);
    }

    // 되는 버전 1
    public ReviewDto update(ReviewDto reviewDto) {
        Review review = Review.toUpdateEntity(reviewDto);
        reviewRepository.save(review);
        return findById(reviewDto.getId());
    }

    public ReviewDto updateItem(ReviewDto reviewDto) {
        Review originalReview = reviewRepository.findById(reviewDto.getId()).get();
        Review review = Review.toUpdate(originalReview, reviewDto, itemRepository);
        reviewRepository.save(review);
        return findById(reviewDto.getId());
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

}