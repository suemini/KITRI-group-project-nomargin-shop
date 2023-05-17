package EZ.nomargin.domain.member;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.order.OrderItem;
import EZ.nomargin.domain.order.Orders;
import EZ.nomargin.dto.JoinDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {
//    IDENTITY

    @Column(name ="MEMBER_ID")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId; //로그인 ID

    private String name; //사용자 이름

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullAddr;

    //--------------04.24 추가(마이페이지 수정용)
    private String postcode;

    private String address;

    private String extraAddress;

    private String detailAddress;
    //--------------

    private String phoneNumber;


    //--------------05.02 추가(현덕) // 05.17 안되면   @JsonIgnore 이거 3개 모두 없애
    // 구매자의 장바구니
    @JsonIgnore
    @OneToOne(mappedBy = "member")
    private Cart cart;

    // 구매자의 주문
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Orders> memberOrders = new ArrayList<>();

    // 구매자의 주문상품들
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<OrderItem> memberOrderItem = new ArrayList<>();




    public static Member createMember(JoinDto joinDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setLoginId(joinDto.getLoginId());
        member.setName(joinDto.getName());
        String password = passwordEncoder.encode(joinDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        member.setFullAddr(joinDto.getPostcode() + " " + joinDto.getAddress() + " " + joinDto.getExtraAddress() + " " + joinDto.getDetailAddress());
        member.setPhoneNumber(joinDto.getPhoneNumber());
        return member;
    }



}
