package ru.alfastrah.account.sber;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.context.annotation.Configuration;
import ru.alfastrah.account.sber.backend.HasLogger;

import javax.servlet.annotation.WebServlet;
import java.io.Serializable;
import java.util.Properties;

@Configuration
@WebServlet(urlPatterns = "/*", name = "AppServlet", asyncSupported = true)
public class AppServlet extends SpringVaadinServlet implements Serializable, HasLogger {
    private static final String PRODUCTION_MODE = "productionMode";

    @Override
    protected DeploymentConfiguration createDeploymentConfiguration(Properties initParameters) {
        initParameters.put(PRODUCTION_MODE, "true");
        return super.createDeploymentConfiguration(initParameters);
    }
}
