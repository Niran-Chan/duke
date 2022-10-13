package helperClasses;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DeadLine extends Task{
    protected LocalDate by;

    private void setLocalDate(String by)
    {
        this.by = LocalDate.parse(by);
    }
    public DeadLine(String description,String by)
    {
        super(description);
        setLocalDate(by);
    }
    public String finalString()
    {
        return "[D]" + super.finalString() +  "(by: " + this.by + ")";
    }
}
