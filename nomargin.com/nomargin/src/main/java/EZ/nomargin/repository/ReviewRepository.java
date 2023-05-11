package EZ.nomargin.repository;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 조회수 추가
    @Modifying
    @Query(value = "update Review r set r.reviewHits=r.reviewHits+1 where r.id=:id and r.id not in :viewedReviewIds")
    void updateHits(@Param("id") Long id, @Param("viewedReviewIds") List<Long> viewedReviewIds);

    List<Review> findByReviewWriter(String reviewWriter);

    List<Review> findByItem(Item item);
}
