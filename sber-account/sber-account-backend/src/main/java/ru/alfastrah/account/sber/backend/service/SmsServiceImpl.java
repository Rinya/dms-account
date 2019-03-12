package ru.alfastrah.account.sber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.exception.SmsException;
import ru.alfastrah.account.sber.backend.sms.*;

import javax.xml.ws.Holder;

@Service
public class SmsServiceImpl implements SmsService, HasLogger {
    private MessageGatewayPort port;

    @Autowired
    public SmsServiceImpl(@Lazy MessageGatewayPort port) {
        this.port = port;
    }

    public void sendSms(String phone, String sms) throws SmsException {
        getLogger().debug("Enter to sendSms");
        Message message = new Message();
        message.setFrom("SAP");

        message.setEndpoints(createEndpoints(phone));
        message.setBody(createBody(sms));

        Holder<String> resultCode = new Holder<>();
        Holder<String> errorCode = new Holder<>();
        Holder<String> message1 = new Holder<>();
        port.sendMessage(message, resultCode, errorCode, message1);

        if (!"OK".equals(resultCode.value)) {
            throw new SmsException(errorCode.value + ": " + message1.value);
        }
    }

    private Body createBody(String message) {
        Body body = new Body();
        body.setTextMessage(message);
        return body;
    }

    private Message.Endpoints createEndpoints(String phone) {
        Message.Endpoints endpoints = new Message.Endpoints();
        Endpoint endpoint = new Endpoint();
        endpoint.setType(EndpointType.SMS);
        endpoint.setAddress(phone);
        endpoints.getEndpoint().add(endpoint);

        return endpoints;
    }
}
