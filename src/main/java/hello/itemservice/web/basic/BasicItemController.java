package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(
            //@ModelAttribute("item") 에서 "item"은, model.addAttribute(item)을 수행해준다.
            @ModelAttribute("item") Item item
            // Model model // 생략 가능.
    ) {

        itemRepository.save(item);
        // model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(
            @ModelAttribute Item item
            // ("item")을 명시하지 않은 경우,
            //  Class 명인 Item 의 첫 글자만 소문자로 바꿔서 item 이름으로, model 에 추가한다.
    ) {

        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV4(
            Item item
            // @ModelAttribute를 생략해도, 위와 마찬가지로 item 이름으로, model 에 추가한다.
    ) {

        itemRepository.save(item);

        return "basic/item";
    }

}
