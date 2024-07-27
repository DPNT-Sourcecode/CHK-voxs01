package befaster.solutions.CHK;

import java.util.*;

record SpecialOfferStrategy() {
    public static Integer apply(int quantity, Item item) {
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
}

record GetNFreeStrategy(int quantity, Character origin, int freeQuantity, Character destiny) {

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

record GroupDiscountStrategy(List<Character> items, int discountQuantity, int totalPrice) {
    public int apply(Map<Character, Integer> productByQuantity, Map<Character, Item> priceByItem) {
        List<Character> allItems = new ArrayList<>();
        for (Character item : items) {
            Integer itemQuantity = productByQuantity.getOrDefault(item, 0);
            for (Integer i = 0; i < itemQuantity; i++) {
                allItems.add(item);
            }
        }
        // Sorting in descending price order
        allItems.sort((a, b) -> Integer.compare(priceByItem.get(b).getPrice(), priceByItem.get(a).getPrice()));

        // Number of groups we will apply the discounts
        int nDiscountGroups = allItems.size() / discountQuantity;
        // Now we remove the most expensive items from the list and remove them from the map
        for (int i = 0; i < nDiscountGroups * discountQuantity; i++) {
            Character item = allItems.get(i);
            productByQuantity.merge(item, -1, Integer::sum);
        }

        return nDiscountGroups * totalPrice;
    }
}

public class CheckoutSolution {

    private static final Map<Character, Item> priceByItem = new HashMap<>();
    private static final List<GetNFreeStrategy> getNFreeStrategies = new ArrayList<>();
    private static final List<GroupDiscountStrategy> groupDiscountStrategies = new ArrayList<>();

    static {
        priceByItem.put('A', new Item(50, List.of(new Offer(3, 130), new Offer(5, 200))));
        priceByItem.put('B', new Item(30, List.of(new Offer(2, 45))));
        priceByItem.put('C', new Item(20, List.of()));
        priceByItem.put('D', new Item(15, List.of()));
        priceByItem.put('E', new Item(40, List.of()));
        priceByItem.put('F', new Item(10, List.of()));
        priceByItem.put('G', new Item(20, List.of()));
        priceByItem.put('H', new Item(10, List.of(new Offer(5, 45), new Offer(10, 80))));
        priceByItem.put('I', new Item(35, List.of()));
        priceByItem.put('J', new Item(60, List.of()));
        priceByItem.put('K', new Item(70, List.of(new Offer(2, 120))));
        priceByItem.put('L', new Item(90, List.of()));
        priceByItem.put('M', new Item(15, List.of()));
        priceByItem.put('N', new Item(40, List.of()));
        priceByItem.put('O', new Item(10, List.of()));
        priceByItem.put('P', new Item(50, List.of(new Offer(5, 200))));
        priceByItem.put('Q', new Item(30, List.of(new Offer(3, 80))));
        priceByItem.put('R', new Item(50, List.of()));
        priceByItem.put('S', new Item(20, List.of()));
        priceByItem.put('T', new Item(20, List.of()));
        priceByItem.put('U', new Item(40, List.of()));
        priceByItem.put('V', new Item(50, List.of(new Offer(2, 90), new Offer(3, 130))));
        priceByItem.put('W', new Item(20, List.of()));
        priceByItem.put('X', new Item(17, List.of()));
        priceByItem.put('Y', new Item(20, List.of()));
        priceByItem.put('Z', new Item(21, List.of()));

        getNFreeStrategies.add(new GetNFreeStrategy(2, 'E', 1, 'B'));
        getNFreeStrategies.add(new GetNFreeStrategy(3, 'F', 1, 'F'));
        getNFreeStrategies.add(new GetNFreeStrategy(3, 'N', 1, 'M'));
        getNFreeStrategies.add(new GetNFreeStrategy(3, 'R', 1, 'Q'));
        getNFreeStrategies.add(new GetNFreeStrategy(4, 'U', 1, 'U'));

        groupDiscountStrategies.add(new GroupDiscountStrategy(List.of('S', 'T', 'X', 'Y', 'Z'), 3, 45));
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
            if (priceByItem.containsKey(item)) {
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
        for (GroupDiscountStrategy groupDiscountStrategy : groupDiscountStrategies) {
            total += groupDiscountStrategy.apply(productByQuantity, priceByItem);
        }
        for (Map.Entry<Character, Integer> entry : productByQuantity.entrySet()) {
            Character itemName = entry.getKey();
            Integer quantity = entry.getValue();

            Item item = priceByItem.get(itemName);
            total += SpecialOfferStrategy.apply(quantity, item);
        }

        return total;
    }
}

