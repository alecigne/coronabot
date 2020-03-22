package net.lecigne.coronamailsender;

import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.service.CoronaMailSender;
import net.lecigne.coronamailsender.wsc.CoronaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CoronaMailSenderApplication implements CommandLineRunner {

	final CoronaClient coronaClient;

	final CoronaMailSender coronaMailSender;

	@Autowired
	public CoronaMailSenderApplication(CoronaClient coronaClient, CoronaMailSender coronaMailSender) {
		this.coronaClient = coronaClient;
		this.coronaMailSender = coronaMailSender;
	}

	public static void main(String[] args) {
		SpringApplication.run(CoronaMailSenderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CoronaInfo coronaInfo = coronaClient.getCoronaInfo("france");
		coronaMailSender.sendEmail("Informations COVID-19", String.format("Nombre de cas : %d", coronaInfo.getCases()));
	}
}
