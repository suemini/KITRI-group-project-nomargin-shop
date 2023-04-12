package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

//    implements UserDetailsService
    private final MemberRepository memberRepository;


    // 중복 회원 검증
    @Transactional
    public Member joinMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());
        if (findMembers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


//    @Override
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//        Optional<Member> member = memberRepository.findByLoginId(loginId);
//
//        if(member.isEmpty()) {
//            throw new UsernameNotFoundException(loginId);
//        }
//
//
//        return User.builder()
//                .username(member.get().getLoginId())
//                .password(member.get().getPassword())
//                .build();
////        .roles(member.getRole().toString())
//    }
}
