package net.lecigne.coronamailsender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@SuppressWarnings("unused") // Used by Spring
public class CoronabotConfig {
    @Bean
    @Validated
    @ConfigurationProperties(prefix = "coronabot.mail")
    public MailConfig mailProperties() {
        return new MailConfig();
    }

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "coronabot.report")
    public ReportConfig reportConfig() {
        return new ReportConfig();
    }
}
