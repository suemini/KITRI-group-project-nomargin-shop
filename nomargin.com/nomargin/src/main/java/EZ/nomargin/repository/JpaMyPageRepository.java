package EZ.nomargin.repository;

import EZ.nomargin.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class JpaMyPageRepository implements MyPageRepository {

    private final EntityManager em;


    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public void updateAccount(String loginId, Member updateMember) {
        Member member = findByLoginId(loginId).get();
        member.setPassword(updateMember.getPassword());
        member.setPhoneNumber(updateMember.getPhoneNumber());
        member.setPostcode(updateMember.getPostcode());
        member.setAddress(updateMember.getAddress());
        member.setDetailAddress(updateMember.getDetailAddress());
        member.setExtraAddress(updateMember.getExtraAddress());
    }

    @Override
    public void deleteAccount(String loginId) {
        em.remove(findByLoginId(loginId).get());
    }

}
