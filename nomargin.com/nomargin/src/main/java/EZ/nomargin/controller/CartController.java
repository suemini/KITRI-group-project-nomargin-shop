package EZ.nomargin.controller;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.cart.CartItem;
import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.item.ItemSize;
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.repository.CartItemRepository;
import EZ.nomargin.repository.CartRepository;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final ItemService itemService;
    private final CartService cartService;
    private final MemberService memberService;
    private final CartRepository cartRepository;


    //--------------05.02 변경(현덕)
    @PostMapping("/cart/{itemId}")
    public String addCartItem(@PathVariable("itemId") Long itemId, @RequestParam String username, int amount) {

        Member member = memberService.findByLoginId(username);
        Item item = itemService.findById(itemId);
        cartService.addCart(member, item, amount);
        return "redirect:/form/itemList/{itemId}";

    }


    // 카트로 버튼 클릭
    @GetMapping("/cart")
    public String memberCart(Model model, Authentication authentication) {

        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);

        // 로그인한 사람의 개인 장바구니의 내용물들
        Cart privateCart = member.getCart();
        List<CartItem> cartItemList = cartService.memberCart(privateCart);
        model.addAttribute("cartItemList", cartItemList);


        int totalPrice = 0;
        for (CartItem item : cartItemList) {
            totalPrice += item.getItem().getPrice() * item.getCount();
        }
        model.addAttribute("totalPrice", totalPrice);
        return "/members/cart";
    }


    // 카트에서 구입 버튼
    @PostMapping("/cart/purchase")
    public String purchase(Authentication authentication ,Model model) {

        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);
        model.addAttribute("member",member);

        // 결제 예정인 개인 카트의 물품들
        Cart privateCart = member.getCart();
        List<CartItem> selectedCartItems = new ArrayList<>(cartService.memberCart(privateCart));
        model.addAttribute("cartItems", selectedCartItems);

        // 결제 예정인 개인 아이템들의 총금액
        int totalPrice = 0;
        for (CartItem item : selectedCartItems) {
            totalPrice += item.getItem().getPrice() * item.getCount();
        }
        model.addAttribute("totalPrice", totalPrice);
        return "/order/cartItems";

    }

    // 카트에서 내가 삭제한 아이템을 주문 총량, 아이템 당 수량 변경
    @Transactional
    @GetMapping("/cart/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId, Authentication authentication) {

        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);
        Cart cart = member.getCart();

        // 카트에서 제거할 수량
        CartItem cartItemToDelete = cartService.findCartItemById(cartItemId);
        int deleteCount = cartItemToDelete.getCount();

        // 카트에서 지우기
        cart.removeCartItem(cartItemToDelete);
        cartService.deleteCartItem(cartItemToDelete);

        // 총 구매할 양 감소시키기
        cart.setCount(cart.getCount() - deleteCount);
        cartRepository.save(cart);

        return "redirect:/cart";

    }


    //카트에서 수량 변경
    @Transactional
    @PostMapping("/cart/update/{cartItemId}")
    public String updateCartItemQuantity(@PathVariable("cartItemId") Long cartItemId, @RequestParam int amount, Authentication authentication) {

        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);
        Cart cart = member.getCart();

        // 카트에서 바꿀 수량
        CartItem cartItemToUpdate = cartService.findCartItemById(cartItemId);

        if (cartItemToUpdate.getCount() > amount) {

            cart.setCount( cart.getCount() - (cartItemToUpdate.getCount() - amount));
            cartItemToUpdate.setCount(amount);// 반드시 필요
        } else {
            cart.setCount(cart.getCount() + ( amount - cartItemToUpdate.getCount()));
            cartItemToUpdate.setCount(amount);
        }

        cartRepository.save(cart);
        return "redirect:/cart";
    }





    @GetMapping("/itemBuy")
    public String itemBuy(@RequestParam("itemId") Long itemId, @RequestParam("amount") int amount, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("amount", amount);
        model.addAttribute("totalPrice", item.getPrice() * amount);
        itemService.changeQuantity(item.getItemId(), amount);
        return "order/itemBuy";
    }
}






