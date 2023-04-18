package EZ.nomargin.service;


import EZ.nomargin.domain.item.Item;
import EZ.nomargin.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).get();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }


    public List<Item> findTop() {
        return itemRepository.findTop();
    }

    public List<Item> findBottom() {
        return itemRepository.findBottom();
    }

    public List<Item> findOuter() {
        return itemRepository.findOuter();
    }

    public List<Item> searchItems(String keyword) {
        List<Item> allItems = itemRepository.findAll();
        List<Item> searchedItems = new ArrayList<>();

        for (Item item : allItems) {
            if (item.getItemName().contains(keyword)) { // 상품 이름에서 검색어가 포함된 경우
                searchedItems.add(item);
            }
        }

        return searchedItems;
    }



    public void update(Long itemId, Item updateItem) {
        itemRepository.update(itemId, updateItem);
    }

    public void delete(Long itemId) {
        itemRepository.delete(itemId);
    }

}
