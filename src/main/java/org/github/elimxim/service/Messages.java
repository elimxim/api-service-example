package org.github.elimxim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Messages {
    private final MessageSource messageSource;

    public String get(String code) {
        return messageSource.getMessage(code, new Object[]{}, Locale.ENGLISH);
    }

    public String get(String code, List<Object> args) {
        return messageSource.getMessage(code, args.toArray(), Locale.ENGLISH);
    }
}
