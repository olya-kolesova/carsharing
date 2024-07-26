package carsharing.customer;
import carsharing.car.Car;

public class Customer {
    private int id;
    private String name;
    private Car car;


    public Customer(String name) {
        this.name = name;
    }

    public Customer(int id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.car = car;
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
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car carNew) {
        this.car = carNew;
    }
}
