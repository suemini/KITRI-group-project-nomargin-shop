package EZ.nomargin.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
public class JoinDto {
    @NotBlank(message = "로그인 ID를 입력하세요!")
    private String loginId; //로그인 ID
//    private String username;
    @NotBlank(message = "가입하시는 분 성함을 입력하세요!.")
    private String name; //사용자 이름
    @NotBlank(message = "비밀번호를 입력하세요!")
    @Length(min = 8, max = 16, message = "비밀번호는 특수문자 포함 8자 이상, 16자 이하로 입력해주세요")
    @Pattern(regexp= "^.*(?=^.{8,16}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "비밀번호는 특수문자 포함 8자 이상, 16자 이하로 입력해주세요")
    private String password;

}
