package com.epam.courses.paycom.stub;

public class CompanyStub {

    private Integer id;
    private String name;
    private Integer counts;
    private Integer amounts;

    public CompanyStub() {
    }

    public CompanyStub(Integer id, String company, Integer counts, Integer amounts) {
        this.id = id;
        this.name = name;
        this.counts = counts;
        this.amounts = amounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return "CompanyStub{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count='" + counts + '\'' +
                ", amounts='" + amounts + '\'' +
                '}';
    }
}
