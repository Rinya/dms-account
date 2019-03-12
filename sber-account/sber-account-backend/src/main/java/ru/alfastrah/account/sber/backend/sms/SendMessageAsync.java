
package ru.alfastrah.account.sber.backend.sms;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Message" type="{http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0}Message"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "message"
})
@XmlRootElement(name = "SendMessageAsync")
public class SendMessageAsync
        implements Serializable {

    private final static long serialVersionUID = 20110101L;
    @XmlElement(name = "Message", required = true)
    protected Message message;

    /**
     * Gets the value of the message property.
     *
     * @return possible object is
     * {@link Message }
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     *
     * @param value allowed object is
     *              {@link Message }
     */
    public void setMessage(Message value) {
        this.message = value;
    }

}
