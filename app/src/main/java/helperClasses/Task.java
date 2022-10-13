package helperClasses;
public class Task {
    protected String description;
    protected boolean status;
    public Task(String description){ //Constructor
        this.description=description;
        this.status = false;
    }
    public String getDescription(){return this.description;}
    public String getStatus(){
        return (this.status ? "\u2713" : "\u2718");
    }
    public void updateTask(boolean status)
    {
        this.status = status;
    }
    public String finalString()
    {
        return "[" + getStatus() + "]" + this.description;
    }
}
