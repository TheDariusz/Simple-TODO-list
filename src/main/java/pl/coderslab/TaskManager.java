package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class TaskManager {

    private static final String TASKS_FILE_DATABASE = "tasks.csv";
    private static final String FILE_DELIMITER = ",";
    public static final String[] MENU_OPTIONS = {"add", "remove", "list", "exit"};

    public static void main(String[] args) {
        String[][] tasks = readTasksFromFile();
        displayMenu();
        String option = menuHandler();

        switch (option){
            case "add":
                System.out.println(ConsoleColors.RED + "Add task option was chosen!");
                System.out.print(ConsoleColors.RESET);
                break;
            case "remove":
                System.out.println(ConsoleColors.RED + "Remove task option was chosen!");
                System.out.print(ConsoleColors.RESET);
                break;
            case "list":
                System.out.println(ConsoleColors.RED + "Show all tasks option was chosen!");
                System.out.print(ConsoleColors.RESET);
                break;
            case "exit":
                System.out.println(ConsoleColors.RED + "BYE BYE!");
                System.out.print(ConsoleColors.RESET);
        }
    }

    /*
    Method get tasks from file TASK_FILE_DATABASE and return these tasks in 2d array
    If there is problem with TASK_FILE_DATABASE file method throws log from IOException
     */
    private static String[][] readTasksFromFile() {
        File file = new File(TASKS_FILE_DATABASE);
        String[][] arrTasks = new String[0][0];
        int numberOfLines = 0;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String[] oneTask = createTask(scan.nextLine());
                oneTask = addIndexAtBeginning(numberOfLines, oneTask);
                arrTasks = Arrays.copyOf(arrTasks, arrTasks.length + 1);
                arrTasks[numberOfLines] = oneTask;
                numberOfLines++;
            }
        } catch (IOException e) {
            System.out.format("Problems with data file! \n Please check file: %s", TASKS_FILE_DATABASE);
            e.printStackTrace();
        }
        return arrTasks;
    }

    /*
    Method gets a number and String array and puts the number at the beginning of the array
    It returns new shifted String arr with the number at the  first index
    Method use addAll method from ArrayUtils class
    */
    private static String[] addIndexAtBeginning(int numberOfLines, String[] oneTask) {
        String[] lineWithIndex = new String[1];
        lineWithIndex[0]= String.valueOf(numberOfLines);
        return ArrayUtils.addAll(lineWithIndex, oneTask);
    }

    /*
    Method split string to arr String based on FILE_DELIMITER
     */
    private static String[] createTask(String line) {
        return line.split(FILE_DELIMITER);
    }

    /*
    Method displays on the user screen predefined menu
    Menu options are store in MENU_OPTIONS final string array
     */
    private static void displayMenu() {
        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
        for (String menuItem : MENU_OPTIONS){
            System.out.println(menuItem);
        }
    }

    /*
    Method uses Scanner to get user input and checks if provided string corresponding to one of system menu
    Method returns matching token as String
     */
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