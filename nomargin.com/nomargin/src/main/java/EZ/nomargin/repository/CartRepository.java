package EZ.nomargin.repository;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    Cart findByMemberId(long id);
    Cart findCartById(long id);
    Cart findCartByMember(Member member);




}
