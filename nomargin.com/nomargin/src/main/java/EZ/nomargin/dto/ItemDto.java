package EZ.nomargin.dto;

import EZ.nomargin.domain.item.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long itemId;
    private String itemName;
    private String itemStore;
    private Integer price;
    private Integer stock;
    private ItemType itemType;
    private MultipartFile mainImg;
    private MultipartFile detailImg1;
    private MultipartFile detailImg2;
    private MultipartFile detailImg3;
    private MultipartFile sizeImg;

}
