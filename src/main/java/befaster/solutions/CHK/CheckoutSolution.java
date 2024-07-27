package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus == null || skus.isBlank()) {
            return 0;
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
                    total += quantity * 50;
                    break;
                case 'B':
                    total += quantity * 30;
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


