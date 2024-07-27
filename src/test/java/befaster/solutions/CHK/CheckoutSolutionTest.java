package befaster.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutSolutionTest {

  private CheckoutSolution solution;

  @BeforeEach
  void setup() {
    solution = new CheckoutSolution();
  }

  @Test
  void shouldReturnMinusOne_IfStringIsNull() {
    assertThat(solution.checkout(null)).isEqualTo(-1);
  }

  @Test
  void shouldReturnMinusOne_StringContainsAnythingOtherThanExistingProducts() {
    assertThat(solution.checkout("-")).isEqualTo(-1);
    assertThat(solution.checkout("a")).isEqualTo(-1);
    assertThat(solution.checkout("AxA")).isEqualTo(-1);
    assertThat(solution.checkout("ABCa")).isEqualTo(-1);
  }

  @Test
  void shouldReturnZero_StringIsEmpty() {
    assertThat(solution.checkout("")).isZero();
  }

  @Test
  void shouldReturnTheTotalValue() {
    assertThat(solution.checkout("ABCDEFZ")).isEqualTo(50 + 30 + 20 + 15 + 40 + 10 + 50);
  }

  @Test
  void shouldReturnTheTotalValue_SpecialOffers() {
    assertThat(solution.checkout("AAAAABBCDEF")).isEqualTo(200 + 45 + 20 + 15 + 40 + 10);
  }

  @Test
  void shouldReturnTheTotalValue_MultipleSpecialOffers_SameProduct_BestOffers() {
    assertThat(solution.checkout("AAAAAAAAA")).isEqualTo(200 + 130 + 50);
  }

  @Test
  void forTwoEBought_ReceiveOneBFree() {
    assertThat(solution.checkout("BBEEEEE")).isEqualTo(40 * 5);
    assertThat(solution.checkout("BBBBEEEEE")).isEqualTo(40 * 5 + 45);
    assertThat(solution.checkout("EE")).isEqualTo(40 * 2);
    assertThat(solution.checkout("EEB")).isEqualTo(40 * 2);
    assertThat(solution.checkout("EEEB")).isEqualTo(40 * 3);
  }

  @Test
  void forTwoFBought_ReceiveOneFFree() {
    assertThat(solution.checkout("FFFF")).isEqualTo(10 * 3);
    assertThat(solution.checkout("FFFFFF")).isEqualTo(10 * 4);
    assertThat(solution.checkout("FF")).isEqualTo(20);
  }

  @Test
  void bothTypes_BuyNGetMFree() {
    // For E and F
    assertThat(solution.checkout("FFFFEEB")).isEqualTo(10 * 3 + 40 * 2);
    assertThat(solution.checkout("FFFFEEEB")).isEqualTo(10 * 3 + 40 * 3);
  }

  @Test
  void groupDiscounts_OnlyApplyingDiscounts_NoRemnants() {
    assertThat(solution.checkout("SSSTTTXXXYYYZZZ")).isEqualTo(45 * 5);
    assertThat(solution.checkout("XXXXZZ")).isEqualTo(45 * 2);
    assertThat(solution.checkout("XXXXZZAAABB")).isEqualTo(45 * 2 + 130 + 45);
  }

  @Test
  void groupDiscounts_ApplyDiscounts_WithRemnants() {
    assertThat(solution.checkout("ZZYYTTSSXX")).isEqualTo(45 * 3 + 17);
    assertThat(solution.checkout("ZXYTSZXYTS")).isEqualTo(45 * 3 + 17);
  }
}

