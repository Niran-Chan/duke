
import java.time.format.DateTimeParseException;
import java.util.*;
import ExceptionClasses.*;
import helperClasses.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

public class Duke extends Application{

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    @Override
    public void start (Stage stage){ //Override start method in Application
        //Step 1. Setting up required components
        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer); //Inside our scrollpane node we set a vertical Layout Box

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        // You will need to import `javafx.scene.layout.Region` for this.
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }
    private static void listAllTasks(List<Task> tasks) //Static method to print all tasks in List
    {
        Task task;
        System.out.println("You have a total of " + tasks.size() + " tasks");
        for(int i =0 ; i < tasks.size();i++) {
            task = tasks.get(i);
            StringBuilder result = new StringBuilder();
            result.append(i+1);
            result.append(".");
            result.append(task.finalString());
            System.out.println(result);
        }
    }
    private static void updateStatusOfTask(String userIn,List<Task> tasks)
    {
        try {
            int taskNumber = Integer.parseInt(userIn.split(" ", 2)[1]) - 1;
            tasks.get(taskNumber).updateTask(true);
            System.out.println("Updated Status!");
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println("Invalid Task Number! Please Input another!");
        }
    }


    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Please add tasks to list");
        System.out.println("Following Acceptable Task Definitions : \n1.todo\n2.deadline\n3.event");


        Scanner sc = new Scanner(System.in);
        String userIn = new String(""); //Initialise Scanner
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task;

        CreateFile File = new CreateFile();
        WriteToFile fileWriter = new WriteToFile();
        while(!userIn.equals("bye"))
        {
            try {
            userIn = sc.nextLine().trim();

            if(userIn.equals("list")){ //List out all tasks
                listAllTasks(tasks);
            }
            else if (userIn.startsWith("done")) {
                updateStatusOfTask(userIn, tasks);
                }
            else if (userIn.startsWith("find")){
                try {
                    String target = userIn.split(" ")[1];
                    for(int i =0; i < tasks.size();i ++ )
                    {
                        if(tasks.get(i).getDescription().contains(target))
                        {
                            System.out.println(i + 1 + ". " + tasks.get(i).getDescription());
                        }
                    }

                }
                catch(ArrayIndexOutOfBoundsException e)
                {
                    System.err.println("Please enter a keyword to search for");
                }
            }
            else if (userIn.startsWith("delete"))
            {
                int n = tasks.size();
                int index = Integer.parseInt(userIn.split(" ")[1]) - 1;
                try {
                    tasks.remove(index);
                    System.out.println("Successfully Removed Task!");
                }
                catch(IndexOutOfBoundsException e)
                {
                    if(n == 0)
                        throw new DukeException("TaskList is Empty!");
                    else
                        throw new DukeException("Invalid Index");
                }
            }
            else if (userIn.startsWith("todo")) { //If keyword

                    String[] splitArr = userIn.split("[ ]+");
                    String command = new String(splitArr[0]); //command is the first
                    String description = String.join(" ", (Arrays.copyOfRange(splitArr, 1, splitArr.length)));

                    if(description.isEmpty())
                        throw new DukeException("OOPS!!! The description of a todo cannot be empty.");

                    task = new ToDo(description);
                    tasks.add(task);
                    System.out.println("Added to task list : " + userIn);

            }
            else if (userIn.startsWith("deadline") || userIn.startsWith("event"))
            {
                String [] splitArr = userIn.split("[ ]+");
                String command = new String (splitArr[0]); //command is the first
                String description = "";
                int index = 1;
                for(int i = 1; i < splitArr.length;i++){
                    if(splitArr[i].equals("/by") || splitArr[i].equals("/at"))
                        break;
                    else {
                        description += splitArr[i] + " ";
                        index ++;
                    }
                }
                String timeLimit = String.join(" ",(Arrays.copyOfRange(splitArr, index + 1, splitArr.length)));

                if(description.isEmpty())
                    throw new DukeException("OOPS!!! The description cannot be empty.");

                if (command.equals("deadline"))
                task = new DeadLine(description,timeLimit);
            else
                task=new Event (description,timeLimit);
            tasks.add(task);
            System.out.println("Added to task list : " + userIn);
            }
            else
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            catch(IllegalArgumentException e){
                System.err.println("OOPS!!! Did you enter /by for deadline or /at for event?");
            }
            catch(DukeException e){
                e.printStackTrace();
            }
            catch(DateTimeParseException e)
            {
                System.err.println("Please input a date in the format YYYY-MM-DD");
            }
            fileWriter.writeToFile(tasks);
        }

        System.out.println("Bye!");
        return;
    }
}
