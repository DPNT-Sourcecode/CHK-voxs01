package befaster.solutions.CHK;

import lombok.Value;

import java.util.List;

@Value
public class Item {
  int price;
  List<Offer> offers;
}

