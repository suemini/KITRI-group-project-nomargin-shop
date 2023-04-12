package EZ.nomargin.repository;

import EZ.nomargin.domain.item.Item;
import EZ.nomargin.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class JpaItemRepository implements ItemRepository {
    private final EntityManager em;

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("select p from Item p", Item.class)
                .getResultList();
    }

    @Override
    public List<Item> findTop() {
        return em.createQuery("select i from Item i where i.itemType = :itemType " , Item.class)
                .setParameter("itemType", ItemType.Top)
                .getResultList();
    }

    @Override
    public List<Item> findBottom() {
        return em.createQuery("select i from Item i where i.itemType = :itemType " , Item.class)
                .setParameter("itemType",ItemType.Bottom)
                .getResultList();
    }

    @Override
    public List<Item> findOuter() {
        return em.createQuery("select i from Item i where i.itemType = :itemType " , Item.class)
                .setParameter("itemType",ItemType.Outer)
                .getResultList();
    }


    @Override
    public void update(Long itemId, Item updateItem) {
        updateItem.setItemId(itemId);
        em.merge(updateItem);
    }

    @Override
    public void delete(Long productId) {
        em.remove(findById(productId));
    }
}
