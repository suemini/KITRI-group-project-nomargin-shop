package EZ.nomargin.repository;

import EZ.nomargin.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long id);
//    Optional<Item> findByName(String name);
    List<Item> findAll();
    void update(Long productId, Item updateProduct);
    void delete(Long productId);
    List<Item> findTop();
    List<Item> findBottom();
    List<Item> findOuter();

}
