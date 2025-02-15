package carsharing;

import carsharing.car.Car;
import carsharing.car.DbCarDao;
import carsharing.company.Company;
import carsharing.company.DbCompanyDao;
import carsharing.customer.Customer;
import carsharing.customer.DbCustomerDao;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        DbClient dbClient = new DbClient();
        DbCompanyDao dbCompanyDao = new DbCompanyDao(dbClient);
        dbCompanyDao.createDb();
        DbCarDao dbCarDao = new DbCarDao(dbClient);
        dbCarDao.createTable();
        DbCustomerDao dbCustomerDao = new DbCustomerDao(dbClient);
//        dbCustomerDao.dropCustomer();
        dbCustomerDao.createTable();


        Scanner scanner = new Scanner(System.in);
        int commandStart = 100;

        while (commandStart != 0) {
            System.out.println("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    4. Delete all customers
                    0. Exit""");

            try {
                commandStart = scanner.nextInt();
                switch(commandStart) {
                    case 1:
                        int commandList = 100;
                        while (commandList != 0) {
                            System.out.println("""
                                    1. Company list
                                    2. Create a company
                                    3. Delete all
                                    0. Back""");
                            try {
                                commandList = scanner.nextInt();
                                switch(commandList) {
                                    case 1:
                                        if (dbCompanyDao.findAll().isEmpty()) {
                                            System.out.println("The company list is empty!");
                                        } else {

                                            for (Company company : dbCompanyDao.findAll()) {
                                                System.out.printf("%d. %s%n", company.getId(), company.getName());
                                            }
                                            System.out.println("0. Back");
                                            Scanner scannerChoose = new Scanner(System.in);
                                            System.out.println("Choose the company:");
                                            int index = scannerChoose.nextInt();
                                            if (index == 0) {
                                                break;
                                            }
                                            dbCompanyDao.findById(index).ifPresentOrElse(
                                                x -> System.out.printf("'%s' company%n", x.getName()),
                                                () -> System.out.println("The company not found!")
                                            );
                                            Company company = null;
                                            try {
                                               company = dbCompanyDao.findById(index).orElseThrow();
                                                System.out.println("We found company!");
                                            } catch (Exception e) {
                                                System.out.println("We can't find the company!");
                                            }

                                            int commandCars = 100;
                                            while (commandCars != 0) {
                                                System.out.println("""
                                                        1. Car list
                                                        2. Create a car
                                                        3. Delete cars
                                                        0. Back
                                                        """);
//
                                                commandCars = scanner.nextInt();
                                                switch (commandCars) {
                                                    case 1:
                                                        if (dbCarDao.findByCompany(company).isEmpty()) {
                                                            System.out.println("The car list is empty!");
                                                        } else {
                                                            int count = 1;
                                                            for (Car car : dbCarDao.findByCompany(company)) {
                                                                System.out.printf("%d. %s%n", count, car.getName());
                                                                count += 1;
                                                            }

                                                            System.out.println();
                                                        }
                                                        break;
                                                    case 2:
                                                        Scanner scannerCar = new Scanner(System.in);
                                                        System.out.println("Enter the car name:");
                                                        String carName = scannerCar.nextLine();
                                                        Car car = new Car(carName, company);
                                                        dbCarDao.save(car);
                                                        System.out.println("The car was added!");
                                                        System.out.println(car.getCompany().getId());
                                                        break;
                                                    case 3:
                                                        dbCarDao.deleteCars();
                                                    case 0:
                                                        break;


                                                }

                                            }

                                        }
                                        break;
                                    case 2:
                                        Scanner scannerSecond = new Scanner(System.in);
                                        System.out.println("Enter the company name:");
                                        String nameCompany = scannerSecond.nextLine();
                                        dbCompanyDao.save(new Company(nameCompany));
                                        break;
                                    case 3:
                                        dbCompanyDao.deleteAll();
                                        dbCompanyDao.restartId();
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("Wrong command!");
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;

                    case 2:
                        List<Company> companies = dbCompanyDao.findAll();
                        List<Car> allCars = dbCarDao.findAll(companies);
                        List<Customer> customers = dbCustomerDao.findAll(allCars);
                        if (customers.isEmpty()) {
                            System.out.println("The customer list is empty!");
                            break;
                        } else {
                            System.out.println(companies.size());
                            System.out.println("Customer list:");
                            allCars = dbCarDao.findAll(companies);
                            for (Customer customer : dbCustomerDao.findAll(allCars)) {
                                System.out.printf("%d. %s%n", customer.getId(), customer.getName());
                            }
                            System.out.println("0. Back");

                            System.out.println("Choose a customer:");
                            Scanner scannerCust = new Scanner(System.in);
                            int id = scannerCust.nextInt();
                            if (id == 0) {
                                break;
                            }
                            Customer customer = null;
                            customer = dbCustomerDao.findById(id).orElseThrow();

                            Scanner scannerRent = new Scanner(System.in);
                            int commandRent = 100;
                            while (commandRent != 0) {

                                System.out.println("""
                                    1. Rent a car
                                    2. Return a rented car
                                    3. My rented car
                                    0. Back
                                    """);

                                commandRent = scannerRent.nextInt();

                                switch(commandRent) {
                                    case 1:
                                        if (customer.getCar() != null) {
                                            System.out.println("You've already rented a car!");
                                        } else {
                                            System.out.println("Choose a company:");
                                            for (Company company : companies) {
                                                System.out.printf("%d. %s%n", company.getId(), company.getName());
                                            }
                                            System.out.println("0. Back");
                                            Scanner scannerCompany = new Scanner(System.in);
                                            int index = scannerCompany.nextInt();
                                            if (index == 0) {
                                                break;
                                            }

                                            Company company = dbCompanyDao.findById(index).orElseThrow();

                                            List<Car> cars = dbCarDao.findByCompany(company);
                                            List<Car> customersCars = customers.stream().map(Customer::getCar).
                                                    filter(Objects::nonNull).toList();

                                            List<Car> carsToChoose = cars.stream().filter(x -> !(customersCars.contains(x))).toList();
                                            int count = 1;
                                            System.out.println("Choose a car:");
                                            for (Car car : carsToChoose) {
                                                System.out.printf("%d. %s%n", count, car.getName());
                                                count += 1;
                                            }
                                            System.out.println("0. Back");

                                            int indexCar = scannerCompany.nextInt();
                                            if (indexCar == 0) {
                                                break;
                                            }
                                            Car car = dbCarDao.findById(carsToChoose.get(indexCar - 1).getId()).orElseThrow();
                                            customer.setCar(car);

                                            dbCustomerDao.update(customer);

                                            System.out.printf("You rented '%s'%n", car.getName());

                                        }
                                        break;


                                    case 2:
                                        if (customer.getCar() == null) {
                                            System.out.println("You didn't rent a car!");
                                        } else {
                                            customer.setCar(null);
                                            dbCustomerDao.updateNull(customer);
                                            System.out.println("You've returned a rented car!");
                                            break;

                                        }

                                    case 3:
                                        Optional.ofNullable(customer.getCar()).ifPresentOrElse(
                                            x -> System.out.printf("""
                                                Your rented car:
                                                %s
                                                Company:
                                                %s%n
                                                """, x.getName(), x.getCompany().getName()),
                                            () -> System.out.println("You didn't rent a car!")
                                        );

                                        break;

                                }

                            }

                            break;

                        }
                    case 3:
                        Scanner scannerCustomer = new Scanner(System.in);
                        System.out.println("Enter the customer name:");
                        String nameCustomer = scannerCustomer.nextLine();
                        dbCustomerDao.save(new Customer(nameCustomer));
                        System.out.println("The customer was added!");
                        break;

                    case 4:
                        dbCustomerDao.deleteAll();
                    case 0:
                        break;
                    default:
                        System.out.println("Wrong command!");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}