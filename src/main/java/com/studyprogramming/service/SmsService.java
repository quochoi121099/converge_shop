package com.studyprogramming.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                message).create();
    }
}

