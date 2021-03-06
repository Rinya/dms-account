package ru.alfastrah.account.sber.backend.sms;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.0.redhat-60024
 * 2018-04-05T18:50:33.549+04:00
 * Generated source version: 2.6.0.redhat-60024
 */
@WebService(targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", name = "MessageGateway")
@XmlSeeAlso({ObjectFactory.class})
public interface MessageGatewayPort {

    /*@RequestWrapper(localName = "SendMessageAsync", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", className = "ru.alfastrah.schemas.interplat4.ws_message_gateway_1.SendMessageAsync")
    @WebMethod(operationName = "SendMessageAsync", action = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0/SendMessageAsync")
    @ResponseWrapper(localName = "SendMessageResponse", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", className = "ru.alfastrah.schemas.interplat4.ws_message_gateway_1.SendMessageResponseType")
    public void sendMessageAsync(
            @WebParam(name = "Message", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    Message message,
            @WebParam(mode = WebParam.Mode.OUT, name = "ResultCode", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<java.lang.String> resultCode,
            @WebParam(mode = WebParam.Mode.OUT, name = "ErrorCode", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<java.lang.String> errorCode,
            @WebParam(mode = WebParam.Mode.OUT, name = "Message", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<java.lang.String> message1
    );*/

    @RequestWrapper(localName = "SendMessage", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", className = "ru.alfastrah.schemas.interplat4.ws_message_gateway_1.SendMessage")
    @WebMethod(operationName = "SendMessage", action = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0/SendMessage")
    @ResponseWrapper(localName = "SendMessageResponse", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", className = "ru.alfastrah.schemas.interplat4.ws_message_gateway_1.SendMessageResponseType")
    public void sendMessage(
            @WebParam(name = "Message", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    Message message,
            @WebParam(mode = WebParam.Mode.OUT, name = "ResultCode", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<String> resultCode,
            @WebParam(mode = WebParam.Mode.OUT, name = "ErrorCode", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<String> errorCode,
            @WebParam(mode = WebParam.Mode.OUT, name = "Message", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<String> message1
    );

    /*@RequestWrapper(localName = "SendMessagesAsync", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", className = "ru.alfastrah.schemas.interplat4.ws_message_gateway_1.SendMessagesAsync")
    @WebMethod(operationName = "SendMessagesAsync", action = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0/SendMessagesAsync")
    @ResponseWrapper(localName = "SendMessageResponse", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", className = "ru.alfastrah.schemas.interplat4.ws_message_gateway_1.SendMessageResponseType")
    public void sendMessagesAsync(
            @WebParam(name = "Message", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    java.util.List<Message> message,
            @WebParam(mode = WebParam.Mode.OUT, name = "ResultCode", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<java.lang.String> resultCode,
            @WebParam(mode = WebParam.Mode.OUT, name = "ErrorCode", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<java.lang.String> errorCode,
            @WebParam(mode = WebParam.Mode.OUT, name = "Message", targetNamespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0")
                    javax.xml.ws.Holder<java.lang.String> message1
    );*/
}
