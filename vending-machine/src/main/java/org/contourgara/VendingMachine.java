package org.contourgara;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class VendingMachine {
    private int balance = 0;
    private final Map<DrinkItem, Integer> drinkPricesMap;
    private final Scanner scanner;

    public VendingMachine() {
        drinkPricesMap = Arrays.stream(DrinkItem.values())
            .collect(Collectors.toMap(item -> item, DrinkItem::getPrice));
        scanner = new Scanner(System.in);
    }

    public void execute() {
        while (true) {
            displayWelcomeMessage();
            displayItemsAndBalance();
            displayMenu();

            int choice = getUserChoice();

            switch (choice) {
                case 1 -> insertCoin();
                case 2 -> buyItem();
                case 3 -> {
                    displayExitMessage();
                    return;
                }
                default -> displayInvalidChoiceMessage();
            }
        }
    }

    private void displayWelcomeMessage() {
        System.out.println("自動販売機へようこそ！");
    }

    private void displayItemsAndBalance() {
        System.out.println("商品一覧:");
        drinkPricesMap.forEach((name, price) -> System.out.println(name + " - " + price + "円"));
        System.out.println("現在の投入金額: " + balance + "円");
    }

    private void displayMenu() {
        System.out.println("1. コインを投入する(100円)");
        System.out.println("2. 商品を購入する");
        System.out.println("3. 終了する");
        System.out.print("選択肢を入力してください（1-3）: ");
    }

    private int getUserChoice() {
        return scanner.nextInt();
    }

    private void insertCoin() {
        try {
            insertCoin(Yen._100YEN);
            displayBalance();
        } catch (IllegalArgumentException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    private void displayBalance() {
        System.out.println("現在の投入金額: " + balance + "円");
    }

    private void buyItem() {
        displayItemSelectionPrompt();
        DrinkItem[] items = DrinkItem.values();
        displayItemChoices(items);

        int itemChoice = getUserItemChoice(items);
        if (itemChoice == -1) {
            displayInvalidChoiceMessage();
            return;
        }

        DrinkItem selectedItem = items[itemChoice - 1];

        try {
            String purchasedItem = buy(selectedItem);
            displayPurchaseSuccessMessage(purchasedItem);
            displayBalance();
        } catch (IllegalArgumentException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    private void displayItemSelectionPrompt() {
        System.out.println("購入する商品を選択してください。");
    }

    private void displayItemChoices(DrinkItem[] items) {
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + ". " + items[i].getDisplayName());
        }
        System.out.print("選択肢を入力してください（1-" + items.length + "）: ");
    }

    private int getUserItemChoice(DrinkItem[] items) {
        int itemChoice = scanner.nextInt();
        if (itemChoice < 1 || itemChoice > items.length) {
            return -1;
        }
        return itemChoice;
    }

    private void displayPurchaseSuccessMessage(String purchasedItem) {
        System.out.println(purchasedItem + "を購入しました。");
    }

    private void displayInvalidChoiceMessage() {
        System.out.println("無効な選択肢です。");
    }

    private void displayExitMessage() {
        System.out.println("自動販売機を終了します。ありがとうございました！");
    }

    private void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public void insertCoin(Yen coin) {
        if (!coin.equals(Yen._100YEN)) {
            throw new IllegalArgumentException("100円玉を投入してください");
        }
        balance += coin.value();
    }

    public String buy(DrinkItem item) {
        if (!drinkPricesMap.containsKey(item)) {
            throw new IllegalArgumentException("該当の商品の取り扱いはありません");
        }
        int itemPrice = drinkPricesMap.get(item);
        if (balance < itemPrice) {
            throw new IllegalArgumentException("投入金額が不足しています");
        }
        balance -= itemPrice;
        return item.getDisplayName();
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

    public enum Yen {
        _100YEN(100),
        _10YEN(10);

        private final int value;

        Yen(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }
}
