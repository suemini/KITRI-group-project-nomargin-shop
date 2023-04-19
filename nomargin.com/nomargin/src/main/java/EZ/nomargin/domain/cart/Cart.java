package EZ.nomargin.domain.cart;

import EZ.nomargin.domain.member.Member;
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
public class Cart {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CART_ID")
    private Long id;
    @OneToMany
    @JoinColumn(name = "FK_MEMBER_ID")
    private List<Member> members = new ArrayList<>();



}
