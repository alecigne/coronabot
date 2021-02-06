package net.lecigne.coronamailsender.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A basic email.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Email {
    private String from;
    private String[] to;
    private String subject;
    private String text;
}
