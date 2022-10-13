package helperClasses;

public class ToDo extends Task {
    public ToDo(String description)
    {
        super(description);
    };
    public String finalString()
    {
       return "[T]" + super.finalString();
    }
}
