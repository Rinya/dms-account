
package ru.alfastrah.account.sber.backend.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Тело сообщения
 * <p>
 * <p>Java class for Body complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="Body">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TextMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ByteMessage" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AttachmentName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Body", propOrder = {
        "textMessage",
        "byteMessage"
})
public class Body
        implements Serializable {

    private final static long serialVersionUID = 20110101L;
    @XmlElement(name = "TextMessage", required = true)
    protected String textMessage;
    @XmlElement(name = "ByteMessage")
    protected List<ByteMessage> byteMessage;

    /**
     * Gets the value of the textMessage property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTextMessage() {
        return textMessage;
    }

    /**
     * Sets the value of the textMessage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTextMessage(String value) {
        this.textMessage = value;
    }

    /**
     * Gets the value of the byteMessage property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the byteMessage property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getByteMessage().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Body.ByteMessage }
     */
    public List<ByteMessage> getByteMessage() {
        if (byteMessage == null) {
            byteMessage = new ArrayList<ByteMessage>();
        }
        return this.byteMessage;
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
     *         &lt;element name="AttachmentName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "attachmentName",
            "content"
    })
    public static class ByteMessage
            implements Serializable {

        private final static long serialVersionUID = 20110101L;
        @XmlElement(name = "AttachmentName", required = true)
        protected String attachmentName;
        @XmlElement(name = "Content", required = true)
        protected byte[] content;

        /**
         * Gets the value of the attachmentName property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getAttachmentName() {
            return attachmentName;
        }

        /**
         * Sets the value of the attachmentName property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setAttachmentName(String value) {
            this.attachmentName = value;
        }

        /**
         * Gets the value of the content property.
         *
         * @return possible object is
         * byte[]
         */
        public byte[] getContent() {
            return content;
        }

        /**
         * Sets the value of the content property.
         *
         * @param value allowed object is
         *              byte[]
         */
        public void setContent(byte[] value) {
            this.content = value;
        }

    }

}
