package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character, Item> priceByItem = new HashMap<>();

    static {
        priceByItem.put('A', new Item(50, List.of(new Offer(3, 130), new Offer(5, 200))));
        priceByItem.put('B', new Item(30, List.of(new Offer(2, 45))));
        priceByItem.put('C', new Item(20, List.of()));
        priceByItem.put('D', new Item(15, List.of()));
        priceByItem.put('E', new Item(40, List.of()));
        priceByItem.put('F', new Item(10, List.of()));
    }

    private Integer calculateOffer(int quantity, Item item) {
        int count = quantity;
        int total = 0;
        for (Offer offer : item.getOffers()) {
            int offerCount = count / offer.getQuantity();
            total += offerCount * offer.getTotalPrice();
            count %= offer.getQuantity();
        }
        total += count * item.getPrice();
        return total;
    }

    public Integer checkout(String skus) {
        if (skus == null) {
            return -1;
        }

        if (skus.isBlank()) {
            return 0;
        }

        Map<Character, Integer> productByQuantity = new HashMap<>();
        for (int i = 0; i < skus.length(); i++) {
            char item = skus.charAt(i);
            if (item >= 'A' && item <= 'E') {
                productByQuantity.merge(item, 1, Integer::sum);
            } else {
                // No invalid values are allowed in the string
                return -1;
            }
        }

        if (productByQuantity.getOrDefault('E', 0) >= 2) {
            int freeBs = productByQuantity.get('E') / 2;
            productByQuantity.computeIfPresent('B', (c, items) -> items - freeBs);

            if (productByQuantity.getOrDefault('B', 0) < 0) {
                productByQuantity.put('B', 0);
            }
        }

        int total = 0;
        for (Map.Entry<Character, Integer> entry : productByQuantity.entrySet()) {
            Character itemName = entry.getKey();
            Integer quantity = entry.getValue();

            Item item = priceByItem.get(itemName);
            total += calculateOffer(quantity, item);
        }

        return total;
    }
}

