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

import javax.servlet.http.HttpSession;
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
        Review review = Review.toSaveEntity(reviewDto);
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
    public void updateHits(Long id, HttpSession session) {
        List<Long> viewedReviewIds = (List<Long>) session.getAttribute("viewedReviewIds");
        // 세션에 저장된 조회한 리뷰 ID가 없으면 새로 생성
        if (viewedReviewIds == null) {
            viewedReviewIds = new ArrayList<>();
        }

        // 이미 조회한 리뷰인 경우 조회수를 증가시키지 않음
        if (!viewedReviewIds.contains(id)) {
            // 조회수 업데이트
            reviewRepository.updateHits(id, viewedReviewIds);
            // 조회한 리뷰 ID를 세션에 저장
            viewedReviewIds.add(id);
            session.setAttribute("viewedReviewIds", viewedReviewIds);
        }
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


    public ReviewDto updateReview(ReviewDto reviewDto) {
        Review originalReview = reviewRepository.findById(reviewDto.getId()).get();
        Review review = Review.toUpdateEntity(originalReview, reviewDto, itemRepository);
        reviewRepository.save(review);
        return findById(reviewDto.getId());
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

}
