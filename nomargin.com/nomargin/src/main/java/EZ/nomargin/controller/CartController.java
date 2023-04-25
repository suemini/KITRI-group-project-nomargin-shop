package EZ.nomargin.controller;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.cart.CartItem;
import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.item.ItemSize;
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.service.CartService;
import EZ.nomargin.service.ItemService;
import EZ.nomargin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final ItemService itemService;
    private final CartService cartService;
    private final MemberService memberService;


    @PostMapping("/cart/{itemId}")
    public String addCartItem(@PathVariable("itemId") Long itemId, @RequestParam String username, int amount) {


        Member member = memberService.findByLoginId(username);
        Item item = itemService.findById(itemId);


        cartService.addCart(member, item, amount);

        return "redirect:/form/itemList/{itemId}";


    }


    @GetMapping("/cart")
    public String userCartPage(Model model, Authentication authentication) {

        String loginId = authentication.getName();

        Member member = memberService.findByLoginId(loginId);


        Cart privateCart = member.getCart();


        // 장바구니에 들어있는 아이템 모두 가져오기
        List<CartItem> cartItemList = cartService.memberCart(privateCart);

        int totalPrice = 0;
        for (CartItem cartitem : cartItemList) {
            totalPrice += cartitem.getCount() * cartitem.getItem().getPrice();
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCount", privateCart.getCount());
        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("member", memberService.findByLoginId(loginId));

        return "/members/cart";
    }

//    @GetMapping("/cart/delete/{cartItemId}")
//    public String deleteCartItem(@PathVariable Long id, Model model, Authentication authentication) {
//
//        // itemId로 장바구니 상품 찾기
//        CartItem cartItem = cartService.findCartItemByItemId(id);
//
//        // 해당 유저의 카트 찾기
//        Cart userCart = cartService.findMemberCart();
//
//        // 장바구니 전체 수량 감소
//        userCart.setCount(userCart.getCount()-cartItem.getCount());
//
//        // 장바구니 물건 삭제
//        cartService.cartItemDelete(itemId);
//
//        // 해당 유저의 장바구니 상품들
//        List<CartItem> cartItemList = cartService.allUserCartView(userCart);
//
//        // 총 가격 += 수량 * 가격
//        int totalPrice = 0;
//        for (CartItem cartitem : cartItemList) {
//            totalPrice += cartitem.getCount() * cartitem.getItem().getPrice();
//        }
//
//
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("itemSize", ItemSize.values()); // 가짜
//        model.addAttribute("totalCount", privateCart.getCount());
//        model.addAttribute("cartItems", cartItemList);
//        model.addAttribute("member", memberService.findByLoginId(loginId));
//
//    }
}






