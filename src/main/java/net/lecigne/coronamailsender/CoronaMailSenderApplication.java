package net.lecigne.coronamailsender;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.service.CoronaMailSender;
import net.lecigne.coronamailsender.wsc.CoronaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.mail.MessagingException;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@Slf4j
public class CoronaMailSenderApplication implements CommandLineRunner {

	@Autowired
	CoronaMailSender coronaMailSender;

	public static void main(String[] args) {
		SpringApplication.run(CoronaMailSenderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			coronaMailSender.sendEmail("Coronabot", String.format("Coronabot is up and running!"));
			log.info("Startup email sent successfully");
		} catch (MessagingException e) {
			log.error("Error sending startup email");
		}
	}
}
