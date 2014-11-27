/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Message Bundle.
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
@Component
public class Messages {
    private final Locale defaultLocale = new Locale("de", "DE");

    @Autowired
    private MessageSource messageSource;

    /**
     * Return message by specified code.
     *
     * @param code the code
     * @return the message
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * Return message by specified code.
     *
     * @param code the code
     * @param args Array of arguments that will be filled in for params within the message (params
     *             look like "{0}", "{1,date}", "{2,time}" within a message),
     * @return the message
     */
    public String getMessage(String code, Object[] args) {
        // ween ich das Dokumentation richtig verstanden habe,
        // LocaleContextHolder.getLocale() - gibt die Locale des Betriebssystems zurück.
        // In meinem Fall ist die Betriebssystem russisch :). Es gibt aber keine Property Datei
        // mit russischen Texten. :). Verzeihen Sie mir mein Deutsch, english wäre noch schlimmer :)
        //return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        return messageSource.getMessage(code, args, defaultLocale);
    }

}
