package carsharing;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        DbClient dbClient = new DbClient();
        DbCompanyDao dbCompanyDao = new DbCompanyDao(dbClient);
//        dbCompanyDao.dropTable();
        dbCompanyDao.createDb();
        Scanner scanner = new Scanner(System.in);
        int commandStart = 100;



        while (commandStart != 0) {
            System.out.println("""
                    1. Log in as a manager
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
                                            Scanner scannerChoose = new Scanner(System.in);
                                            System.out.println("Choose the company:");
                                            int index = scanner.nextInt();
                                            dbCompanyDao.findById(index).ifPresentOrElse(
                                                x -> System.out.printf("%s company", x.getName()),
                                                System.out.println("The company not found!"));
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