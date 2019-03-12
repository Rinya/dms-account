package ru.alfastrah.account.sber.backend;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.alfastrah.account.sber.backend.avis.pdf.GetPdfPort;
import ru.alfastrah.account.sber.backend.converter.XmlConverter;
import ru.alfastrah.account.sber.backend.mapper.*;
import ru.alfastrah.account.sber.backend.platron.ElectronPay;
import ru.alfastrah.account.sber.backend.sms.MessageGatewayPort;

import javax.sql.DataSource;
import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "ru.alfastrah.account.sber.backend")
public class BackendConfiguration implements HasLogger {
    public static final String CONFIG_PROPERTIES = "LK_DMS_CONFIG";
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${sms.service.url:}")
    private String smsUrl;
    @Value("${print.service.url:}")
    private String printUrl;
    @Value("${pay.service.url:}")
    private String payUrl;

    @Value("${email.host}")
    private String emailHost;
    @Value("${email.login:}")
    private String emailUsername;
    @Value("${email.password:}")
    private String emailPassword;
    @Value("${email.debug:false}")
    private boolean emailDebug;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer res = new PropertySourcesPlaceholderConfigurer();
        res.setIgnoreResourceNotFound(true);
        if (StringUtils.isNotBlank(System.getenv(CONFIG_PROPERTIES))) {
            res.setLocation(new FileSystemResource(System.getenv(CONFIG_PROPERTIES)));
        }
        return res;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName(driverClassName);
        source.setUrl(dbUrl);
        source.setUsername(dbUser);
        source.setPassword(dbPassword);

        return source;
    }

    @Bean
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        return sessionFactoryBean.getObject();
    }

    @Bean
    public MapperFactoryBean<AuthenticationMapper> getAuthenticationFactoryBean() throws Exception {
        MapperFactoryBean<AuthenticationMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setSqlSessionFactory(getSqlSessionFactory());
        factoryBean.setMapperInterface(AuthenticationMapper.class);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<InsuredMapper> getInsuredFactoryBean() throws Exception {
        MapperFactoryBean<InsuredMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setSqlSessionFactory(getSqlSessionFactory());
        factoryBean.setMapperInterface(InsuredMapper.class);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<PolicyMapper> getPolicyFactoryBean() throws Exception {
        MapperFactoryBean<PolicyMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setSqlSessionFactory(getSqlSessionFactory());
        factoryBean.setMapperInterface(PolicyMapper.class);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<PaymentMapper> getPaymentFactoryBean() throws Exception {
        MapperFactoryBean<PaymentMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setSqlSessionFactory(getSqlSessionFactory());
        factoryBean.setMapperInterface(PaymentMapper.class);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<InvoiceMapper> getInvoiceFactoryBean() throws Exception {
        MapperFactoryBean<InvoiceMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setSqlSessionFactory(getSqlSessionFactory());
        factoryBean.setMapperInterface(InvoiceMapper.class);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<ApplicationMapper> getApplicationFactoryBean() throws Exception {
        MapperFactoryBean<ApplicationMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setSqlSessionFactory(getSqlSessionFactory());
        factoryBean.setMapperInterface(ApplicationMapper.class);
        return factoryBean;
    }

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

    @Lazy
    @Bean
    public MessageGatewayPort getMessageGatewayPort() {
        try {
            JaxWsProxy<MessageGatewayPort> smsProxy = new JaxWsProxy<>(MessageGatewayPort.class);
            MessageGatewayPort smsPort = smsProxy.getPort(smsUrl);

            Client cxfClient = JaxWsClientProxy.getClient(smsPort);
            if (cxfClient != null) {
                HTTPClientPolicy httpClientPolicy = getHttpClientPolicy(cxfClient);
                httpClientPolicy.setConnectionTimeout(10000);
                httpClientPolicy.setReceiveTimeout(10000);
            }

            return smsPort;
        } catch (Exception e) {
            getLogger().error("getMessageGatewayPort exception {}", e);
        }

        return null;
    }

    @Lazy
    @Bean
    public GetPdfPort getGetPdfPort() {
        try {
            JaxWsProxy<GetPdfPort> pdfProxy = new JaxWsProxy<>(GetPdfPort.class);
            GetPdfPort pdfPort = pdfProxy.getPort(printUrl);

            Client cxfClient = JaxWsClientProxy.getClient(pdfPort);
            if (cxfClient != null) {
                HTTPClientPolicy httpClientPolicy = getHttpClientPolicy(cxfClient);
                httpClientPolicy.setConnectionTimeout(10000);
                httpClientPolicy.setReceiveTimeout(10000);
            }

            return pdfPort;
        } catch (Exception e) {
            getLogger().error("getGetPdfPort exception {}", e);
        }

        return null;
    }

    @Lazy
    @Bean
    public ElectronPay getElectronPay() {
        try {
            JaxWsProxy<ElectronPay> payProxy = new JaxWsProxy<>(ElectronPay.class);
            ElectronPay payPort = payProxy.getPort(payUrl);

            Client cxfClient = JaxWsClientProxy.getClient(payPort);
            if (cxfClient != null) {
                HTTPClientPolicy httpClientPolicy = getHttpClientPolicy(cxfClient);
                httpClientPolicy.setConnectionTimeout(10000);
                httpClientPolicy.setReceiveTimeout(10000);
            }

            return payPort;
        } catch (Exception e) {
            getLogger().error("getElectronPay exception {}", e);
        }

        return null;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(emailHost);
        if (StringUtils.isNotBlank(emailUsername)) {
            mailSender.setUsername(emailUsername);
        }
        if (StringUtils.isNotBlank(emailPassword)) {
            mailSender.setPassword(emailPassword);
        }

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        //properties.put("mail.smtp.auth", "false");
        properties.put("mail.debug", String.valueOf(emailDebug));

        return mailSender;
    }

    private HTTPClientPolicy getHttpClientPolicy(Client cxfClient) {
        HTTPConduit httpConduit = (HTTPConduit) cxfClient.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpConduit.setClient(httpClientPolicy);
        return httpClientPolicy;
    }

    private class JaxWsProxy<T> extends JaxWsProxyFactoryBean {
        private final Class<T> portClass;

        JaxWsProxy(Class<T> portClass) {
            this.portClass = portClass;
        }

        T getPort(String url) {
            setServiceClass(portClass);
            setAddress(url);

            return (T) create();
        }
    }
}
