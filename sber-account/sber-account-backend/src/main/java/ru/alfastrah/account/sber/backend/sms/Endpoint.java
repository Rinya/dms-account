
package ru.alfastrah.account.sber.backend.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * Конечная точка
 * <p>
 * <p>Java class for Endpoint complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="Endpoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0}EndpointType"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Endpoint", propOrder = {
        "type",
        "address"
})
public class Endpoint
        implements Serializable {

    private final static long serialVersionUID = 20110101L;
    @XmlElement(name = "Type", required = true)
    protected EndpointType type;
    @XmlElement(name = "Address", required = true)
    protected String address;

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     * {@link EndpointType }
     */
    public EndpointType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link EndpointType }
     */
    public void setType(EndpointType value) {
        this.type = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAddress(String value) {
        this.address = value;
    }

}
