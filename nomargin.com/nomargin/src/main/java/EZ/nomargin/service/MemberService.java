package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

//
    private final JpaMemberRepository jpaMemberRepository;


    // 중복 회원 검증

    public Member joinMember(Member member) {
        validateDuplicateMember(member);
        return jpaMemberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers = jpaMemberRepository.findByLoginId(member.getLoginId());
        if (findMembers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public List<Member> findMembers() {
        return jpaMemberRepository.findAll();
    }


    public Member findOne(Long memberId) {
        return jpaMemberRepository.findOne(memberId);
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> member = jpaMemberRepository.findByLoginId(loginId);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException(loginId);
        }

        return User.builder()
                .username(member.get().getLoginId())
                .password(member.get().getPassword())
                .build();

    }
}
