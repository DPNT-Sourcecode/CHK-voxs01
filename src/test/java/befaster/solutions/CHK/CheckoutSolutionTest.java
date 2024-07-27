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
  }

  @Test
  void shouldReturnZero_StringIsEmpty() {
    assertThat(solution.checkout("")).isZero();
  }

  @Test
  void shouldReturnTheTotalValue() {
    assertThat(solution.checkout("ABCD")).isEqualTo(50 + 30 + 20 + 15);
  }

  @Test
  void shouldReturnTheTotalValue_SpecialOffers() {
    assertThat(solution.checkout("AAABBCD")).isEqualTo(130 + 45 + 20 + 15);
  }

  @Test
  void shouldReturnTheTotalValue_MultipleSpecialOffers_SameProduct() {
    assertThat(solution.checkout("AAAAAAAA")).isEqualTo(130 * 2 + 50 * 2);
  }
}