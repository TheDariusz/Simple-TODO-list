package pl.coderslab;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class TaskManager {

    public static final String TASKS_FILE_DATABASE = "tasks.csv";
    public static final char FILE_DELIMITER = ',';
    public static final String[] MENU_OPTIONS = {"add", "remove", "list", "exit"};

    public static void main(String[] args) {
        //String[][] tasks = getTasksFromFile();
        displayMenu();

       // String[][] tasks = getTasksFromFile();
        System.out.println(menuHandler());
    }

    private static void displayMenu() {
        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
        for (String menuItem : MENU_OPTIONS){
            System.out.println(menuItem);

    private static String menuHandler(){
        Scanner input = new Scanner(System.in);

        while (input.hasNext()) {
            String token = input.next();
            for (String option : MENU_OPTIONS){
                if (option.equals(token)){
                    return token;
                }
            }
            System.out.println("There in no such option!");
        }
        return "exit";
    }

}