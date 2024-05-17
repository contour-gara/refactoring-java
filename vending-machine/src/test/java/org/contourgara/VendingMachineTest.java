package org.contourgara;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class VendingMachineTest {
    @Nested
    class 購入成功 {
        @Test
        void _100円を投入してウーロン茶を購入() {
            // setup
            VendingMachine sut = new VendingMachine();
            sut.insertCoin(100);
            String expected = "ウーロン茶";

            // execute
            String actual = sut.buy("ウーロン茶");

            // assert
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void _200円を投入してレッドブルを購入() {
            // setup
            VendingMachine sut = new VendingMachine();
            sut.insertCoin(100);
            sut.insertCoin(100);
            String expected = "レッドブル";

            // execute
            String actual = sut.buy("レッドブル");

            // assert
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class 購入失敗 {
        @Test
        void _100円以外のコインを投入() {
            // setup
            VendingMachine sut = new VendingMachine();

            // assert
            assertThatThrownBy(() -> sut.insertCoin(10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("--- 100円玉を投入してください ---");
        }

        @Test
        void 投入金額不足() {
            // setup
            VendingMachine sut = new VendingMachine();

            // assert
            assertThatThrownBy(() -> sut.buy("ウーロン茶"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("--- 投入金額が不足しています ---");
        }

        @Test
        void 存在しない商品を選択() {
            // setup
            VendingMachine sut = new VendingMachine();
            sut.insertCoin(100);

            // assert
            assertThatThrownBy(() -> sut.buy(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("--- 該当の商品の取り扱いはありません ---");
        }
    }
}
