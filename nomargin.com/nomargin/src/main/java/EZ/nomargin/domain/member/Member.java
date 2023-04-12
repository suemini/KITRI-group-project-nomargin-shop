package EZ.nomargin.domain.member;

import EZ.nomargin.dto.JoinDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Data
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotEmpty
    private String loginId; //로그인 ID
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;




    public static Member createMember(JoinDto joinDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setLoginId(joinDto.getLoginId());
        member.setName(joinDto.getName());
//        member.setAddress(joinFormDto.getAddress());
        String password = passwordEncoder.encode(joinDto.getPassword());
        member.setPassword(password);
//        member.setRole(Role.ADMIN);
        return member;
    }


}
