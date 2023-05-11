package EZ.nomargin.service;


import EZ.nomargin.domain.cart.CartItem;
import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.domain.order.OrderItem;
import EZ.nomargin.domain.order.Orders;
import EZ.nomargin.repository.MemberRepository;
import EZ.nomargin.repository.OrderItemRepository;
import EZ.nomargin.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//--------------05.02 추가(현덕)

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository; // 주문 당한 애들 저장
    private final OrderItemRepository orderItemRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ItemService itemService;


//
    public List<OrderItem> allOrderItem(){
        return orderItemRepository.findAll();

    }


    // 주문시 주문 정보를 저장
    @Transactional
    public void addOrders(Member member, List<OrderItem> orderItemList) {
        Orders memberOrder = Orders.createOrder(member, orderItemList);
        orderRepository.save(memberOrder);
    }




    // 주문시 주문 상품 정보를 저장
    @Transactional
    public OrderItem addCartOrder(Long itemId , Long id, CartItem cartItem) {
        Member member = memberService.findById(id);
        OrderItem orderItem = OrderItem.createOrderItem(itemId, member, cartItem);
        orderItemRepository.save(orderItem);
        return orderItem;
    }



    // 주문 취소 기능
    @Transactional
    public void orderCancel(OrderItem cancelItem) {
        Item item = itemService.findById(cancelItem.getOrderId());
        item.setStock(item.getStock()+ cancelItem.getOrderCount()); // 재고 다시 늘림
        int value1 = item.getCount();
        int value2 = cancelItem.getOrderCount();

        //item.setCount(value); // 판매량 감소
        cancelItem.setCancel(true); // 주문 취소
        orderItemRepository.save(cancelItem);
    }

    // loginId별 주문한 상품 조회
    public List<OrderItem> findMemberOrdersItems(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).get();
        List<OrderItem> orderItemList = orderItemRepository.findByMember(member);
        return orderItemList;
    }

    // 주문한 아이템을 ItemId로 검색
    public OrderItem findOrderItem(Long ItemId){
        return orderItemRepository.findOrderItemById(ItemId);
    }


    // 현덕
    // 단일 구매시 order과 orderItem에 바로 저장
    @Transactional
    public void addOneItemOrder(String memberId, Item item, int count) {
        Member member = memberService.findByLoginId(memberId);
        Orders order = Orders.createOneOrder(member);
        OrderItem orderItem = OrderItem.createOneOrderItem(item.getItemId(), member, item, order, count);
        orderItemRepository.save(orderItem);
        orderRepository.save(order);
    }

}
