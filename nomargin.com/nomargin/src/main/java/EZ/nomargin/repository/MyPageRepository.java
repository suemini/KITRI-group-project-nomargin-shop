package EZ.nomargin.repository;

import EZ.nomargin.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MyPageRepository {

    List<Member> findAll();
    Optional<Member> findByLoginId(String loginId);
    void updateAccount(String loginId, Member updateMember);
    void deleteAccount(String loginId);

}

