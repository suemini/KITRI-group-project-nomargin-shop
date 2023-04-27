package EZ.nomargin.repository;

import EZ.nomargin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {




    Optional<Member> findById(Long id);

    List<Member> findAll();


    List<Member> findByName(String name);

    @Query("SELECT m FROM Member m WHERE m.loginId = :loginId")
    Optional<Member> findByLoginId(@Param("loginId") String loginId);

    void deleteById(Long id);


}
