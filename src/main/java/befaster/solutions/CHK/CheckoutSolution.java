package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CheckoutSolution {

    private static final Map<Character, Item> priceByItem;

    private void initializePrices() {
        priceByItem.put('A', new Item(50, List.of(new Offer(3, 130), new Offer(5, 200))));
        priceByItem.put('B', new Item(30, List.of(new Offer(2, 45))));
        priceByItem.put('C', new Item(20, List.of()));
        priceByItem.put('D', new Item(15, List.of()));
        priceByItem.put('E', new Item(40, List.of()));
    }

    private Integer calculateOffer(int quantity, Item item) {
        if (item.getOffers().isEmpty()) {
            return quantity * item.getPrice();
        }

        // Not clear at the moment if this will handle their cases, but will prevent errors
        if (item.getOffers() == 0) {
            return 0;
        }
        int repeatOffer = quantity / offerQuantity;
        int remainingProductsWithoutOffer = quantity % offerQuantity;
        return repeatOffer * offerPrice + remainingProductsWithoutOffer * normalPrice;
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
            if (item >= 'A' && item <= 'D') {
                productByQuantity.put(item, productByQuantity.getOrDefault(item, 0) + 1);
            } else {
                // No invalid values are allowed in the string
                return -1;
            }
        }

        int total = 0;
        for (Map.Entry<Character, Integer> entry : productByQuantity.entrySet()) {
            Character item = entry.getKey();
            Integer quantity = entry.getValue();

            switch (item) {
                case 'A':
                    total += calculateOffer(quantity, 50, 130, 3);
                    break;
                case 'B':
                    total += calculateOffer(quantity, 30, 45, 2);
                    break;
                case 'C':
                    total += quantity * 20;
                    break;
                case 'D':
                    total += quantity * 15;
                    break;
                default:
                    break;
            }
        }

        return total;
    }
}



