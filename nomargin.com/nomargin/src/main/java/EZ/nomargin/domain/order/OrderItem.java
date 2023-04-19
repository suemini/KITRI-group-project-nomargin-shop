package EZ.nomargin.domain.order;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @OneToMany
    @JoinColumn(name = "FK_CART_ID")
    private List<Cart> carts = new ArrayList<>();


    @OneToMany
    @JoinColumn(name = "FK_ITEM_ID")
    private List<Item> items = new ArrayList<>();

    private int count;

    private int price;


}
