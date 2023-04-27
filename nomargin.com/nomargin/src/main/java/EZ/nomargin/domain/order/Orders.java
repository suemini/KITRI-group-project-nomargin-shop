package EZ.nomargin.domain.order;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @OneToMany
    @JoinColumn(name = "FK_MEMBER_ID")
    private List<Member> members = new ArrayList<>();

    private int datetime;

    private String status;




}
