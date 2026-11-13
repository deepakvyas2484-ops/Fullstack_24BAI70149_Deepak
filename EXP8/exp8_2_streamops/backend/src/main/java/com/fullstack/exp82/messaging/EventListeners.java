package com.fullstack.exp82.messaging;
import com.fullstack.exp82.service.EventService;import org.springframework.kafka.annotation.KafkaListener;import org.springframework.stereotype.Component;import com.fasterxml.jackson.databind.ObjectMapper;import java.util.Map;
@Component public class EventListeners{
 private final EventService service;private final ObjectMapper mapper;public EventListeners(EventService s,ObjectMapper m){service=s;mapper=m;}
 @KafkaListener(topics="${app.kafka.main-topic}",groupId="${app.kafka.consumer-group}") public void main(String json)throws Exception{service.process(mapper.readValue(json,Map.class));}
 @KafkaListener(topics="${app.kafka.dlq-topic}",groupId="${app.kafka.consumer-group}-dlq") public void dlq(String json)throws Exception{service.markDlq(mapper.readValue(json,Map.class));}
}