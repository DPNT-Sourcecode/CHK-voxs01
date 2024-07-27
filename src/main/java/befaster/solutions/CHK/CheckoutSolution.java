package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

record GetNFreeStrategy(int quantity, int freeQuantity, Character origin, Character destiny) {

    public void apply(Map<Character, Integer> productByQuantity) {
        if (productByQuantity.getOrDefault(origin, 0) >= quantity) {
            int freeN = productByQuantity.get(origin) / quantity * freeQuantity;
            productByQuantity.computeIfPresent(destiny, (c, items) -> items - freeN);

            if (productByQuantity.getOrDefault(destiny, 0) < 0) {
                productByQuantity.put(destiny, 0);
            }
        }
    }
}

public class CheckoutSolution {

    private static final Map<Character, Item> priceByItem = new HashMap<>();
    private static final List<GetNFreeStrategy> getNFreeStrategies = new ArrayList<>();

    static {
        priceByItem.put('A', new Item(50, List.of(new Offer(3, 130), new Offer(5, 200))));
        priceByItem.put('B', new Item(30, List.of(new Offer(2, 45))));
        priceByItem.put('C', new Item(20, List.of()));
        priceByItem.put('D', new Item(15, List.of()));
        priceByItem.put('E', new Item(40, List.of()));
        priceByItem.put('F', new Item(10, List.of()));

        getNFreeStrategies.add(new GetNFreeStrategy(2, 1, 'E', 'B'));
        getNFreeStrategies.add(new GetNFreeStrategy(3, 1, 'F', 'F'));
    }

    private Integer calculateTotalWithOfferForItem(int quantity, Item item) {
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
            if (item >= 'A' && item <= 'F') {
                productByQuantity.merge(item, 1, Integer::sum);
            } else {
                // No invalid values are allowed in the string
                return -1;
            }
        }

        for (GetNFreeStrategy getNFreeStrategy : getNFreeStrategies) {
            getNFreeStrategy.apply(productByQuantity);
        }

        int total = 0;
        for (Map.Entry<Character, Integer> entry : productByQuantity.entrySet()) {
            Character itemName = entry.getKey();
            Integer quantity = entry.getValue();

            Item item = priceByItem.get(itemName);
            total += calculateTotalWithOfferForItem(quantity, item);
        }

        return total;
    }
}




