package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.repository.JpaMyPageRepository;
import EZ.nomargin.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final JpaMyPageRepository jpaMyPageRepository;
    private final MyPageRepository myPageRepository;


    public Member findByLoginId(String loginId) {
        return jpaMyPageRepository.findByLoginId(loginId).get();
    }

    public void updateAccount(String loginId, Member updateMember) {
        myPageRepository.updateAccount(loginId, updateMember);
    }

    public void deleteAccount(String loginId) {
        jpaMyPageRepository.deleteAccount(loginId);
    }

}
