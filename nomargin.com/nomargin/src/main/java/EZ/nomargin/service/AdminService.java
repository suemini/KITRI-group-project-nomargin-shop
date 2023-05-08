package EZ.nomargin.service;

import EZ.nomargin.domain.member.Member;
import EZ.nomargin.domain.order.Orders;
import EZ.nomargin.dto.MemberManagementDto;
import EZ.nomargin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;



    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).get();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).get();}


    @Transactional
    public void deleteById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not Existing Member id: " + id));

        // Cart 부터 삭제
        if (member.getCart() != null) {
            cartItemRepository.deleteAll(member.getCart().getCartItems());
            cartRepository.delete(member.getCart());
        }
        for (Orders orders : member.getMemberOrders()) {
            orderItemRepository.deleteAll(orders.getOrderItems());
        }
        orderRepository.deleteAll(member.getMemberOrders());
        orderItemRepository.deleteAll(member.getMemberOrderItem());
        memberRepository.delete(member);
    }



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