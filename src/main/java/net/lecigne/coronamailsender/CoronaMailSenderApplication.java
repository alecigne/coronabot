package net.lecigne.coronamailsender;

import net.lecigne.coronamailsender.model.CoronaInfo;
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

	@Autowired
	public CoronaMailSenderApplication(CoronaClient coronaClient) {
		this.coronaClient = coronaClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(CoronaMailSenderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CoronaInfo coronaInfo = coronaClient.getCoronaInfo("france");
		System.out.println(coronaInfo.getCases());
	}
}
