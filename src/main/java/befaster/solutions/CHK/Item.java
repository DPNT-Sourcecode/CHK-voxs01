package befaster.solutions.CHK;

import lombok.Value;

import java.util.Comparator;
import java.util.List;

@Value
public class Item {
  int price;
  List<Offer> offers;

  public Item(int price, List<Offer> offers) {
    this.price = price;
    // We need to store the offer with the highest discountQuantity, since it will have the best balance
    this.offers = offers.stream().sorted(Comparator.comparing(Offer::getQuantity).reversed()).toList();
  }
}
