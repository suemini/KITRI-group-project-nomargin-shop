package EZ.nomargin.repository;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    List<Member> findAll();
    List<Member> findByName(String name);
    Optional<Member> findByLoginId(String loginId);
    void delete(Long id);


}
