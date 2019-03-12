package ru.alfastrah.account.sber.backend.converter;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;

public class XmlConverter implements Serializable {
    private Jaxb2Marshaller marshaller;

    public XmlConverter(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public String doMarshaling(Object object) {
        if (object == null) return "";

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(object, new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    public <T> T unmarshallXml(final InputStream xml) {
        return (T) marshaller.unmarshal(new StreamSource(xml));
    }
}
