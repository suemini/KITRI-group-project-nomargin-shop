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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final JpaItemRepository jpaItemRepository;
    private final CartItemRepository cartItemRepository;



    public void createCart(Member member) {
        Cart cart  = Cart.createCart(member);

        cartRepository.save(cart);

    }

    public void addCart(Member member, Item newItem, int amount) {

        // 유저 id로 해당 유저의 장바구니 찾기
        //
        //
        // Cart cart = cartRepository.findCartByMemberId(member.getId());
        Cart cart = cartRepository.findCartByMember(member);

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
            cartItem.setSize(cartItem.getSize());
            cartItemRepository.save(cartItem);
        }

        // 카트 상품 총 개수 증가
        cart.setCount(cart.getCount() + amount);

    }


    public Cart findMemberCart(Member member) {

        return cartRepository.findCartByMember(member);

    }



    // 카트 조회
    @Transactional
    public List<CartItem> memberCart(Cart memberCart) {

        // 유저의 카트 id를 꺼냄
        Long memberCartId = memberCart.getId();

        // id에 해당하는 유저가 담은 상품들 넣어둘 곳
        List<CartItem> memberCartItems = new ArrayList<>();

        // 유저 상관 없이 카트에 있는 상품 모두 불러오기
        List<CartItem> CartItems = cartItemRepository.findAll();

        for(CartItem cartItem : CartItems) {
            if(Objects.equals(cartItem.getCart().getId(), memberCartId)) {
                memberCartItems.add(cartItem);
            }
        }

        return memberCartItems;
    }

    public CartItem findCartItemByItemId(Long id) {

        CartItem cartItems = cartItemRepository.findCartItemByItemId(id);

        return cartItems;
    }


    public void cartItemDelete(Long id) {

        cartItemRepository.deleteById(id);
    }

    // 장바구니 상품 수량 변경
//    public void updateCartItemCount(Long id, int count) {
//        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//        cartItem.updateCount(count);
//    }
//
//    // 장바구니 상품 삭제
//    public void deleteCartItem(Long id) {
//        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//        cartItemRepository.delete(cartItem);
//    }





}
