
package ru.alfastrah.account.sber.backend.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EndpointType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EndpointType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SMS"/>
 *     &lt;enumeration value="Email"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EndpointType")
@XmlEnum
public enum EndpointType {

    SMS("SMS"),
    @XmlEnumValue("Email")
    EMAIL("Email");
    private final String value;

    EndpointType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EndpointType fromValue(String v) {
        for (EndpointType c: EndpointType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
