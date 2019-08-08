package com.epam.courses.paycom.endpoint;

import com.epam.courses.paycom.service.CompanyService;
import com.epam.courses.paycom.service.PaymentService;
import org.mockito.Mockito;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
@ComponentScan
public class TestConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/soapws/*");
    }

    @Bean(name = "company")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema companySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CompanyPort");
        wsdl11Definition.setLocationUri("/soapws");
        wsdl11Definition.setTargetNamespace("http://payments/");
        wsdl11Definition.setSchema(companySchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema companySchema() {
        return new SimpleXsdSchema(new ClassPathResource("/xsds/payments.xsd"));
    }

    @Bean
    public CompanyService companyService() {
        return Mockito.mock(CompanyService.class);
    }

    @Bean
    public PaymentService paymentService() {
        return Mockito.mock(PaymentService.class);
    }
}
