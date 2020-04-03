package net.lecigne.coronamailsender.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mail {

    private String from;

    private String[] to;

    private String subject;

    private String text;

}
