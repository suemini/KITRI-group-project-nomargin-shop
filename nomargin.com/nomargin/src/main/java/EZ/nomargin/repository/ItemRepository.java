package EZ.nomargin.repository;

import EZ.nomargin.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long itemId);
    List<Item> findAll();
    void update(Long itemId, Item updateItem);
    void delete(Long itemId);
    List<Item> findTop();
    List<Item> findBottom();
    List<Item> findOuter();


}
