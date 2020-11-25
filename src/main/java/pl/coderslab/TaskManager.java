package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.DataFormatException;

class TaskManager {

    private static final String TASKS_FILE_DATABASE = "tasks.csv";
    private static final String FILE_DELIMITER = ",";
    private static final String[] MENU_OPTIONS = {"add", "remove", "list", "exit"};
    private static final String YES = "y";
    private static final String NO = "n";
    private static final String EMPTY_STRING = " ";
    private static final int NUMBER_OF_COLUMNS = 3;
    private static final String TRUE_STRING = "true";
    private static final String FALSE_STRING = "false";

    public static void main(String[] args) {

        String[][] tasks = readTasksFromFile();
        displayMenu();
        boolean isExit = false;

        do {
            String option = menuHandler();
            switch (option) {
                case "add":
                    int taskId = createTaskId(tasks);
                    String[] taskToAdd = getTaskFromUser(taskId);
                    tasks = addTask(tasks, taskToAdd);
                    printLog(ConsoleColors.RED, "Task was added!");
                    displayMenu();
                    break;
                case "remove":
                    if (tasks.length > 0) {
                        tasks = removeTask(tasks);
                        printLog(ConsoleColors.RED, "Task was removed!");
                    } else {
                        printLog(ConsoleColors.RED, "There is no task to remove!");
                    }
                    break;
                case "list":
                    displayAllTasks(tasks);
                    break;
                case "exit":
                    if (isSave()) {
                        writeListToFile(tasks);
                        printLog(ConsoleColors.RED, "List was saved to file");
                    }

                    printLog(ConsoleColors.RED, "BYE BYE!");
                    isExit=true;
                    break;
            }
        } while (!isExit);
    }

    private static void printLog(String color, String msg) {
        System.out.print(ConsoleColors.RESET);
        System.out.println(color + msg);
    }

    private static int createTaskId(String[][] tasks) {
        return tasks.length + 1;
    }

    public static void displayAllTasks(String[][] tasks) {
        printLog(ConsoleColors.PURPLE, "List of saved tasks: ");

        for (String[] line : tasks) {
            for (String task : line) {
                System.out.print(task + " | ");
            }
            System.out.println();
        }
        printLog(ConsoleColors.PURPLE,"---------end of list");
    }


    public static String[][] addTask(String[][] tasks, String[] task) {
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = task;
        return tasks;
    }


    private static String[] getTaskFromUser(int id) {
        String[] task = new String[4];
        task[0] = Integer.toString(id);

        System.out.println(ConsoleColors.RESET);
        printLog(ConsoleColors.PURPLE, "Provide details of new task: ");

        System.out.print("Task description: ");
        task[1] = getUserInput();

        System.out.print("Due date (yyyy-mm-dd): ");
        task[2] = getUserInput();

        System.out.print("Is it important (true/false): ");
        task[3] = getUserInput();

        return task;
    }


    private static String getUserInput() {
        Scanner input = new Scanner(System.in);
        String userInput;

        do {
            userInput = input.nextLine();
        } while (userInput.length() <= 0);

        return userInput;
    }


    private static String[][] readTasksFromFile() {
        File file = new File(TASKS_FILE_DATABASE);
        String[][] arrTasks = new String[0][0];
        int numberOfLines = 0;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String[] oneTask = createTask(scan.nextLine());
                //if (!validateTask(oneTask));
                oneTask = addIndexAtBeginning(numberOfLines, oneTask);
                arrTasks = Arrays.copyOf(arrTasks, arrTasks.length + 1);
                arrTasks[numberOfLines] = oneTask;
                numberOfLines++;
            }
        } catch (IOException  e) {
            System.out.format("Problems with data file! \nPlease check file: %s", TASKS_FILE_DATABASE);
            e.printStackTrace();
        } catch (DataFormatException e){
            System.out.println("Problems with format data in line " + (numberOfLines+1));
            System.out.println("Please check the delimiter (should be '" + FILE_DELIMITER + "')");
            throw new Error();
        }
        return arrTasks;
    }

    private static boolean validateTask(String[] task) {
        String dateColumn = task[1];
        String booleanColumn = task[2];
        //checkDateFormat(dateColumn);
        return checkBooleanFormat(booleanColumn);
    }

    private static boolean checkBooleanFormat(String booleanColumn) {
        return (TRUE_STRING.equals(booleanColumn) || FALSE_STRING.equals(booleanColumn));
    }


    private static String[] addIndexAtBeginning(int numberOfLines, String[] oneTask) {
        String[] lineWithIndex = new String[1];
        lineWithIndex[0] = String.valueOf(numberOfLines + 1);
        return ArrayUtils.addAll(lineWithIndex, oneTask);
    }


    private static String[] createTask(String line) throws DataFormatException {
        String[] arr = line.trim().split("\\s*"+FILE_DELIMITER+"\\s*");
        if (arr.length!=NUMBER_OF_COLUMNS){
            System.out.println(ConsoleColors.RED + "Wrong format data in file " + TASKS_FILE_DATABASE);
            throw new DataFormatException();
        }
        return arr;
    }


    private static void displayMenu() {
        System.out.print(ConsoleColors.RESET);
        printLog(ConsoleColors.WHITE, "Please select an option:");
        System.out.print(ConsoleColors.BLUE);
        for (String menuItem : MENU_OPTIONS) {
            System.out.println(menuItem);
        }
    }

    private static String menuHandler() {
        String token;
        while (isNotInMenu(token = getUserInput(), MENU_OPTIONS)) {
            System.out.println("There is no such option!");
        }
        return token;
    }

    private static boolean isNotInMenu(String token, String[] menu) {
        for (String option : menu) {
            if (option.equals(token)) return false;
        }
        return true;
    }

    private static void writeListToFile(String[][] tasks) {

        try (FileWriter file = new FileWriter(TASKS_FILE_DATABASE, false)) {
            for (String[] task : tasks) {
                StringBuilder fileLine = new StringBuilder();
                for (int i = 0; i < task.length; i++) {
                    if (i==0) {
                        continue; //do not write id of line
                    }
                    fileLine.append(task[i]);
                    if (i < task.length - 1)
                        fileLine.append(FILE_DELIMITER).append(EMPTY_STRING); //do not add delimiter after the last column
                }
                file.append(fileLine).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Problems with file writing!");
            e.printStackTrace();
        }
    }

    private static String[][] removeTask(String[][] tasks) {
        System.out.println(ConsoleColors.BLUE + "Which task should be removed? (from 1 to " + tasks.length + ")");
        System.out.print(ConsoleColors.RESET);
        String input;
        do {
            input = getUserInput();
        } while (isNotParsable(input));

        tasks = rebuildIndexes(ArrayUtils.remove(tasks, Integer.parseInt(input) - 1));
        return tasks;
    }

    private static String[][] rebuildIndexes(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            tasks[i][0] = Integer.toString(i + 1);
        }
        return tasks;
    }

    private static boolean isNotParsable(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.out.println("Wrong number!");
            return true;
        }
        return false;
    }

    private static boolean isSave() {
        printLog(ConsoleColors.PURPLE, "Do you want to save the list? (y/n)");
        String input;
        String[] menu = {YES, NO};
        do {
            input = getUserInput();
        } while (isNotInMenu(input, menu));
        return input.equals(YES);
    }
}