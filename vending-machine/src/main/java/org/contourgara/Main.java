package org.contourgara;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    VendingMachine vendingMachine = new VendingMachine();
    vendingMachine.execute();
  }
}
