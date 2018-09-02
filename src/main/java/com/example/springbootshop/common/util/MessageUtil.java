package com.example.springbootshop.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class MessageUtil {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() { accessor = new MessageSourceAccessor(messageSource, Locale.KOREA); }

    public String getMessage(String code) {
        return accessor.getMessage(code);
    }
}
