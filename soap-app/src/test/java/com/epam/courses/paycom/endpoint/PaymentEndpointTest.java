package com.epam.courses.paycom.endpoint;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class PaymentEndpointTest {

    private static Payment FIRST_PAYMENT;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;
    private Resource xsdSchema = new ClassPathResource("schema/payments.xsd");

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
        FIRST_PAYMENT = createPayment();
    }

    @AfterEach
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(paymentService);
        Mockito.reset(paymentService);
    }

    @Test
    public void findPaymentByIdEndpoint() throws Exception {

        Mockito.when(paymentService.findById(anyInt())).thenReturn(FIRST_PAYMENT);

        Source requestPayload = new StringSource(
                "<findPaymentByIdRequest xmlns='http://payments/'>" +
                        "<paymentId>1</paymentId>" +
                        "</findPaymentByIdRequest>");
        Source responsePayload = new StringSource(
                "<findPaymentByIdResponse xmlns='http://payments/'>" +
                        "<paymentInfo>" +
                        "<paymentId>1</paymentId>" +
                        "<payerName>aaa</payerName>" +
                        "<paymentSum>200</paymentSum>" +
                        "<companyAccount>" +
                        "<companyInfo>" +
                        "<companyId>1</companyId>" +
                        "<companyAccount>aaa</companyAccount>" +
                        "<companyName>bbb</companyName>" +
                        "</companyInfo>" +
                        "</companyAccount>" +
                        "<paymentDate>2019-03-10T12:12:30</paymentDate>" +
                        "</paymentInfo>" +
                        "</findPaymentByIdResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(paymentService, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void addPaymentEndpoint() throws Exception {

        Source requestPayload = new StringSource(
                "<addPaymentRequest xmlns='http://payments/'>" +
                        "<paymentInfo>" +
                        "<paymentId>1</paymentId>" +
                        "<payerName>aaa</payerName>" +
                        "<paymentSum>200</paymentSum>" +
                        "<companyAccount>" +
                        "<companyInfo>" +
                        "<companyId>1</companyId>" +
                        "<companyAccount>aaa</companyAccount>" +
                        "<companyName>bbb</companyName>" +
                        "</companyInfo>" +
                        "</companyAccount>" +
                        "<paymentDate>2019-03-10T12:12:30</paymentDate>" +
                        "</paymentInfo>" +
                        "</addPaymentRequest>");

        Source responsePayload = new StringSource(
                "<addPaymentResponse xmlns='http://payments/'>" +
                        "<serviceStatus>" +
                        "<statusCode>SUCCESS</statusCode>" +
                        "<message>Payment Added Successfully</message>" +
                        "</serviceStatus>" +
                        "</addPaymentResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(paymentService, Mockito.times(1)).add(any());
    }

    @Test
    public void deletePaymentEndpoint() throws Exception {

        Mockito.when(paymentService.findById(anyInt())).thenReturn(FIRST_PAYMENT);
        Source requestPayload = new StringSource(

                "<deletePaymentRequest xmlns='http://payments/'>" +
                        "<companyId>1</companyId>" +
                        "</deletePaymentRequest>");
        Source responsePayload = new StringSource(
                "<deletePaymentResponse xmlns='http://payments/'>" +
                        "<serviceStatus>" +
                        "<statusCode>SUCCESS</statusCode>" +
                        "<message>Payment Deleted Successfully</message>" +
                        "</serviceStatus>" +
                        "</deletePaymentResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(paymentService, Mockito.times(1)).cancel(anyInt());
    }

    public Payment createPayment() {
        Payment payment = new Payment();
        payment.setPaymentId(1);
        payment.setPayerName("aaa");
        payment.setPaymentSum(200);
        payment.setCompanyAccount(createCompany());
        payment.setPaymentDate(java.sql.Timestamp.valueOf("2019-03-10 12:12:30"));
        return payment;
    }

    public Company createCompany() {
        Company company = new Company();
        company.setCompanyId(1);
        company.setCompanyAccount("aaa");
        company.setCompanyName("bbb");
        return company;
    }
}
