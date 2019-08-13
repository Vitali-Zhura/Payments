package com.epam.courses.paycom.endpoint;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.stub.CompanyStub;
import java.util.ArrayList;
import java.util.List;

import com.epam.courses.paycom.service.CompanyService;
import com.epam.courses.paycom.ws.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CompanyEndpoint {

    private static final String NAMESPACE_URI = "http://payments/";

    private CompanyService companyService;

    @Autowired
    public CompanyEndpoint(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findCompanyByIdRequest")
    @ResponsePayload
    public FindCompanyByIdResponse findCompanyById(@RequestPayload FindCompanyByIdRequest request) {
        FindCompanyByIdResponse response = new FindCompanyByIdResponse();
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(companyService.findById(request.getCompanyId()), companyInfo);
        response.setCompanyInfo(companyInfo);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllCompaniesRequest")
    @ResponsePayload
    public FindAllCompaniesResponse findAllCompanies() {
        FindAllCompaniesResponse response = new FindAllCompaniesResponse();
        List<CompanyInfo> companyInfoList = new ArrayList<>();
        List<Company> companyList = companyService.findAll();
        for (int i = 0; i < companyList.size(); i++) {
            CompanyInfo ob = new CompanyInfo();
            BeanUtils.copyProperties(companyList.get(i), ob);
            companyInfoList.add(ob);
        }
        response.getCompanyInfo().addAll(companyInfoList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllStubsRequest")
    @ResponsePayload
    public FindAllStubsResponse findAllStubs() {
        FindAllStubsResponse response = new FindAllStubsResponse();
        List<CompanyInfoStub> companyStubList = new ArrayList<>();
        List<CompanyStub> companyList = companyService.findAllStubs();
        for (int i = 0; i < companyList.size(); i++) {
            CompanyInfoStub ob = new CompanyInfoStub();
            BeanUtils.copyProperties(companyList.get(i), ob);
            companyStubList.add(ob);
        }
        response.getCompanyInfoStub().addAll(companyStubList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findCompanyByAccountRequest")
    @ResponsePayload
    public FindCompanyByAccountResponse findCompanyByAccount(@RequestPayload FindCompanyByAccountRequest request) {
        FindCompanyByAccountResponse response = new FindCompanyByAccountResponse();
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(companyService.findByAccount(request.getCompanyAccount()), companyInfo);
        response.setCompanyInfo(companyInfo);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCompanyRequest")
    @ResponsePayload
    public AddCompanyResponse addCompany(@RequestPayload AddCompanyRequest request) {
        AddCompanyResponse response = new AddCompanyResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        Company company = new Company();
        company.setCompanyId(request.getCompanyId());
        company.setCompanyAccount(request.getCompanyAccount());
        company.setCompanyName(request.getCompanyName());
        companyService.add(company);
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Company Added Successfully");
        response.setServiceStatus(serviceStatus);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCompanyRequest")
    @ResponsePayload
    public DeleteCompanyResponse deleteCompany(@RequestPayload DeleteCompanyRequest request) {
        Company company = companyService.findById(request.getCompanyId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (company == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Content Not Available");
        } else {
            companyService.delete(request.getCompanyId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Company Deleted Successfully");
        }
        DeleteCompanyResponse response = new DeleteCompanyResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
