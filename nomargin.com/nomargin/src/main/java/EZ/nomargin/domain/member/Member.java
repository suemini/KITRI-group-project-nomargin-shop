package EZ.nomargin.domain.member;

import EZ.nomargin.dto.JoinDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.util.Collection;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Member {


    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId; //로그인 ID

    private String name; //사용자 이름

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullAddr;

    private String phoneNumber;


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
