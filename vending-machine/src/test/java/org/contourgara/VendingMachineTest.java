package org.contourgara;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class VendingMachineTest {
  @Test
  void 文字列を返す() {
    // setup
    VendingMachine sut = new VendingMachine();

    // execute
    String actual = sut.execute();

    // assert
    String expected = "Hello Refactoring!!!";
    assertThat(actual).isEqualTo(expected);
  }
}
