package carsharing;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
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
                        System.out.println("""
                                1. Company list
                                2. Create a company
                                0. Back""");
                        int commandList =
                    case 2:
                        break;
                    default:
                        System.out.println("Wrong command!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        DbClient dbClient = new DbClient();
        DbCompanyDao dbCompanyDao = new DbCompanyDao(dbClient);
        dbCompanyDao.findAll();
        dbCompanyDao.save(new Company("Volkswagen"));
        dbCompanyDao.findAll();



//        dbCompanyDao.updateColumnId();
//        dbCompanyDao.updateColumnName();
    }


}