package EZ.nomargin.domain.member;

import EZ.nomargin.dto.JoinDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
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
    @NotEmpty
    private String loginId; //로그인 ID
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;



    public static Member createMember(JoinDto joinDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setLoginId(joinDto.getLoginId());
        member.setName(joinDto.getName());
//        member.setAddress(joinFormDto.getAddress());
        String password = passwordEncoder.encode(joinDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }

}
