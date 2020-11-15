package pl.coderslab;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class TaskManager {

    public static final String TASKS_FILE_DATABASE = "tasks.csv";
    public static final char FILE_DELIMITER = ',';

    public static void main(String[] args) {
        //String[][] tasks = getTasksFromFile();
        displayMenu();

    }

    private static void displayMenu() {
        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }

    private static String[][] getTasksFromFile() {
        File file = new File(TASKS_FILE_DATABASE);
        String[][] arrTasks = new String[0][0];
        int numberOfLines=0;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] oneTask = {"Simple task - very important", "2020-03-09", "true"}; //getTaskFromLine(line);
                arrTasks = Arrays.copyOf(arrTasks,arrTasks.length+1);
                arrTasks[numberOfLines]=oneTask;
                numberOfLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrTasks;
    }
}