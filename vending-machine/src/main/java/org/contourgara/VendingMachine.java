package org.contourgara;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class VendingMachine {

    private final Map<String, Integer> mmapDrink; // 不可思議な名前
    private final Scanner scanner;
    private int miTotalAmount = 0; // 不可思議な名前

    public VendingMachine() {
        mmapDrink = Arrays.stream(DrinkItem.values())
            .collect(Collectors.toMap(item -> item.getDisplayName(), DrinkItem::getPrice));
        scanner = new Scanner(System.in);
    }

    // 長い関数
    public void execute() {
        log.info("自動販売機へようこそ！");

        while (true) {
            log.info("商品一覧:");
            mmapDrink.forEach((name, price) -> log.info(name + " - " + price + "円"));
            log.info("現在の投入金額: " + miTotalAmount + "円");
            printInitialMessage();

            int choice = scanner.nextInt();
            if (choice == 3) {
                log.info("--- 自動販売機を終了します。ありがとうございました！ ---");
                return;
            }
            actionByChoice(choice);
        }
    }

    private void printInitialMessage(){
        log.info("1. コインを投入する(100円)");
        log.info("2. 商品を購入する");
        log.info("3. 終了する");
        log.info("--- 選択肢を入力してください（1-3）: ");
    }

    private void actionByChoice(int choice) {
        if (choice == 1) {
            insertCoin(100); // 不可思議な名前
        } else if (choice == 2) {
            selectDrink(); // 不可思議な名前
        } else {
            log.info("--- 無効な選択肢です。--- ");
        }
    }

    public void insertCoin(int coin) {
        try {
            // 基本データ型への執着
            if (coin != 100) {
                throw new IllegalArgumentException("--- 100円玉を投入してください ---");
            }
            miTotalAmount += coin;
            log.info("現在の投入金額: " + miTotalAmount + "円"); // 重複したコード
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void selectDrink() {
        log.info("--- 購入する商品を選択してください。 ---");
        DrinkItem[] items = DrinkItem.values();
        for (int i = 0; i < items.length; i++) { // ループ
            log.info((i + 1) + ". " + items[i].getDisplayName());
        }
        log.info("--- 選択肢を入力してください（1-" + items.length + "）: ");

        int itemChoice = scanner.nextInt();
        if (itemChoice < 1 || itemChoice > items.length) {
            log.info("--- 無効な選択肢です。--- ");
            return;
        }

        DrinkItem selectedItem = items[itemChoice - 1];

        try {
            String purchasedItem = buy(selectedItem.getDisplayName());
            log.info("--- " + purchasedItem + "を購入しました。 ---");
            log.info("現在の投入金額: " + miTotalAmount + "円"); // 重複したコード
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
        }
    }

    public String buy(String item) {
        if (!mmapDrink.containsKey(item)) {
            throw new IllegalArgumentException("--- 該当の商品の取り扱いはありません ---");
        }
        int itemPrice = mmapDrink.get(item);
        if (miTotalAmount < itemPrice) {
            throw new IllegalArgumentException("--- 投入金額が不足しています ---");
        }
        miTotalAmount -= itemPrice;
        return item;
    }

    public enum DrinkItem {
        COLA("コーラ", 100),
        OOLONG_TEA("ウーロン茶", 100),
        REDBULL("レッドブル", 200);

        private final String displayName;
        private final int price;

        DrinkItem(String displayName, int price) {
            this.displayName = displayName;
            this.price = price;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getPrice() {
            return price;
        }
    }
}
