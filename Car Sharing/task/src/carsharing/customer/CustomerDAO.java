package carsharing.customer;

import carsharing.car.Car;

import java.util.List;

public interface CustomerDAO {

    List<Customer> findALL();
    Customer findById(int id);
    void save(Customer customer);
    void update(Car car);


}
