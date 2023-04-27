package EZ.nomargin.domain.cart;

import EZ.nomargin.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    private int count;


    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setCount(0);
        cart.setMember(member);
        return cart;

    }
}

