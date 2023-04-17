package EZ.nomargin.service;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.repository.ItemRepository;
import EZ.nomargin.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final JpaMemberRepository jpaMemberRepository;

    public Member save(Member member) {
        return jpaMemberRepository.save(member);
    }

    public Member findByLoginId(String loginId) {
        return jpaMemberRepository.findByLoginId(loginId).get();
    }

    public List<Member> findAll() {
        return jpaMemberRepository.findAll();
    }


}
