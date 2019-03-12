
package ru.alfastrah.account.sber.backend.sms;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the ru.alfastrah.schemas.interplat4.ws_message_gateway_1 package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendMessageResponse_QNAME = new QName("http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", "SendMessageResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.alfastrah.schemas.interplat4.ws_message_gateway_1
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Body }
     */
    public Body createBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link Message }
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link SendMessageAsync }
     */
    /*public SendMessageAsync createSendMessageAsync() {
        return new SendMessageAsync();
    }*/

    /**
     * Create an instance of {@link SendMessage }
     */
    public SendMessage createSendMessage() {
        return new SendMessage();
    }

    /**
     * Create an instance of {@link SendMessageResponseType }
     */
    public SendMessageResponseType createSendMessageResponseType() {
        return new SendMessageResponseType();
    }

    /**
     * Create an instance of {@link SendMessagesAsync }
     */
    /*public SendMessagesAsync createSendMessagesAsync() {
        return new SendMessagesAsync();
    }*/

    /**
     * Create an instance of {@link Header }
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link Endpoint }
     */
    public Endpoint createEndpoint() {
        return new Endpoint();
    }

    /**
     * Create an instance of {@link Body.ByteMessage }
     */
    public Body.ByteMessage createBodyByteMessage() {
        return new Body.ByteMessage();
    }

    /**
     * Create an instance of {@link Message.Endpoints }
     */
    public Message.Endpoints createMessageEndpoints() {
        return new Message.Endpoints();
    }

    /**
     * Create an instance of {@link Message.Headers }
     */
    public Message.Headers createMessageHeaders() {
        return new Message.Headers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageResponseType }{@code >}}
     */
    @XmlElementDecl(namespace = "http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0", name = "SendMessageResponse")
    public JAXBElement<SendMessageResponseType> createSendMessageResponse(SendMessageResponseType value) {
        return new JAXBElement<SendMessageResponseType>(_SendMessageResponse_QNAME, SendMessageResponseType.class, null, value);
    }

}
