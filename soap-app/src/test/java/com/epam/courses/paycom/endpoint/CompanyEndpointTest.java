package com.epam.courses.paycom.endpoint;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.service.CompanyService;
import com.epam.courses.paycom.stub.CompanyStub;
import org.junit.After;
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

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CompanyEndpointTest {

    private static Company FIRST_COMPANY;
    private static CompanyStub COMPANY_STUB;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;
    private Resource xsdSchema = new ClassPathResource("xsds/payments.xsd");

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
        FIRST_COMPANY = create();
        COMPANY_STUB = createStub();
    }

    @AfterEach
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(companyService);
        Mockito.reset(companyService);
    }

    @Test
    public void findCompanyByIdEndpoint() throws Exception {

        Mockito.when(companyService.findById(anyInt())).thenReturn(FIRST_COMPANY);

        Source requestPayload = new StringSource(
                "<findCompanyByIdRequest xmlns='http://payments/'>" +
                        "<companyId>1</companyId>" +
                        "</findCompanyByIdRequest>");
        Source responsePayload = new StringSource(
                "<findCompanyByIdResponse xmlns='http://payments/'>" +
                        "<companyInfo>" +
                        "<companyId>1</companyId>" +
                        "<companyAccount>aaa</companyAccount>" +
                        "<companyName>bbb</companyName>" +
                        "</companyInfo>" +
                        "</findCompanyByIdResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(companyService, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void findAllCompaniesEndpoint() throws Exception {

        Mockito.when(companyService.findAll()).thenReturn(new ArrayList<Company>() {{add(FIRST_COMPANY);}});

        Source requestPayload = new StringSource(
                "<findAllCompaniesRequest xmlns='http://payments/'/>" );

        Source responsePayload = new StringSource(
                "<findAllCompaniesResponse xmlns='http://payments/'>" +
                        "<companyInfo>" +
                        "<companyId>1</companyId>" +
                        "<companyAccount>aaa</companyAccount>" +
                        "<companyName>bbb</companyName>" +
                        "</companyInfo>" +
                        "</findAllCompaniesResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(companyService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllStubsEndpoint() throws Exception {

        Mockito.when(companyService.findAllStubs()).thenReturn(new ArrayList<CompanyStub>() {{add(COMPANY_STUB);}});

        Source requestPayload = new StringSource(
                "<findAllStubsRequest xmlns='http://payments/'/>" );

        Source responsePayload = new StringSource(
                "<findAllStubsResponse xmlns='http://payments/'>" +
                        "<companyInfoStub>" +
                        "<id>1</id>" +
                        "<name>aaa</name>" +
                        "<counts>20</counts>" +
                        "<amounts>60</amounts>" +
                        "</companyInfoStub>" +
                        "</findAllStubsResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(companyService, Mockito.times(1)).findAllStubs();
    }

    @Test
    public void findCompanyByAccountEndpoint() throws Exception {

        Mockito.when(companyService.findByAccount(anyString())).thenReturn(FIRST_COMPANY);

        Source requestPayload = new StringSource(
                "<findCompanyByAccountRequest xmlns='http://payments/'>" +
                        "<companyAccount>aaa</companyAccount>" +
                        "</findCompanyByAccountRequest>");
        Source responsePayload = new StringSource(
                "<findCompanyByAccountResponse xmlns='http://payments/'>" +
                        "<companyInfo>" +
                        "<companyId>1</companyId>" +
                        "<companyAccount>aaa</companyAccount>" +
                        "<companyName>bbb</companyName>" +
                        "</companyInfo>" +
                        "</findCompanyByAccountResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(companyService, Mockito.times(1)).findByAccount(anyString());
    }

    @Test
    public void addCompanyEndpoint() throws Exception {

        Source requestPayload = new StringSource(
                "<addCompanyRequest xmlns='http://payments/'>" +
                        "<companyId>1</companyId>" +
                        "<companyAccount>aaa</companyAccount>" +
                        "<companyName>bbb</companyName>" +
                        "</addCompanyRequest>");
        Source responsePayload = new StringSource(
                "<addCompanyResponse xmlns='http://payments/'>" +
                         "<serviceStatus>" +
                         "<statusCode>SUCCESS</statusCode>" +
                         "<message>Company Added Successfully</message>" +
                         "</serviceStatus>" +
                         "</addCompanyResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(companyService, Mockito.times(1)).add(any());
    }

    @Test
    public void deleteCompanyEndpoint() throws Exception {

        Mockito.when(companyService.findById(anyInt())).thenReturn(FIRST_COMPANY);
        Source requestPayload = new StringSource(

                "<deleteCompanyRequest xmlns='http://payments/'>" +
                        "<companyId>1</companyId>" +
                        "</deleteCompanyRequest>");
        Source responsePayload = new StringSource(
                "<deleteCompanyResponse xmlns='http://payments/'>" +
                        "<serviceStatus>" +
                        "<statusCode>SUCCESS</statusCode>" +
                        "<message>Company Deleted Successfully</message>" +
                        "</serviceStatus>" +
                        "</deleteCompanyResponse>");
        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));

        Mockito.verify(companyService, Mockito.times(1)).delete(anyInt());
    }

    public Company create() {
        Company company = new Company();
        company.setCompanyId(1);
        company.setCompanyAccount("aaa");
        company.setCompanyName("bbb");
        return company;
    }

    public CompanyStub createStub() {
        CompanyStub stub = new CompanyStub();
        stub.setId(1);
        stub.setName("aaa");
        stub.setCounts(20);
        stub.setAmounts(60);
        return stub;
    }
}
