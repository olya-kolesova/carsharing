package carsharing;

import java.util.List;

public interface CarDao {
    void save(Car car, Company company);
    List<Company> findByCompany(Company company);
}
