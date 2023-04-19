package EZ.nomargin.service;

<<<<<<< HEAD:nomargin.com/nomargin/nomargin.com/nomargin/src/main/java/EZ/nomargin/service/MemberService.java
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService , Validator {

    //
    @Autowired
    private final JpaMemberRepository jpaMemberRepository;


    // 중복 회원 검증 TEST
    public Member joinMember(Member member) {
        validateDuplicateMember(member);
        return jpaMemberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = jpaMemberRepository.findByLoginId(member.getLoginId());
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }
    ///


    public List<Member> findMembers() {
        return jpaMemberRepository.findAll();
    }


    //조회된 회원객체를 소유한 userDetailsService
    // DB에 저장된 것을 바탕으로 만들어 냄
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> member = jpaMemberRepository.findByLoginId(loginId);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException(loginId);
        }

        return User.builder()
                .username(member.get().getLoginId())
                .password(member.get().getPassword())
                .roles(member.get().getRole().toString())
//                .authorities(Collections.emptyList()) 아무 권한(role)도 적용할지 않았을 때 반드시 설정해야함.
                .build();

    }



    //Validator
    //JoinDto가 있는지 확인
    @Override
    public boolean supports(Class<?> clazz) {
        return JoinDto.class.isAssignableFrom(clazz);
    }

    // 이미 저장된 id가 있는지 확인하고, 있으면 메시지 날림
    @Override
    public void validate(Object target, Errors errors) {
        JoinDto joinDto = (JoinDto) target;
        if (jpaMemberRepository.findByLoginId(joinDto.getLoginId()).isPresent()) {
            errors.rejectValue("loginId", "duplicate.username", "이미 존재하는 ID입니다.");
        }

        if (!joinDto.getPassword().equals(joinDto.getPassword_check())){
            errors.rejectValue("password", "notEqual.password", "비밀번호가 서로 일치하지 않습니다.");
        }
    }
=======
public class MemberService {
>>>>>>> kyungjins-patch:nomargin/src/main/java/EZ/nomargin/service/MemberService.java
}
