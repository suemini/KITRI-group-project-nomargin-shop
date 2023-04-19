package EZ.nomargin.dto;

import EZ.nomargin.domain.item.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long itemId;
    private String itemName;
    private String itemStore;
    private Integer price;
    private Integer stock;
    private ItemType itemType;
    private String imgName;
    private String imgDetail1;
    private String imgDetail2;
    private String imgDetail3;
    private String imgSize;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
