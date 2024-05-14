package org.contourgara;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class VendingMachine {

    private final Map<String, Integer> map; // Mysterious Name: 変数名が曖昧
    private final Scanner scanner;
    private int a = 0; // Mysterious Name: 変数名が曖昧

    private boolean enableNetworking = false; // Lazy Element: 実際には使用されていない

    public VendingMachine() {
        map = Arrays.stream(DrinkItem.values())
            .collect(Collectors.toMap(item -> item.getDisplayName(), DrinkItem::getPrice));
        scanner = new Scanner(System.in);
        setupNetworking();
    }

    // Lazy Element: 将来のために実装したが現在は何も行われていない。
    private void setupNetworking() {
        if (enableNetworking) {
            log.info("ネットワーク機能が有効です");
        }
    }

    // Long Function: 長い関数 & 深いネスト
    public void execute() {
        log.info("自動販売機へようこそ！");

        while (true) {
            log.info("商品一覧:");
            map.forEach((name, price) -> log.info(name + " - " + price + "円"));
            log.info("現在の投入金額: " + a + "円");

            log.info("1. コインを投入する(100円)");
            log.info("2. 商品を購入する");
            log.info("3. 終了する");
            log.info("--- 選択肢を入力してください（1-3）: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                insert(100); // Mysterious Name: メソッド名が曖昧
            } else if (choice == 2) {
                select(); // Mysterious Name: メソッド名が曖昧
            } else if (choice == 3) {
                log.info("--- 自動販売機を終了します。ありがとうございました！ ---");
                return;
            } else {
                log.info("--- 無効な選択肢です。--- ");
            }
        }
    }

    public void insert(int coin) {
        try {
            // Primitive Obsession: プリミティブ型の乱用
            if (coin != 100) {
                throw new IllegalArgumentException("--- 100円玉を投入してください ---");
            }
            a += coin;
            log.info("現在の投入金額: " + a + "円"); // Duplicated Code: 重複したコード
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void select() {
        log.info("--- 購入する商品を選択してください。 ---");
        DrinkItem[] items = DrinkItem.values();
        for (int i = 0; i < items.length; i++) { // Loops: ループの使用
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
            log.info("現在の投入金額: " + a + "円"); // Duplicated Code: 重複したコード
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String buy(String item) {
        if (!map.containsKey(item)) {
            throw new IllegalArgumentException("--- 該当の商品の取り扱いはありません ---");
        }
        int itemPrice = map.get(item);
        if (a < itemPrice) {
            throw new IllegalArgumentException("--- 投入金額が不足しています ---");
        }
        a -= itemPrice;
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
