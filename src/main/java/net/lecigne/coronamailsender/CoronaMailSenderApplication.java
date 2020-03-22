package net.lecigne.coronamailsender;

import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.service.CoronaMailSender;
import net.lecigne.coronamailsender.wsc.CoronaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class CoronaMailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronaMailSenderApplication.class, args);
	}

}
