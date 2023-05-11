package EZ.nomargin.domain.order;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.cart.CartItem;
import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.item.ItemType;
import EZ.nomargin.domain.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class OrderItem {

    //--------------05.02 추가(현덕)
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    private Long orderId; // 주문 상품 번호

    private String orderName; // 주문 상품 이름

    private String itemStore;

    private Integer orderPrice; //주문가격

    private Integer orderCount; //주문수량

    private ItemType itemType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orders")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member; // 구매자

    private boolean isCancel; // false: 주문, true 취소

    private Integer orderTotalPrice;

    // 장바구니 전체 주문
    public static OrderItem createOrderItem(Long orderId, Member member, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setMember(member);
        orderItem.setOrderName(cartItem.getItem().getItemName());
        orderItem.setItemStore(cartItem.getItem().getItemStore());
        orderItem.setOrderPrice(cartItem.getItem().getPrice());
        orderItem.setOrderCount(cartItem.getCount());
        orderItem.setItemType(cartItem.getItem().getItemType());
        orderItem.setOrderTotalPrice(orderItem.orderPrice * orderItem.orderCount);
        return orderItem;
    }


    public static OrderItem createOneOrderItem(Long orderId, Member member, Item item, Orders orders, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setMember(member);
        orderItem.setOrders(orders);
        orderItem.setOrderName(item.getItemName());
        orderItem.setItemStore(item.getItemStore());
        orderItem.setOrderPrice(item.getPrice());
        orderItem.setOrderCount(count);
        orderItem.setItemType(item.getItemType());
        orderItem.setOrderTotalPrice(orderItem.orderPrice * count);
        return orderItem;
    }



}
