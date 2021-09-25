package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //GIVEN
        Item item = new Item("itemA",10000,10);

        //WHEN
        Item savedItem = itemRepository.save(item);

        //THEN
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        //GIVEN
        Item item1 = new Item("itemA",10000,10);
        Item item2 = new Item("itemB",20000,20);

        //WHEN
        itemRepository.save(item1);
        itemRepository.save(item2);

        //THEN
        List<Item> result = itemRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);

    }

    @Test
    void updateItem(){
        //GIVEN
        Item item1 = new Item("item1",10000,10);
        Item savedItem = itemRepository.save(item1);
        Long itemId = savedItem.getId();

        //WHEN
        Item updateParam = new Item("item2",20000,20);
        itemRepository.update(itemId,updateParam);

        //THEN
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}