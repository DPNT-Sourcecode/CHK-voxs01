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
    this.offers = offers.stream().sorted(Comparator.comparing(Offer::getQuantity).reversed()).toList();
  }
}


