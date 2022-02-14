package com.example.demo.kafka;


import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.LocationMapper;
import com.example.demo.model.dto.LocationDto;
import com.example.demo.repository.LocationRepository;



@Service
public class KafkaListeners {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaListener.class);
	
	private LocationMapper mapstructMapper;	
	private LocationRepository locationRepo;
	

	public KafkaListeners(LocationRepository locationRepo,LocationMapper mapstructMapper) {
		this.locationRepo = locationRepo;
		this.mapstructMapper = mapstructMapper;
	}


	@KafkaListener(topics = "gps4")
	public void listener1(List<ConsumerRecord<String,LocationDto>> location) {
		for(ConsumerRecord<String,LocationDto> data : location) {
		log.info("Recived: " + data.value());
		log.info("Topic: " + data.topic());
		log.info("Partition: " + data.partition());
		log.info("Sending to DB...");
		log.info(mapstructMapper.dtoToLocation(data.value()).toString());
		locationRepo.save(mapstructMapper.dtoToLocation(data.value()));
		}
		
		
	}
	
}
