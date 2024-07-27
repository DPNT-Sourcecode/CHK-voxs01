package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private Integer calculateOffer(int quantity, int normalPrice, int offerPrice, int offerQuantity) {
        // Not clear at the moment if this will handle their cases, but will prevent errors
        if (offerQuantity == 0) {
            return 0;
        }
        int repeatOffer = quantity / offerQuantity;
        int remainingProductsWithoutOffer = quantity % offerQuantity;
        return repeatOffer * offerPrice + remainingProductsWithoutOffer * normalPrice;
    }

    public Integer checkout(String skus) {
        if (skus == null || skus.isBlank()) {
            return -1;
        }

        Map<Character, Integer> productByQuantity = new HashMap<>();
        for (int i = 0; i < skus.length(); i++) {
            char item = skus.charAt(i);
            productByQuantity.put(item, productByQuantity.getOrDefault(item, 0) + 1);
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

