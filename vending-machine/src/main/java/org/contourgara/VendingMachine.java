package org.contourgara;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 自動販売機のクラスです。 飲み物の購入、コインの投入、現在の残高表示などの機能を提供します。
 */
@Slf4j
public class VendingMachine {

    private final Map<DrinkItem, Integer> drinkItemMap; // 不可思議な名前 -> 格納データの中身が理解できる命名
    private final Scanner scanner;
    private int balance = 0; // 不可思議な名前 -> 変数の用途が理解できる命名

    /**
     * コンストラクタ。自動販売機を初期化します。
     */
    public VendingMachine() {
        drinkItemMap = Arrays.stream(DrinkItem.values())
            .collect(Collectors.toMap(item -> item, DrinkItem::getPrice));
        scanner = new Scanner(System.in);
    }

    /**
     * 自動販売機の操作を開始します。
     */
    public void execute() {
        displayWelcomeMessage(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。

        while (true) {
            displayItemsAndBalance(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
            displayMenu(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。

            int choice = getUserChoice();

            switch (choice) {
                case 1 ->
                    insertCoin(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。&  不可思議な名前 -> メソッドの処理が伝わる命名。
                case 2 -> buyItem(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。 & 不可思議な名前 -> メソッドの処理が伝わる命名。
                case 3 -> {
                    displayExitMessage(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
                    return;
                }
                default -> displayInvalidChoiceMessage(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
            }
        }
    }

    private void displayWelcomeMessage() {
        log.info("自動販売機へようこそ！");
    }

    private void displayItemsAndBalance() {
        log.info("商品一覧:");
        drinkItemMap.forEach((name, price) -> log.info(name + " - " + price + "円"));
        log.info("現在の投入金額: " + balance + "円");
    }

    private void displayMenu() {
        log.info("1. コインを投入する(100円)");
        log.info("2. 商品を購入する");
        log.info("3. 終了する");
        log.info("--- 選択肢を入力してください（1-3）: ");
    }

    private int getUserChoice() {
        return scanner.nextInt();
    }

    private void insertCoin() {
        try {
            insertCoin(Yen._100YEN); // 基本データ型への執着 -> オブジェクトによるプリミティブの置き換え
            displayBalance(); // 重複したコード -> メソッドの外出しによる共通化。
        } catch (IllegalArgumentException e) {
            displayErrorMessage(e.getMessage()); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
        }
    }

    private void displayBalance() {
        log.info("現在の投入金額: " + balance + "円");
    }

    private void buyItem() {
        displayItemSelectionPrompt(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
        DrinkItem[] items = DrinkItem.values();
        displayItemChoices(items); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。

        int itemChoice = getUserItemChoice(items); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
        if (itemChoice == -1) {
            displayInvalidChoiceMessage(); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
            return;
        }

        DrinkItem selectedItem = items[itemChoice - 1];

        try {
            String purchasedItem = buy(selectedItem); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
            displayPurchaseSuccessMessage(purchasedItem); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
            displayBalance(); // 重複したコード -> メソッドの外出しによる共通化。
        } catch (IllegalArgumentException e) {
            displayErrorMessage(e.getMessage()); // 長い関数 -> 意味的にひとかたまりの処理をメソッドに外だし。
        }
    }

    private void displayItemSelectionPrompt() {
        log.info("--- 購入する商品を選択してください。 ---");
    }

    private void displayItemChoices(DrinkItem[] items) {
        IntStream.range(0, items.length)
            .forEach(i -> log.info((i + 1) + ". " + items[i].getDisplayName())); // ループ -> パイプラインによるループの置き換え
        log.info("--- 選択肢を入力してください（1-" + items.length + "）: ");
    }

    private int getUserItemChoice(DrinkItem[] items) {
        int itemChoice = scanner.nextInt();
        if (itemChoice < 1 || itemChoice > items.length) {
            return -1;
        }
        return itemChoice;
    }

    private void displayPurchaseSuccessMessage(String purchasedItem) {
        log.info("--- " + purchasedItem + "を購入しました。 ---");
    }

    private void displayInvalidChoiceMessage() {
        log.info("--- 無効な選択肢です。--- ");
    }

    private void displayExitMessage() {
        log.info("--- 自動販売機を終了します。ありがとうございました！ ---");
    }

    private void displayErrorMessage(String message) {
        log.info(message);
    }

    /**
     * コインを投入するメソッドです。
     *
     * @param coin 投入するコイン
     * @throws IllegalArgumentException 100円以外のコインが投入された場合
     */
    public void insertCoin(Yen coin) {
        if (!coin.equals(Yen._100YEN)) {
            throw new IllegalArgumentException("--- 100円玉を投入してください ---");
        }
        balance += coin.value();
    }

    /**
     * 飲み物を購入するメソッドです。
     *
     * @param item 購入する飲み物
     * @return 購入した飲み物の名前
     * @throws IllegalArgumentException 商品が存在しない、または残高が不足している場合
     */
    public String buy(DrinkItem item) {
        if (!drinkItemMap.containsKey(item)) {
            throw new IllegalArgumentException("--- 該当の商品の取り扱いはありません ---");
        }
        int itemPrice = drinkItemMap.get(item);
        if (balance < itemPrice) {
            throw new IllegalArgumentException("--- 投入金額が不足しています ---");
        }
        balance -= itemPrice;
        return item.getDisplayName();
    }

    /**
     * 飲み物のアイテムを表す列挙型です。
     */
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

        /**
         * 飲み物の表示名を取得します。
         *
         * @return 飲み物の表示名
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * 飲み物の価格を取得します。
         *
         * @return 飲み物の価格
         */
        public int getPrice() {
            return price;
        }
    }

    // 基本データ型への執着 -> オブジェクトによるプリミティブの置き換え
    /**
     * 円のコインを表す列挙型です。
     */
    public enum Yen {
        _100YEN(100),
        _10YEN(10);

        private final int value;

        Yen(int value) {
            this.value = value;
        }

        /**
         * コインの価値を取得します。
         *
         * @return コインの価値
         */
        public int value() {
            return value;
        }
    }
}
