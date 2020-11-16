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

}