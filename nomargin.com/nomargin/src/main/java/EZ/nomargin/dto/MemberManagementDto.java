package EZ.nomargin.dto;

import EZ.nomargin.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberManagementDto {

    private long id;
    private String loginId;
    private Role role;
    private String name;
    private String phoneNumber;
    private String fullAddr;


}
