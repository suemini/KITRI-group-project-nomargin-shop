package EZ.nomargin.repository;

<<<<<<< HEAD:nomargin.com/nomargin/nomargin.com/nomargin/src/main/java/EZ/nomargin/repository/MemberRepository.java
import EZ.nomargin.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Member findOne(Long id);
    List<Member> findAll();
    List<Member> findByName(String name);
    Optional<Member> findByLoginId(String loginId);

=======
public class MemberRepository {
>>>>>>> kyungjins-patch:nomargin/src/main/java/EZ/nomargin/repository/MemberRepository.java
}
