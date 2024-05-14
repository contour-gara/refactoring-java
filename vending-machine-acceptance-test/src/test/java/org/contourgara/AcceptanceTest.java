package org.contourgara;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.contourgara.util.StandardInputStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

class AcceptanceTest {
  static StandardInputStream standardInputStream = new StandardInputStream();

  @Mock
  Appender<ILoggingEvent> appender;

  @Captor
  ArgumentCaptor<LoggingEvent> argumentCaptor;

   @BeforeAll
   static void setUpAll() {
     System.setIn(standardInputStream);
   }

   @AfterAll
   static void tearDownAll() {
     System.setIn(null);
   }

   @BeforeEach
   void setUp() {
     MockitoAnnotations.openMocks(this);
     Logger logger = (Logger) LoggerFactory.getLogger(VendingMachine.class);
     logger.addAppender(appender);
   }

   @Test
   void 自販機を起動し終了する() {
     // set up
     standardInputStream.inputln("3");

     // execute
     Main.main(null);

     // assert
     verify(appender, times(11)).doAppend(argumentCaptor.capture());
     assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getLevel).map(Level::toString)).containsOnly("INFO");
     assertThat(argumentCaptor.getAllValues().get(0).getMessage()).isEqualTo("自動販売機へようこそ！");
     assertThat(argumentCaptor.getAllValues().get(5).getMessage()).isEqualTo("現在の投入金額: 0円");
     assertThat(argumentCaptor.getAllValues().get(10).getMessage()).isEqualTo("--- 自動販売機を終了します。ありがとうございました！ ---");
   }

   @Test
   void コインを1度投入しコーラを買うことができ投入金額が0になる() {
     // set up
     standardInputStream.inputln("1");
     standardInputStream.inputln("2");
     standardInputStream.inputln("1");
     standardInputStream.inputln("3");

     // execute
     Main.main(null);

     // assert
     verify(appender, times(37)).doAppend(argumentCaptor.capture());
     assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getLevel).map(Level::toString)).containsOnly("INFO");
     assertThat(argumentCaptor.getAllValues().get(10).getMessage()).isEqualTo("現在の投入金額: 100円");
     assertThat(argumentCaptor.getAllValues().get(25).getMessage()).isEqualTo("--- コーラを購入しました。 ---");
     assertThat(argumentCaptor.getAllValues().get(26).getMessage()).isEqualTo("現在の投入金額: 0円");
   }

  @Test
  void コインを1度投入しウーロン茶を買うことができ投入金額が0になる() {
    // set up
    standardInputStream.inputln("1");
    standardInputStream.inputln("2");
    standardInputStream.inputln("2");
    standardInputStream.inputln("3");

    // execute
    Main.main(null);

    // assert
    verify(appender, times(37)).doAppend(argumentCaptor.capture());
    assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getLevel).map(Level::toString)).containsOnly("INFO");
    assertThat(argumentCaptor.getAllValues().get(10).getMessage()).isEqualTo("現在の投入金額: 100円");
    assertThat(argumentCaptor.getAllValues().get(25).getMessage()).isEqualTo("--- ウーロン茶を購入しました。 ---");
    assertThat(argumentCaptor.getAllValues().get(26).getMessage()).isEqualTo("現在の投入金額: 0円");
  }

  @Test
  void コインを1度投入しレッドブルを買うことができず投入金額が100になる() {
    // set up
    standardInputStream.inputln("1");
    standardInputStream.inputln("2");
    standardInputStream.inputln("3");
    standardInputStream.inputln("3");

    // execute
    Main.main(null);

    // assert
    verify(appender, times(36)).doAppend(argumentCaptor.capture());
    assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getLevel).map(Level::toString)).containsOnly("INFO");
    assertThat(argumentCaptor.getAllValues().get(10).getMessage()).isEqualTo("現在の投入金額: 100円");
    assertThat(argumentCaptor.getAllValues().get(20).getMessage()).isEqualTo("--- 購入する商品を選択してください。 ---");
    assertThat(argumentCaptor.getAllValues().get(25).getMessage()).isEqualTo("--- 投入金額が不足しています ---");
    assertThat(argumentCaptor.getAllValues().get(30).getMessage()).isEqualTo("現在の投入金額: 100円");
  }

  @Test
  void コインを2度投入しコーラを買うことができ投入金額が100になる() {
    // set up
    standardInputStream.inputln("1");
    standardInputStream.inputln("1");
    standardInputStream.inputln("2");
    standardInputStream.inputln("1");
    standardInputStream.inputln("3");

    // execute
    Main.main(null);

    // assert
    verify(appender, times(47)).doAppend(argumentCaptor.capture());
    assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getLevel).map(Level::toString)).containsOnly("INFO");
    assertThat(argumentCaptor.getAllValues().get(10).getMessage()).isEqualTo("現在の投入金額: 100円");
    assertThat(argumentCaptor.getAllValues().get(25).getMessage()).isEqualTo("現在の投入金額: 200円");
    assertThat(argumentCaptor.getAllValues().get(30).getMessage()).isEqualTo("--- 購入する商品を選択してください。 ---");
    assertThat(argumentCaptor.getAllValues().get(35).getMessage()).isEqualTo("--- コーラを購入しました。 ---");
    assertThat(argumentCaptor.getAllValues().get(36).getMessage()).isEqualTo("現在の投入金額: 100円");
  }

  @Test
  void コインを2度投入しレッドブルを買うことができ投入金額が0になる() {
    // set up
    standardInputStream.inputln("1");
    standardInputStream.inputln("1");
    standardInputStream.inputln("2");
    standardInputStream.inputln("3");
    standardInputStream.inputln("3");

    // execute
    Main.main(null);

    // assert
    verify(appender, times(47)).doAppend(argumentCaptor.capture());
    assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getLevel).map(Level::toString)).containsOnly("INFO");
    assertThat(argumentCaptor.getAllValues().get(10).getMessage()).isEqualTo("現在の投入金額: 100円");
    assertThat(argumentCaptor.getAllValues().get(25).getMessage()).isEqualTo("現在の投入金額: 200円");
    assertThat(argumentCaptor.getAllValues().get(30).getMessage()).isEqualTo("--- 購入する商品を選択してください。 ---");
    assertThat(argumentCaptor.getAllValues().get(35).getMessage()).isEqualTo("--- レッドブルを購入しました。 ---");
    assertThat(argumentCaptor.getAllValues().get(36).getMessage()).isEqualTo("現在の投入金額: 0円");
  }
}
