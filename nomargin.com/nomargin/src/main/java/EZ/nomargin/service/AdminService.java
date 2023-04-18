package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.dto.JoinDto;
import EZ.nomargin.dto.MemberManagementDto;
import EZ.nomargin.repository.JpaMemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
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



    public List<MemberManagementDto> findByMMDto() {
        List<Member> Members = jpaMemberRepository.findAll();
        List<MemberManagementDto> memberManagementDtos = new ArrayList<>();
        for (Member member : Members) {
            MemberManagementDto memberManagementDto = new MemberManagementDto(
                    member.getId() ,member.getLoginId(), member.getRole(), member.getName(), member.getPhoneNumber(), member.getFullAddr()
            );
            memberManagementDtos.add(memberManagementDto);

        }
        return memberManagementDtos;
    }








}
