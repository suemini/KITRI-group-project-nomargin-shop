package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.dto.MemberManagementDto;
import EZ.nomargin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).get();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).get();}


    public void deleteById(Long id) {
        memberRepository.delete(findById(id));}


    public List<MemberManagementDto> findByMmDto() {
        List<Member> Members = memberRepository.findAll();
        List<MemberManagementDto> memberManagementDtos = new ArrayList<>();
        for (Member member : Members) {
            MemberManagementDto memberManagementDto = new MemberManagementDto(
                    member.getId() ,member.getLoginId(), member.getRole(), member.getName(), member.getPhoneNumber(), member.getFullAddr()
            );
            memberManagementDtos.add(memberManagementDto);

        }
        return memberManagementDtos;
    }

    public void editByMmDto(Long memberId, MemberManagementDto memberManagementDto) {

        Member member = memberRepository.findById(memberId).get();
        member.setName(memberManagementDto.getName());
        member.setRole(memberManagementDto.getRole());
        member.setPhoneNumber(memberManagementDto.getPhoneNumber());
        member.setFullAddr(memberManagementDto.getFullAddr());
        memberRepository.save(member);
    }





}