package org.contourgara;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    // FIXME: AT パスのための仮実装
    log.info("自動販売機へようこそ！");

    VendingMachine vendingMachine = new VendingMachine();
    vendingMachine.execute();
  }
}
