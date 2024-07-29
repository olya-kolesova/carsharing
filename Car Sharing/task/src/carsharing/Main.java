package carsharing;

import carsharing.car.Car;
import carsharing.car.DbCarDao;
import carsharing.company.Company;
import carsharing.company.DbCompanyDao;
import carsharing.customer.Customer;
import carsharing.customer.DbCustomerDao;

import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        DbClient dbClient = new DbClient();
        DbCompanyDao dbCompanyDao = new DbCompanyDao(dbClient);
        dbCompanyDao.createDb();
        DbCarDao dbCarDao = new DbCarDao(dbClient);
        dbCarDao.createTable();
        DbCustomerDao dbCustomerDao = new DbCustomerDao(dbClient);
        dbCustomerDao.createTable();


        Scanner scanner = new Scanner(System.in);
        int commandStart = 100;

        while (commandStart != 0) {
            System.out.println("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
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
                                            } catch (Exception e) {
                                                break;
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
                        System.out.println("Customer list:");
                        List<Company> companies = dbCompanyDao.findAll();
                        List<Car> allCars = dbCarDao.findAll(companies);
                        for (Customer customer : dbCustomerDao.findAll(allCars)) {
                            System.out.printf("%d.%s%n", customer.getId(), customer.getName());
                        }

                        System.out.println("Choose a customer:");
                        Scanner scannerCust = new Scanner(System.in);
                        int id = scannerCust.nextInt();
                        Customer customer = null;
                        try {
                            customer = dbCustomerDao.findById(id).orElseThrow();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("""
                                1. Rent a car
                                2. Return a rented car
                                3. My rented car
                                0. Back
                                """);

                        Scanner scannerRent = new Scanner(System.in);
                        int commandRent = scannerRent.nextInt();
                        switch(commandRent) {
                            case 1:
                                System.out.println("Choose a company:");
                                for (Company company : companies) {
                                    System.out.printf("%d. %s", company.getId(), company.getName());
                                }
                                Scanner scannerCompany = new Scanner(System.in);
                                int index = scannerCompany.nextInt();
                                Company company = null;
                                company = dbCompanyDao.findById(index).orElseThrow();

                                List<Car> cars = dbCarDao.findByCompany(company);
                                int count = 1;
                                System.out.println("Choose a car:");
                                for (Car car : cars) {
                                    System.out.printf("%d. %s", count, car.getName());
                                    count += 1;
                                }

                                int indexCar = scannerCompany.nextInt();
                                dbCarDao.findById(cars.get(indexCar).getId());
                        }


                        break;
                    case 3:
                        Scanner scannerCustomer = new Scanner(System.in);
                        System.out.println("Enter the customer name:");
                        String nameCustomer = scannerCustomer.nextLine();
                        dbCustomerDao.save(new Customer(nameCustomer));
                        System.out.println("The customer was added!");
                        break;
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




//        dbCompanyDao.updateColumnId();
//        dbCompanyDao.updateColumnName();
    }


}