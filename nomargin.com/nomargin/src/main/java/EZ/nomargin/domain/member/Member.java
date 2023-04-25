package EZ.nomargin.domain.member;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.dto.JoinDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {


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

    private String phoneNumber;


    @OneToOne(mappedBy = "member")
    private Cart cart;



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
