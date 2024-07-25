package carsharing;

import java.util.List;

public interface CarDao {
    void save(Car car);
    List<Car> findByCompany(Company company);
}
