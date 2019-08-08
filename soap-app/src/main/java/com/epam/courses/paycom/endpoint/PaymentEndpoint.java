package com.epam.courses.paycom.endpoint;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.model.Payment;
import java.util.ArrayList;
import java.util.List;

import com.epam.courses.paycom.service.CompanyService;
import com.epam.courses.paycom.service.PaymentService;
import com.epam.courses.paycom.ws.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PaymentEndpoint {
    private static final String NAMESPACE_URI = "http://payments/";

    @Autowired
    public PaymentEndpoint(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private PaymentService paymentService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findPaymentByIdRequest")
    @ResponsePayload
    public FindPaymentByIdResponse findPaymentById(@RequestPayload FindPaymentByIdRequest request) {
        FindPaymentByIdResponse response = new FindPaymentByIdResponse();
        PaymentInfo paymentInfo = new PaymentInfo();
        BeanUtils.copyProperties(paymentService.findById(request.getPaymentId()), paymentInfo);
        response.setPaymentInfo(paymentInfo);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllPaymentsRequest")
    @ResponsePayload
    public FindAllPaymentsResponse findAllPayments() {
        FindAllPaymentsResponse response = new FindAllPaymentsResponse();
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        List<Payment> paymentList = paymentService.findAll();
        for (int i = 0; i < paymentList.size(); i++) {
            PaymentInfo ob = new PaymentInfo();
            BeanUtils.copyProperties(paymentList.get(i), ob);
            paymentInfoList.add(ob);
        }
        response.getPaymentInfo().addAll(paymentInfoList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findPaymentsByDateRequest")
    @ResponsePayload
    public FindPaymentsByDateResponse findPaymentsByDate(@RequestPayload FindPaymentsByDateRequest request) {
               FindPaymentsByDateResponse response = new FindPaymentsByDateResponse();
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        List<Payment> paymentList = paymentService.findByDate(new java.sql.Date(request.getBeginDate().toGregorianCalendar().getTime().getTime()), new java.sql.Date(request.getEndDate().toGregorianCalendar().getTime().getTime()));
        for (int i = 0; i < paymentList.size(); i++) {
            PaymentInfo ob = new PaymentInfo();
            BeanUtils.copyProperties(paymentList.get(i), ob);
            paymentInfoList.add(ob);
        }
        response.getPaymentInfo().addAll(paymentInfoList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPaymentRequest")
    @ResponsePayload
    public AddPaymentResponse addPayment(@RequestPayload AddPaymentRequest request) {
        AddPaymentResponse response = new AddPaymentResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        Payment payment = new Payment();
        payment.setPaymentId(request.getPaymentId());
        payment.setPayerName(request.getPayerName());
        payment.setPaymentSum(request.getPaymentSum());
        Company company = new Company();
        company.setCompanyId(request.getCompanyAccount().getCompanyId());
        company.setCompanyAccount(request.getCompanyAccount().getCompanyAccount());
        company.setCompanyName(request.getCompanyAccount().getCompanyName());
        payment.setCompanyAccount(company);
        payment.setPaymentDate(new java.sql.Date(request.getPaymentDate().toGregorianCalendar().getTime().getTime()));
        paymentService.add(payment);

        PaymentInfo paymentInfo = new PaymentInfo();
        BeanUtils.copyProperties(payment, paymentInfo);
        response.setPaymentInfo(paymentInfo);
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Payment Added Successfully");
        response.setServiceStatus(serviceStatus);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePaymentRequest")
    @ResponsePayload
    public DeletePaymentResponse deletePayment(@RequestPayload DeletePaymentRequest request) {
        Payment payment = paymentService.findById(request.getPaymentId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (payment == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Content Not Available");
        } else {
            paymentService.cancel(request.getPaymentId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Payment Deleted Successfully");
        }
        DeletePaymentResponse response = new DeletePaymentResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

}
