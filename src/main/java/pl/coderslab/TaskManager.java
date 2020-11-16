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
        String[][] tasks = getTasksFromFile();

    }

    private static String[][] getTasksFromFile() {
        File file = new File(TASKS_FILE_DATABASE);
        String[][] arrTasks = new String[0][0];
        int numberOfLines = 0;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] oneTask = splitColumns(line);
                arrTasks = Arrays.copyOf(arrTasks, arrTasks.length + 1);
                arrTasks[numberOfLines] = oneTask;
                numberOfLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problems with data file!");
            System.out.format("Please check file: %s", TASKS_FILE_DATABASE);
        }
        return arrTasks;
    }

    private static String[] splitColumns(String line) {
        return line.split(",");
    }
}