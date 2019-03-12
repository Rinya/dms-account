package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.exception.SmsException;

import java.io.Serializable;

public interface SmsService extends Serializable {
    void sendSms(String phone, String sms) throws SmsException;
}
