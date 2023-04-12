package EZ.nomargin.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ItemColor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String colorName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

}
