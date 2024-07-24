package carsharing;

public class Car {
    private int id;
    private String name;
    private Company company;

    public Car(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public Car(int id, String name, Company company) {
        this.id = id;
        this.name = name;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int idNew) {
        this.id = idNew;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameNew) {
        this.name = nameNew;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompanyId(Company companyNew) {
        this.company = companyNew;
    }



}
