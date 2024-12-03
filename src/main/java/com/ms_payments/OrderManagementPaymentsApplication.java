package com.ms_payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableFeignClients
@EnableJpaAuditing
@EnableMongoAuditing
@SpringBootApplication
public class OrderManagementPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementPaymentsApplication.class, args);
	}

}
