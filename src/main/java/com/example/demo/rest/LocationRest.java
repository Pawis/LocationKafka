package com.example.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.kafka.KafkaProducer;
import com.example.demo.model.dto.LocationDto;



@RestController
@RequestMapping("/kafka")
public class LocationRest {

	private static final Logger log = LoggerFactory.getLogger(LocationRest.class);

	@Qualifier("locationKafkaTemplate")
	private KafkaProducer kafkaProducer;

	public LocationRest(KafkaProducer kafkaProducer) {
		this.kafkaProducer= kafkaProducer;

	}

	@PostMapping("/sendLocation")
	public void sendLocationDto(@RequestBody LocationDto location) {

		//log.info("Sending location...");
		log.info(String.format("Producing message ->" + location.toString()));
		kafkaProducer.sendMessage(location,location.getDeviceId());

	}

}