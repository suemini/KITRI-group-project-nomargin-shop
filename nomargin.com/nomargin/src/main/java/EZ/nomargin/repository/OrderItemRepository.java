package EZ.nomargin.repository;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


    //--------------05.02 추가(현덕)
    List<OrderItem> findAll();
    List<OrderItem> findByMember(Member member);

    OrderItem findOrderItemById(Long orderItemId);

}
