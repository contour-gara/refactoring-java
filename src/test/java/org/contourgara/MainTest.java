package org.contourgara;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

class MainTest {
  @Mock
  Appender<ILoggingEvent> appender;

  @Captor
  ArgumentCaptor<LoggingEvent> argumentCaptor;

  @Test
  void 文字列がログに出力される() {
    // setup
    MockitoAnnotations.openMocks(this);
    Logger logger = (Logger) LoggerFactory.getLogger(Main.class);
    logger.addAppender(appender);

    // execute
    Main.main(new String[] {});

    // assert
    verify(appender, times(1)).doAppend(argumentCaptor.capture());
    assertThat(argumentCaptor.getValue().getLevel()).hasToString("INFO");
    assertThat(argumentCaptor.getValue().getMessage()).isEqualTo("Hello Refactoring!!!");
  }
}
