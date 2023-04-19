package EZ.nomargin.domain.item;

import EZ.nomargin.dto.ItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {
    public static Item toEntity(ItemDto dto, UploadFile attachFile, List<UploadFile> imageFiles) {
        Item item = new Item();
        item.setItemId(dto.getItemId());
        item.setItemName(dto.getItemName());
        item.setItemStore(dto.getItemStore());
        item.setPrice(dto.getPrice());
        item.setStock(dto.getStock());
        item.setItemType(dto.getItemType());
        item.setItemName(dto.getItemName());
        item.setImgDetail1(dto.getImgDetail1());
        item.setImgDetail2(dto.getImgDetail2());
        item.setImgDetail3(dto.getImgDetail3());
        item.setImgSize(dto.getImgSize());
        item.setAttachFile(attachFile);
        item.setImageFiles(imageFiles);
        return item;
    }
}
