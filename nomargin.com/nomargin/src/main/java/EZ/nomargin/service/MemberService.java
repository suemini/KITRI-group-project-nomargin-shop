package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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
}
