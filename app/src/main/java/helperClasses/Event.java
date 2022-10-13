package helperClasses;

public class Event extends Task {
    protected String at;

    public Event(String description,String at)
    {
        super(description);
        this.at = at;
    }
    public String finalString()
    {
        return "[E]" + super.finalString() + "(at: " + this.at + ")";
    }

}
