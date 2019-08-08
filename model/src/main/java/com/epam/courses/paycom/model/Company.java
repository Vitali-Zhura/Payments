package com.epam.courses.paycom.model;

import com.epam.courses.paycom.stub.CompanyStub;
import javax.persistence.*;
import java.io.Serializable;

@SqlResultSetMapping(
        name = "CompanyValueMapping",
        classes = @ConstructorResult(
                targetClass = CompanyStub.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "counts", type = Integer.class),
                        @ColumnResult(name = "amounts", type = Integer.class )}))

@NamedNativeQuery(name = "Company.findAllStubs",
                  resultSetMapping="CompanyValueMapping",
        query = "SELECT c.company_id AS id, c.company_name AS name, " +
                "count(p.company_account) AS counts, " +
                "sum(p.payment_sum) AS amounts " +
                "FROM company c LEFT JOIN payment p " +
                "ON (c.company_account = p.company_account) " +
                "GROUP BY c.company_id")

@Entity
@Table(name = "company", uniqueConstraints={@UniqueConstraint(columnNames={"company_account"})})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", updatable = false, nullable = false)
    private Integer companyId;

    @Column(name = "company_account", unique = true)
    private String companyAccount;

    @Column(name = "company_name")
    private String companyName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyAccount='" + companyAccount + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
