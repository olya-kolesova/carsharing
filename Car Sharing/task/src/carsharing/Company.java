package carsharing;

import java.util.List;

public class Company {
    private int id;
    private String name;

    private List<Car> cars;

    Company(String name) {
        this.name = name;
    }

    Company(int id, String name) {
        this.id = id;
        this.name = name;
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


}
