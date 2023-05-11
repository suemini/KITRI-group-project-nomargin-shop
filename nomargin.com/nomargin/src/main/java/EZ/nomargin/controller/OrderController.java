package EZ.nomargin.controller;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.cart.CartItem;
import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.domain.order.OrderItem;
import EZ.nomargin.service.CartService;
import EZ.nomargin.service.ItemService;
import EZ.nomargin.service.MemberService;
import EZ.nomargin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class OrderController {


    private final CartService cartService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final ItemService itemService;


    //--------------05.02 추가(현덕)


    // 주문 페이지에서 구매
    @Transactional
    @PostMapping("/order/checkout")
    public String purchaseCartItems(Authentication authentication, Model model) {

        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);
        Cart privateCart = member.getCart();
        model.addAttribute("member", member);

        List<CartItem> selectedCartItems = new ArrayList<>(cartService.memberCart(privateCart));
        List<OrderItem> orderItemList = new ArrayList<>();

        int purchasePrice = 0;
        for (CartItem cartItem : selectedCartItems) {
            cartItem.getItem().setStock(cartItem.getItem().getStock() - cartItem.getCount()); // 재고 변화
            cartItem.getItem().setCount(cartItem.getCount());

            OrderItem orderItem = orderService.addCartOrder(cartItem.getItem().getItemId(), member.getId(), cartItem);
            orderItemList.add(orderItem);
            purchasePrice += cartItem.getItem().getPrice() * cartItem.getCount();
        }
        model.addAttribute("purchasePrice", purchasePrice);

        // 주문 추가 & 카트 비우기
        orderService.addOrders(member, orderItemList);
        cartService.allCartItemDelete(loginId);

        return "order/purchase";
    }

    // 개인 주문 조회
    @Transactional
    @GetMapping("/order/list")
    public String memberOrderList(Model model, Authentication authentication ) {
        String loginId = authentication.getName();
        List<OrderItem> orderItemList = orderService.findMemberOrdersItems(loginId);


        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            if (!orderItem.isCancel()){
                totalPrice += orderItem.getOrderTotalPrice();
            }
        }

        model.addAttribute("orderItems", orderItemList);
        model.addAttribute("totalPrice", totalPrice);
        return "members/orderList";

    }

    // 개인 주문 취소
    @Transactional
    @PostMapping("/order/cancel/{orderItemId}")
    public String memberOrderCancel(@PathVariable("orderItemId") Long orderItemId) {
        OrderItem orderItemToCancel = orderService.findOrderItem(orderItemId);
        orderService.orderCancel(orderItemToCancel);
        return "redirect:/order/list";

    }



    // 관리자 주문 취소
    @GetMapping("/order/adminList")
    public String allOrderList(Model model) {
        List<OrderItem> allOrderItems = orderService.allOrderItem();

        int totalPrice = 0;
        for (OrderItem orderItem : allOrderItems) {
            if (!orderItem.isCancel()){
                totalPrice += orderItem.getOrderTotalPrice();
            }
        }
        int totalQuantity = 0;
        for (OrderItem orderItem : allOrderItems) {
            if (!orderItem.isCancel()){
                totalQuantity += orderItem.getOrderCount();
            }
        }


        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("allOrderItems",allOrderItems);
        return "/admin/allOrderList";
    }



    // 관리자 주문 취소
    @Transactional
    @PostMapping("/order/adminCancel/{orderItemId}")
    public String adminOrderCancel(@PathVariable("orderItemId") Long orderItemId){
        OrderItem orderItemToCancel = orderService.findOrderItem(orderItemId);
        orderService.orderCancel(orderItemToCancel);
        return "redirect:/order/adminList";
    }


    //현덕
    // 상품에서 구매버튼 눌렀을 때
    @Transactional
    @PostMapping("/order/direct/{itemId}")
    public String oneOrderDirect(@PathVariable("itemId") Long itemId, Authentication authentication, Model model, @RequestParam("amount") int count) {

        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);

        Item item = itemService.findById(itemId);

        int totalPrice = item.getPrice() * count;

        item.setStock(item.getStock() - count);

        if (item.getCount() == null) {
            item.setCount(0);
        }
        item.setCount(item.getCount() + count);

        orderService.addOneItemOrder(member.getLoginId(), item, count);

        model.addAttribute("item", item);
        model.addAttribute("totalPrice", totalPrice);

        return "/order/directPurchase";

    }

    // 단일 구매 확정
    @PostMapping("/order/direct/{itemId}/{itemCount}")
    public String oneOrderDirect(@PathVariable("itemId") Long itemId, @PathVariable("itemCount") int itemCount, Model model, Authentication authentication){

        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);
        model.addAttribute("member",member);

        Item item = itemService.findById(itemId);
        int purchasePrice = item.getPrice()*itemCount;
        model.addAttribute("purchasePrice", purchasePrice);

        return "/order/purchase";
    }
}




