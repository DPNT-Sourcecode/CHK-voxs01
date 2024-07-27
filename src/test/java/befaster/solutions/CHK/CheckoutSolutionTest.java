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
  void shouldReturnMinusOne_IfStringIsInvalid() {
    assertThat(solution.checkout(null)).isEqualTo(-1);
    assertThat(solution.checkout("-")).isEqualTo(-1);
    assertThat(solution.checkout("a")).isEqualTo(-1);
    assertThat(solution.checkout("AxA")).isEqualTo(-1);
    assertThat(solution.checkout("ABCa")).isEqualTo(-1);
    assertThat(solution.checkout("ABCDEFG")).isEqualTo(-1);
  }

  @Test
  void shouldReturnZero_StringIsEmpty() {
    assertThat(solution.checkout("")).isZero();
  }

  @Test
  void shouldReturnTheTotalValue() {
    assertThat(solution.checkout("ABCDE")).isEqualTo(50 + 30 + 20 + 15 + 40);
  }

  @Test
  void shouldReturnTheTotalValue_SpecialOffers() {
    assertThat(solution.checkout("AAAAABBCDE")).isEqualTo(200 + 45 + 20 + 15 + 40);
  }

  @Test
  void shouldReturnTheTotalValue_MultipleSpecialOffers_SameProduct_BestOffers() {
    assertThat(solution.checkout("AAAAAAAAA")).isEqualTo(200 + 130 + 50);
  }

  @Test
  void shouldReturnTheTotalValue_ReceiveFreeB() {
    assertThat(solution.checkout("BBEEEEE")).isEqualTo(40 * 5);
    assertThat(solution.checkout("BBBBEEEEE")).isEqualTo(40 * 5 + 45);
    assertThat(solution.checkout("EE")).isEqualTo(40 * 2);
    assertThat(solution.checkout("EEB")).isEqualTo(40 * 2);
    assertThat(solution.checkout("EEEB")).isEqualTo(40 * 3);
  }
}

