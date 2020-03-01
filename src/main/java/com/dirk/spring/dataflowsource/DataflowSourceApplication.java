package com.dirk.spring.dataflowsource;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.cloud.stream.annotation.EnableBinding;
        import org.springframework.cloud.stream.messaging.Source;
        import org.springframework.context.annotation.Bean;
        import org.springframework.integration.annotation.InboundChannelAdapter;
        import org.springframework.integration.annotation.Poller;
        import org.springframework.integration.core.MessageSource;
        import org.springframework.messaging.support.MessageBuilder;

        import java.util.List;
        import java.util.stream.Collectors;
        import java.util.stream.Stream;

@SpringBootApplication
@EnableBinding(Source.class)
public class DataflowSourceApplication {

  Logger logger = LoggerFactory.getLogger(DataflowSourceApplication.class);

  @Bean
  @InboundChannelAdapter(
          value = Source.OUTPUT,
          poller = @Poller(fixedDelay = "10000", maxMessagesPerPoll = "1"))
  public MessageSource<List<TheData>> addData() {
    List<TheData> data =
            Stream.of(new TheData(101, "Data1", 100), new TheData(102, "Data2", 200))
                    .collect(Collectors.toList());

    logger.info("data: {}", data);

    return () -> MessageBuilder.withPayload(data).build();
  }

  public static void main(String[] args) {
    SpringApplication.run(DataflowSourceApplication.class, args);
  }
}
