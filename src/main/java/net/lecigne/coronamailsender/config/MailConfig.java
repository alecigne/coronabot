package net.lecigne.coronamailsender.config;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class MailConfig {
    @NotBlank private String from;
    @NotEmpty private List<@Email String> to;
    @NotBlank private String subject;
}
