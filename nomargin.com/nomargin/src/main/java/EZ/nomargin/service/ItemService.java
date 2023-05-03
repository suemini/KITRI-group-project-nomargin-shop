package EZ.nomargin.service;


import EZ.nomargin.domain.item.Item;
import EZ.nomargin.dto.ItemDto;
import EZ.nomargin.file.FileStore;
import EZ.nomargin.file.UploadFile;
import EZ.nomargin.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id);
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

        // 검색 결과가 없는 경우 빈 리스트 반환
        if (searchedItems.size() == 0) {
            return Collections.emptyList();
        }

        return searchedItems;
    }

    public void delete(Long itemId) {
        itemRepository.delete(itemId);
    }

    public Item post(ItemDto itemDto) throws Exception {
        Item item = new Item();

        item.setItemId(itemDto.getItemId());
        item.setItemName(itemDto.getItemName());
        item.setItemStore(itemDto.getItemStore());
        item.setPrice(itemDto.getPrice());
        item.setStock(itemDto.getStock());
        item.setItemType(itemDto.getItemType());

        MultipartFile file1 = itemDto.getMainImg();
        MultipartFile file2 = itemDto.getDetailImg1();
        MultipartFile file3 = itemDto.getDetailImg2();
        MultipartFile file4 = itemDto.getDetailImg3();
        MultipartFile file5 = itemDto.getSizeImg();

        UploadFile uploadFile1 = fileStore.storeFile(file1);
        UploadFile uploadFile2 = fileStore.storeFile(file2);
        UploadFile uploadFile3 = fileStore.storeFile(file3);
        UploadFile uploadFile4 = fileStore.storeFile(file4);
        UploadFile uploadFile5 = fileStore.storeFile(file5);

        item.setMainImg(uploadFile1.getStoreFileName());
        item.setDetailImg1(uploadFile2.getStoreFileName());
        item.setDetailImg2(uploadFile3.getStoreFileName());
        item.setDetailImg3(uploadFile4.getStoreFileName());
        item.setSizeImg(uploadFile5.getStoreFileName());

        Item savedItem = itemRepository.save(item);
        return savedItem;
    }

    public void update(Long itemId, ItemDto itemDto) throws Exception {
        Item item = itemRepository.findById(itemId);
        item.setItemId(itemDto.getItemId());
        item.setItemName(itemDto.getItemName());
        item.setItemStore(itemDto.getItemStore());
        item.setPrice(itemDto.getPrice());
        item.setStock(itemDto.getStock());
        item.setItemType(itemDto.getItemType());

        if (itemDto.getMainImg() != null && itemDto.getMainImg().getOriginalFilename().length() != 0) {
            fileStore.updateFile(item.getMainImg(), itemDto.getMainImg());
        }
        if (itemDto.getDetailImg1() != null && itemDto.getDetailImg1().getOriginalFilename().length() != 0) {
            fileStore.updateFile(item.getDetailImg1(), itemDto.getDetailImg1());
        }
        if (itemDto.getDetailImg2() != null && itemDto.getDetailImg2().getOriginalFilename().length() != 0) {
            fileStore.updateFile(item.getDetailImg2(), itemDto.getDetailImg2());
        }
        if (itemDto.getDetailImg3() != null && itemDto.getDetailImg3().getOriginalFilename().length() != 0) {
            fileStore.updateFile(item.getDetailImg3(), itemDto.getDetailImg3());
        }
        if (itemDto.getSizeImg() != null && itemDto.getSizeImg().getOriginalFilename().length() != 0) {
            fileStore.updateFile(item.getSizeImg(), itemDto.getSizeImg());
        }
        itemRepository.save(item);
    }

//    public void delete(Long itemId) {
//        itemRepository.delete(itemId);
//    }


//    public void update(Long itemId, Item updateItem) {
//        itemRepository.update(itemId, updateItem);
//    }



    //////////
    public void changeQuantity(Long itemId, int quantity) {
        // DB에 저장된 개수에서 클라가 고른 수량을 산 만큼 뺴서 저장
        Item item = itemRepository.findById(itemId);

        int stock = item.getStock();
        item.setStock(stock - quantity);

        itemRepository.save(item);
    }


}
