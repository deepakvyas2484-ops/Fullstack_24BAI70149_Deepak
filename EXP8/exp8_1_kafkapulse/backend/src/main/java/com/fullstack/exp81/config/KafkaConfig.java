package com.fullstack.exp81.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
@Configuration public class KafkaConfig {
 @Bean NewTopic mainTopic(@Value("${app.kafka.main-topic}") String t){return TopicBuilder.name(t).partitions(1).replicas(1).build();}
 @Bean NewTopic dlqTopic(@Value("${app.kafka.dlq-topic}") String t){return TopicBuilder.name(t).partitions(1).replicas(1).build();}
 @Bean DeadLetterPublishingRecoverer recoverer(KafkaTemplate<String,String> template,@Value("${app.kafka.dlq-topic}") String dlq){
  return new DeadLetterPublishingRecoverer(template,(record,ex)->new TopicPartition(dlq,record.partition()));
 }
 @Bean DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer){
  ExponentialBackOffWithMaxRetries b=new ExponentialBackOffWithMaxRetries(3);b.setInitialInterval(1000L);b.setMultiplier(2.0);b.setMaxInterval(4000L);
  return new DefaultErrorHandler(recoverer,b);
 }
}