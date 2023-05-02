package EZ.nomargin.repository;

import EZ.nomargin.domain.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.item.itemId = :itemId")
    CartItem findByCartIdAndItemId(@Param("cartId") Long cartId, @Param("itemId") Long itemId);
    @Query("SELECT c FROM CartItem c WHERE c.item.itemId = :itemId")
    CartItem findCartItemByItemId(@Param("itemId") Long itemId);

    //--------------05.02 추가(현덕)
    CartItem findCartItemById(Long id);

    void deleteById(Long id);

}