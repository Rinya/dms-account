
package ru.alfastrah.account.sber.backend.sms;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Сообщение сочетающее транспортный конверт и тело сообщения
 * <p>
 * <p>
 * <p>Java class for Message complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="Message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MessageFormat " type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CorrelationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="From" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateCreated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="MessageExpiration" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Endpoints">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Endpoint" type="{http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0}Endpoint" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Headers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Header" type="{http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0}Header" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Body" type="{http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0}Body"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", propOrder = {
        "messageFormat0020",
        "correlationId",
        "from",
        "dateCreated",
        "messageExpiration",
        "endpoints",
        "headers",
        "body"
})
public class Message
        implements Serializable {

    private final static long serialVersionUID = 20110101L;
    @XmlElement(name = "MessageFormat ")
    protected String messageFormat0020;
    @XmlElement(name = "CorrelationId")
    protected String correlationId;
    @XmlElement(name = "From")
    protected String from;
    @XmlElement(name = "DateCreated")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    @XmlElement(name = "MessageExpiration")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar messageExpiration;
    @XmlElement(name = "Endpoints", required = true)
    protected Message.Endpoints endpoints;
    @XmlElement(name = "Headers")
    protected Message.Headers headers;
    @XmlElement(name = "Body", required = true)
    protected Body body;

    /**
     * Gets the value of the messageFormat0020 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMessageFormat_0020() {
        return messageFormat0020;
    }

    /**
     * Sets the value of the messageFormat0020 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMessageFormat_0020(String value) {
        this.messageFormat0020 = value;
    }

    /**
     * Gets the value of the correlationId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the value of the correlationId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCorrelationId(String value) {
        this.correlationId = value;
    }

    /**
     * Gets the value of the from property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the dateCreated property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDateCreated(XMLGregorianCalendar value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the messageExpiration property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getMessageExpiration() {
        return messageExpiration;
    }

    /**
     * Sets the value of the messageExpiration property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setMessageExpiration(XMLGregorianCalendar value) {
        this.messageExpiration = value;
    }

    /**
     * Gets the value of the endpoints property.
     *
     * @return possible object is
     * {@link Message.Endpoints }
     */
    public Message.Endpoints getEndpoints() {
        return endpoints;
    }

    /**
     * Sets the value of the endpoints property.
     *
     * @param value allowed object is
     *              {@link Message.Endpoints }
     */
    public void setEndpoints(Message.Endpoints value) {
        this.endpoints = value;
    }

    /**
     * Gets the value of the headers property.
     *
     * @return possible object is
     * {@link Message.Headers }
     */
    public Message.Headers getHeaders() {
        return headers;
    }

    /**
     * Sets the value of the headers property.
     *
     * @param value allowed object is
     *              {@link Message.Headers }
     */
    public void setHeaders(Message.Headers value) {
        this.headers = value;
    }

    /**
     * Gets the value of the body property.
     *
     * @return possible object is
     * {@link Body }
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     *
     * @param value allowed object is
     *              {@link Body }
     */
    public void setBody(Body value) {
        this.body = value;
    }


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
     *         &lt;element name="Endpoint" type="{http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0}Endpoint" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "endpoint"
    })
    public static class Endpoints
            implements Serializable {

        private final static long serialVersionUID = 20110101L;
        @XmlElement(name = "Endpoint", required = true)
        protected List<Endpoint> endpoint;

        /**
         * Gets the value of the endpoint property.
         * <p>
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the endpoint property.
         * <p>
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEndpoint().add(newItem);
         * </pre>
         * <p>
         * <p>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Endpoint }
         */
        public List<Endpoint> getEndpoint() {
            if (endpoint == null) {
                endpoint = new ArrayList<Endpoint>();
            }
            return this.endpoint;
        }

    }


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
     *         &lt;element name="Header" type="{http://schemas.alfastrah.ru/interplat4/ws-message-gateway-1.0}Header" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "header"
    })
    public static class Headers
            implements Serializable {

        private final static long serialVersionUID = 20110101L;
        @XmlElement(name = "Header", required = true)
        protected List<Header> header;

        /**
         * Gets the value of the header property.
         * <p>
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the header property.
         * <p>
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHeader().add(newItem);
         * </pre>
         * <p>
         * <p>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Header }
         */
        public List<Header> getHeader() {
            if (header == null) {
                header = new ArrayList<Header>();
            }
            return this.header;
        }

    }

}
