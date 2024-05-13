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

  @Test
  void 文字列がログに出力される() {
    // setup
    MockitoAnnotations.openMocks(this);
    Logger logger = (Logger) LoggerFactory.getLogger(VendingMachine.class);
    logger.addAppender(appender);

    // execute
    standardInputStream.inputln("3");
    Main.main(new String[] {});

    // assert
    verify(appender, times(11)).doAppend(argumentCaptor.capture());
    assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getLevel).map(Level::toString)).containsOnly("INFO");
    assertThat(argumentCaptor.getAllValues().get(0).getMessage()).isEqualTo("自動販売機へようこそ！");
    assertThat(argumentCaptor.getAllValues().get(10).getMessage()).isEqualTo("--- 自動販売機を終了します。ありがとうございました！ ---");
    assertThat(argumentCaptor.getAllValues().stream().map(LoggingEvent::getMessage)).contains("商品一覧:");
  }
}
