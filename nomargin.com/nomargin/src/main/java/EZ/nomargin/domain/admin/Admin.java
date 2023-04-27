package EZ.nomargin.domain.admin;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private Long id;

    @OneToMany
    @JoinColumn(name = "FK_MEMBER_ID")
    private List<Member> members = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "FK_ITEM_ID")
    private List<Item> items = new ArrayList<>();


}
