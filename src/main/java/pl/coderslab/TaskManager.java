package pl.coderslab;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class TaskManager {

    private static final String TASKS_FILE_DATABASE = "tasks.csv";
    private static final String FILE_DELIMITER = ",";

    public static void main(String[] args) {
        String[][] tasks = readTasksFromFile();
        System.out.println(tasks);
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
        String[] lineWithIndex = new String[oneTask.length+1];
        lineWithIndex[0]= String.valueOf(numberOfLines);
        for (int i=1; i<lineWithIndex.length; i++){
            lineWithIndex[i]=oneTask[i-1];
        }
        return lineWithIndex;
    }

    private static String[] createTask(String line) {
        return line.split(FILE_DELIMITER);
    }
}