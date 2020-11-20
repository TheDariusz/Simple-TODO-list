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
        //displayAllTasks(getTasksFromFile());
        //String[][] tasks = addTask(getTasksFromFile());
        //displayAllTasks(tasks);
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
        String[] lineWithIndex = new String[oneTask.length+1];
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