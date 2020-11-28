# Simple TODO list
Terminal simple ToDo list in Java.
Project releated to **Tasks** module1 workshop in [CodersLab](coderslab.pl) Java Developer training program (*WAR_JEE_W_18* group)  

# Features
According to **Tasks** project requirements: 
- all available tasks displaying
- task adding 
- task removing
- storage data in one file 
- read from file on start and write to file on exit
- data format validation

# Usage
Program loads tasks from file (by default `TASKS_FILE_DATABASE=tasks.csv`) after starting.
User can choose one of four options from menu:
- **add** - add new task to list of tasks (according to *Contraints for new task data format*)
- **remove** - delete one of task from the existing ones (the program asks which task should be removed, with Integer format and proper number range validation)
- **list** - display all tasks from the list
- **exit** - exit program with prompt about if list should be saved 
The program adds an internal unique identifier for all tasks.


**Proper tasks file format**:
1. st column - string format without length limit
2. nd column - string with pattern: `yyyy-mm-dd` 
3. rd column - string wiht pattern: `true` or `false` 


**Constraints for data file format**:  
The program will throw an error:
- if columns are not separated by proper file delimiter (by default `FILE_DELIMITER = ","`) 
- if the second column is not in proper date format (`yyyy-mm-dd`) 
- if the third column is not in proper boolean format (`true` or `false`)  
**The program throws IOException if there are any problems with read/write file operations**


**Contraints for new task data format**:
- **task description** - string format without length limit
- **task due date** - string with pattern `yyyy-mm-dd` (program do not convert `String` to `Date` type, just check proper pattern)
- **task importance** - string with patter `true` or `false` (program do not convert `String` to `boolean` type, just check proper pattern)


# Build with
[**Java 11**](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) using only:
- arrays
- read/write file Java simple methods
- NumberUtils class
- [Apache Commons Lang](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3) library
