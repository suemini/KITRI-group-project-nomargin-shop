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

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final ItemService itemService;
    private final CartService cartService;
    private final MemberService memberService;
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;



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

        //개별 장바구니
        List<CartItem> cartItemList = cartService.memberCart(privateCart);

//        cartItemDtoLists

        model.addAttribute("cartItemList", cartItemList);
//        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("member", member);

        return "/members/cart";
    }

    @PostMapping("/cart/purchase")
    public String purchase(@RequestParam("cartItemIds") List<Long> cartItemIds,  Model model) {
        // 선택한 카트 아이템들을 가져와서 모델에 추가합니다.
        List<CartItem> cartItems = cartService.getCartItemsByIds(cartItemIds);
        model.addAttribute("cartItems", cartItems);

        // 총 가격을 계산합니다.
        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getItem().getPrice() * item.getCount())
                .sum();
        model.addAttribute("totalPrice", totalPrice);
        return "/order/cartItemBuy";

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









    // 카트에서 내가 삭제한 아이템을 카트와 카트아이템에서 모두 제거
    @GetMapping("/cart/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId, Authentication authentication) {
        String loginId = authentication.getName();
        Member member = memberService.findByLoginId(loginId);
        Cart cart = member.getCart();

        // 카트에서 제거할 수량
        CartItem cartItemToDelete = cartItemRepository.findById(cartItemId).orElseThrow();
        int deletedQuantity = cartItemToDelete.getCount();

        // 카드에서 지우기
        cart.removeCartItem(cartItemToDelete);
        cartItemRepository.delete(cartItemToDelete);

        // 총 구매량 감소시키기
        cart.setCount(cart.getCount() - deletedQuantity);
        cartRepository.save(cart);
        return "redirect:/cart";


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






