package carsharing.car;

import carsharing.company.Company;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    void save(Car car);

    Optional<Car> findById(int id);
    List<Car> findByCompany(Company company);
    List<Car> findAll(List<Company> companies);
}
