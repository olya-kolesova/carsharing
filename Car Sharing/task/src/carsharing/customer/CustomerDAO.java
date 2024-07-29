package carsharing.customer;

import carsharing.car.Car;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    List<Customer> findAll(List<Car> cars);
    Optional<Customer> findById(int id);
    void save(Customer customer);
//    void update(Car car);


}
