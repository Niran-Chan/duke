package helperClasses;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.lang.reflect.Array;
import java.util.ArrayList;

public class WriteToFile {

    public WriteToFile () {

    }
    public void writeToFile(ArrayList<Task> tasks)
    {
        try {
            FileWriter myWriter = new FileWriter("./data/duke.txt");

            for(int i =0 ; i < tasks.size();i++)
                myWriter.write(tasks.get(i).finalString() + "\n");

            myWriter.close();

        } catch (IOException e) {
            System.out.println("IO Error in Writing to File");
            e.printStackTrace();
        }
    }
}