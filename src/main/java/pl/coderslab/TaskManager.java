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
        //displayAllTasks(getTasksFromFile());
        //String[][] tasks = addTask(getTasksFromFile());
        //displayAllTasks(tasks);
    }

    /*
    Method displays all tasks from 2d array provided as argument
     */
    public static void displayAllTasks(String[][] tasks){
        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "List of saved tasks: ");
        System.out.print(ConsoleColors.RESET);

        for (String[] line : tasks){
            for (String task : line){
                System.out.print(task + " | ");
            }
            System.out.println();
        }
        System.out.println(ConsoleColors.PURPLE + "---------end of list");
        System.out.println(ConsoleColors.RESET);
    }

    /*
    Method add task to provided 2d array of tasks
    Method return new 2d array with new task
    */
    public static String[][] addTask(String[][] tasks){
        int id = tasks.length;
        String[] task = getTaskFromUser(id);
        tasks = Arrays.copyOf(tasks, tasks.length+1);
        tasks[task.length-1]=task;
        return tasks;
    }

    /*
    Method gets data from user based on Scanner input and returns these data in String array
    For now, method doesn't check any data validation (except zero string length)
    New task id is getting from method argument
     */
    private static String[] getTaskFromUser(int id) {
        String[] task = new String[4];
        task[0]=Integer.toString(id);

        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "Provide details of new task: ");
        System.out.print(ConsoleColors.RESET);

        System.out.print("Task description: ");
        task[1] = getUserInput();

        System.out.print("Due date (yyyy-mm-dd): ");
        task[2] = getUserInput();

        System.out.print("Is it important (true/false): ");
        task[3] = getUserInput();

        return task;
    }

    /*
    Method uses Scanner to get data from user input
    Validation: it only checks zero string length (as recursive calling)

     */
    private static String getUserInput() {
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();

        if (userInput.length() > 0) {
            return userInput;
        }
        return getUserInput();
    }

}