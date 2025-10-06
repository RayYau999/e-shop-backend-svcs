//package com.rayyau.eshop.pymt.service;
//
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.KTable;
//import org.apache.kafka.streams.kstream.Produced;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.Properties;
//import java.util.UUID;
//
//@Service
//@Slf4j
//public class KafkaStreamsService {
//    @PostConstruct
//    public void startKafkaStreams() {
//        log.info("KafkaStreamsService :: starting kafka streams");
//        StreamsBuilder builder = new StreamsBuilder();
//        KStream<String, String> textLines = builder.stream("quickstart-events");
//
//        KTable<String, Long> wordCounts = textLines
//                .flatMapValues(line -> Arrays.asList(line.toLowerCase().split(" ")))
//                .groupBy((keyIgnored, word) -> word)
//                .count();
//
//        wordCounts.toStream().to("output-topic", Produced.with(Serdes.String(), Serdes.Long()));
//
//        Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-kafka-app-" + UUID.randomUUID());
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//
//        KafkaStreams streams = new KafkaStreams(builder.build(), props);
//        streams.start();
//    }
//}
