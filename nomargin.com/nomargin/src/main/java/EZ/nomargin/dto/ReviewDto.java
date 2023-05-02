package EZ.nomargin.dto;


import EZ.nomargin.domain.review.Review;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String reviewWriter;
    private String reviewPass;
    private String reviewTitle;
    private String reviewContents;
    private int reviewHits;
    private LocalDateTime reviewCreatedTime;
    private LocalDateTime reviewUpdatedTime;
    private Long itemId;

    public static ReviewDto toReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setReviewWriter(review.getReviewWriter());
        reviewDto.setReviewPass(review.getReviewPass());
        reviewDto.setReviewTitle(review.getReviewTitle());
        reviewDto.setReviewContents(review.getReviewContents());
        reviewDto.setReviewHits(review.getReviewHits());
        reviewDto.setReviewCreatedTime(review.getCreatedTime());
        reviewDto.setReviewUpdatedTime(review.getUpdatedTime());
        reviewDto.setItemId(review.getItem().getItemId());
        return reviewDto;
    }
}
