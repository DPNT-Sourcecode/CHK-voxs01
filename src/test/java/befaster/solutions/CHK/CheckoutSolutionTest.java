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
  void shouldReturnZero_IfNullOrEmpty() {
    assertThat(solution.checkout(null)).isZero();
    assertThat(solution.checkout("")).isZero();
  }

  @Test
  void shouldReturnTheTotalValue() {
    assertThat(solution.checkout("ABCD")).isEqualTo(50 + 30 + 20 + 15);
  }
}