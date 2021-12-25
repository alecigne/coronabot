package net.lecigne.coronamailsender.config;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReportConfig {
    @NotBlank private String country;
    @NotBlank private String syncCron;
    @NotBlank private String mailCron;
}
