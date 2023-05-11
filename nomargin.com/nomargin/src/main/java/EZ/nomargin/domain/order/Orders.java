package EZ.nomargin.domain.order;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Data
public class Orders {

    //--------------05.02 추가(현덕)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;
    //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // 구매자

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems = new ArrayList<>();



    @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime orderDateTime; // 구매 일시


    // 주문 상품을 주문 상품 리스트에 추가ㅣ
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }


    // 주문 날짜를 지금 날짜로 설정
    @PrePersist
    public void createDate() {
        this.orderDateTime = LocalDateTime.now();
    }


    // 장바구니 구매
    public static Orders createOrder(Member member, List<OrderItem> orderItemList) {
        Orders order = new Orders();
        order.setMember(member);
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    // 현덕
    //단일 구매
    public static Orders createOneOrder(Member member) {
        Orders orders = new Orders();
        orders.setMember(member);
        return orders;
    }
}
