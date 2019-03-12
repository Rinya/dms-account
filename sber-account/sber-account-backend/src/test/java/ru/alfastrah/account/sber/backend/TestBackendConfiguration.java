package ru.alfastrah.account.sber.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.alfastrah.account.sber.backend.converter.XmlConverter;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestBackendConfiguration implements HasLogger {
    @Bean
    public Jaxb2Marshaller getJaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("ru.alfastrah.account.sber.backend.model");

        Map<String, Object> properties = new HashMap<>();
        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        properties.put(Marshaller.JAXB_ENCODING, "UTF-16");
        marshaller.setMarshallerProperties(properties);

        return marshaller;
    }

    @Bean
    public XmlConverter getXmlConverter() {
        return new XmlConverter(getJaxb2Marshaller());
    }
}
