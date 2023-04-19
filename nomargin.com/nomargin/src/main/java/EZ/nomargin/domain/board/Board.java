package EZ.nomargin.domain.board;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.order.Orders;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;
    @OneToMany
    @JoinColumn(name = "FK_ORDER_ID")
    private List<Orders> orders = new ArrayList<>();
    private String title;

    private String text;

    private String stars;

}
