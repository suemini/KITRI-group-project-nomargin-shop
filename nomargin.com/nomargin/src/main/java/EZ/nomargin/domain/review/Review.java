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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String reviewWriter;

    @Column
    private String reviewPass;

    @Column
    private String reviewTitle;

    @Column(length = 500)
    private String reviewContents;
    @Column
    private int reviewHits;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;


    public static Review toSave(ReviewDto reviewDto) {
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

    public static Review toUpdateEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setReviewWriter(reviewDto.getReviewWriter());
        review.setReviewPass(reviewDto.getReviewPass());
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setReviewContents(reviewDto.getReviewContents());
        review.setReviewHits(reviewDto.getReviewHits());
        review.setUpdatedTime(reviewDto.getReviewUpdatedTime());
        return review;
    }

    public static Review toUpdate(Review review, ReviewDto reviewDto, ItemRepository itemRepository) {
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
