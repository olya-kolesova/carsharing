package carsharing.car;

import carsharing.company.Company;

import java.util.List;

public interface CarDao {
    void save(Car car);
    List<Car> findByCompany(Company company);
    List<Car> findAll(List<Company> companies);
}
