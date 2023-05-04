package EZ.nomargin.service;

import EZ.nomargin.domain.cart.Cart;
import EZ.nomargin.domain.cart.CartItem;
import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.member.Member;
import EZ.nomargin.repository.CartItemRepository;
import EZ.nomargin.repository.CartRepository;
import EZ.nomargin.repository.JpaItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final JpaItemRepository jpaItemRepository;
    private final CartItemRepository cartItemRepository;



    public void addCart(Member member, Item newItem, int amount) {

        // 유저 id로 해당 유저의 장바구니 찾기
        //
        Cart cart = cartRepository.findCartById(member.getId());
//         Cart cart = cartRepository.findCartByMemberId(member.getId()); //기존 버전
//        Cart cart = cartRepository.findCartByMember(member);

        // 장바구니가 존재하지 않는다면
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Item item = jpaItemRepository.findById(newItem.getItemId());
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getItemId());


        // 상품이 장바구니에 존재하지 않는다면 카트상품 생성 후 추가
        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, item, amount);
            cartItemRepository.save(cartItem);
        }

        // 상품이 장바구니에 이미 존재한다면 수량만 증가
        else {
            cartItem.setCart(cartItem.getCart());
            cartItem.setItem(cartItem.getItem());
            cartItem.addCount(amount);
            cartItem.setCount(cartItem.getCount());
            cartItemRepository.save(cartItem);
        }

        // 카트 상품 총 개수 증가
        cart.setCount(cart.getCount() + amount);

    }




    //--------------05.02 변경(현덕)
    // 카트 조회
    @Transactional
    public List<CartItem> memberCart(Cart memberCart) {

        // 유저의 카트 id를 꺼냄
        Long memberCartId = memberCart.getId();
        List<CartItem> memberCartItems = new ArrayList<>();
        List<CartItem> CartItems = cartItemRepository.findAll();

        for(CartItem cartItem : CartItems) {
            if(Objects.equals(cartItem.getCart().getId(), memberCartId)) {
                memberCartItems.add(cartItem);
            }
        }

        return memberCartItems;
    }


    //--------------05.02 추가(현덕)

    public CartItem findCartItemById(Long Id) {
        return cartItemRepository.findCartItemById(Id);
    }

    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void save(CartItem cartItem){
        cartItemRepository.save(cartItem);}


    //--------------05.02 추가(현덕)
    // 주문 결제가 끝나면 카트 비우기
    public void allCartItemDelete(String id) {
        List<CartItem> cartItems = cartItemRepository.findAll();
        for(CartItem cartItem : cartItems){
            System.out.println(cartItem.getCart().getMember().getLoginId());
            if(cartItem.getCart().getMember().getLoginId().equals(id)) {
                cartItem.getCart().setCount(0);
                cartItemRepository.deleteById(cartItem.getId());
            }
        }
    }

    // 05/03 추가(경진)
    public List<CartItem> getCartItemList(Member member) {
        Optional<Cart> optionalCart = cartRepository.findByMember(member);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            return cart.getCartItems();
        } else {
            return Collections.emptyList();
        }
    }

    public int getTotalPrice(Member member) {
        List<CartItem> cartItemList = getCartItemList(member);
        int totalPrice = 0;
        for (CartItem cartItem : cartItemList) {
            int itemPrice = cartItem.getItem().getPrice();
            int itemCount = cartItem.getCount();
            totalPrice += itemPrice * itemCount;
        }
        return totalPrice;
    }


}
