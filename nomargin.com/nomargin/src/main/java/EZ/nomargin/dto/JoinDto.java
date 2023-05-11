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
    @Pattern( regexp = "^[a-z]{1}[a-z0-9]{5,10}+$", message = "영문 숫자 조합으로 6자 이상, 10자 이하로 입력해주세요")
    private String loginId; //로그인 ID
//    private String username;

    @NotBlank(message = "가입하시는 분 성함을 입력하세요!.")
    private String name; //사용자 이름

    @NotBlank(message = "비밀번호를 입력하세요!")
    @Pattern( regexp= "^.*(?=^.{8,16}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "비밀번호는 특수문자 포함 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    private String password_check;

    @NotBlank(message = "주소를 입력해 주세요")
    private String postcode;

    private String address;

    private String extraAddress;

    @NotBlank(message = "상세 주소를 입력해 주세요")
    private String detailAddress;

    @Pattern( regexp= "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "'-'포함해서 휴대폰 번호를 입력해주세요")
    private String phoneNumber;



}
