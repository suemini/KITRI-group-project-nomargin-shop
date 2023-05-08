package EZ.nomargin.domain.review;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.dto.ReviewDto;
import EZ.nomargin.repository.ItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Review {
    @Id // 리뷰번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)  // 작성자
    private String reviewWriter;

    @Column // 비밀번호
    private String reviewPass;

    @Column(length = 20)
    private String reviewTitle;

    @Column(length = 500)
    private String reviewContents;
    @Column // 조회수
    private int reviewHits;

    @CreationTimestamp  // 작성시간
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp    // 수정시간
    @Column(insertable = false)
    private LocalDateTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;


    public static Review toSaveEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setReviewPass(reviewDto.getReviewPass());
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setReviewContents(reviewDto.getReviewContents());
        review.setReviewHits(0);
        // 이 부분은 Review 엔티티의 Item 객체를 설정하는 부분입니다.
        Item item = new Item();
        item.setItemId(reviewDto.getItemId()); // ReviewDto의 itemId를 가져와 Item 객체에 설정
        review.setItem(item); // Review 엔티티에 Item 객체 설정

        return review;
    }

    public static Review toUpdateEntity(Review review, ReviewDto reviewDto, ItemRepository itemRepository) {
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setReviewContents(reviewDto.getReviewContents());
        review.setReviewHits(reviewDto.getReviewHits());
        review.setUpdatedTime(reviewDto.getReviewUpdatedTime());

        // 이 부분은 Review 엔티티의 Item 객체를 설정하는 부분입니다.
        if (reviewDto.getItemId() != null) {
            Item item = itemRepository.findById(reviewDto.getItemId());
            review.setItem(item); // Review 엔티티에 Item 객체 설정
        }
        return review;
    }


}
