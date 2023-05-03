package EZ.nomargin.repository;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 05/03 추가(경진)
    Optional<Cart> findByMember(Member member);

    Cart findByMemberId(long id);
    Cart findCartById(long id);
    Cart findCartByMember(Member member);




}
