package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class TaskManager {

    private static final String TASKS_FILE_DATABASE = "tasks.csv";
    private static final String FILE_DELIMITER = ",";
    private static final String[] MENU_OPTIONS = {"add", "remove", "list", "exit"};
    private static final String YES = "y";
    private static final String NO = "n";
    private static final String EMPTY_STRING = " ";

    public static void main(String[] args) {

        String[][] tasks = readTasksFromFile();
        displayMenu();


        while (true) {
            String option = menuHandler();
            switch (option) {
                case "add":
                    tasks = addTask(tasks);
                    System.out.println(ConsoleColors.RED + "Task was added!");
                    System.out.print(ConsoleColors.RESET);
                    displayMenu();
                    break;
                case "remove":
                    if (tasks.length > 0) {
                        tasks = removeTask(tasks);
                        System.out.println(ConsoleColors.RED + "Task was removed!");
                        System.out.print(ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.RED + "There is no task to remove!");
                        System.out.print(ConsoleColors.RESET);
                    }
                    break;
                case "list":
                    displayAllTasks(tasks);
                    break;
                case "exit":
                    if (isSave()) {
                        writeListToFile(tasks);
                        System.out.println(ConsoleColors.RED + "List was saved to file");
                        System.out.print(ConsoleColors.RESET);
                    }

                    System.out.println(ConsoleColors.RED + "BYE BYE!");
                    System.out.print(ConsoleColors.RESET);
                    break;
            }
            if (option.equals("exit")) break;
        }
    }


    public static void displayAllTasks(String[][] tasks) {
        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "List of saved tasks: ");
        System.out.print(ConsoleColors.RESET);

        for (String[] line : tasks) {
            for (String task : line) {
                System.out.print(task + " | ");
            }
            System.out.println();
        }
        System.out.println(ConsoleColors.PURPLE + "---------end of list");
        System.out.println(ConsoleColors.RESET);
    }


    public static String[][] addTask(String[][] tasks) {
        int id = tasks.length + 1;
        String[] task = getTaskFromUser(id);
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = task;
        return tasks;
    }


    private static String[] getTaskFromUser(int id) {
        String[] task = new String[4];
        task[0] = Integer.toString(id);

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


    private static String getUserInput() {
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();

        if (userInput.length() > 0) {
            return userInput;
        }
        return getUserInput();
    }


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


    private static String[] addIndexAtBeginning(int numberOfLines, String[] oneTask) {
        String[] lineWithIndex = new String[1];
        lineWithIndex[0] = String.valueOf(numberOfLines + 1);
        return ArrayUtils.addAll(lineWithIndex, oneTask);
    }


    private static String[] createTask(String line) {
        return line.split(FILE_DELIMITER);
    }


    private static void displayMenu() {
        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
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
                    if (i==0) continue; //do not write id of line
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
        while (isNotParsable(input = getUserInput())) {
        }
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
            int index = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.out.println("Wrong number!");
            return true;
        }
        return false;
    }

    private static boolean isSave() {
        System.out.println(ConsoleColors.PURPLE + "Do you want to save the list? (y/n)");
        System.out.print(ConsoleColors.RESET);
        String input;
        String[] menu = {YES, NO};
        while (isNotInMenu(input = getUserInput(), menu)) {
        }
        return input.equals(YES);
    }
}