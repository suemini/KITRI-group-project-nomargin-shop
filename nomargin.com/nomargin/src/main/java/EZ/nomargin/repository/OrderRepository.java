package EZ.nomargin.repository;

import EZ.nomargin.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    //--------------05.02 추가(현덕)

//    List<OrderItem> findMemberOrdersItems(Long memberId);
//
//    OrderItem findOrdersItem(Long orderItemId);
//
//
//
//    List<Orders> findByMemberId(Long memberId);
}

