package beans;

import java.text.ParseException;
import java.util.Date;
import util.Dates;
import util.Utilities;

/** A task as it is stored in JSON. */
public class NewTaskBean {
  private Integer ID;
  private String Name;
  private String Description;
  private Float EstimatedETC;
  private Float ActualETC;
  private Date AssignDate;
  private Date EstimatedDate;
  private Date CompletedDate;

  public NewTaskBean() {}

  public NewTaskBean(NewTaskAddBean task) throws ParseException {
    this(
        task.getName().getText(),
        task.getDescription().getText(),
        task.getEstimatedETC().getText(),
        task.getEstimatedDate().getText());
  }

  public NewTaskBean(String name, String description, String estimatedHours, String estimatedDate)
      throws ParseException {
    this.ID = Utilities.nextTaskId();
    this.Name = name;
    this.Description = description == null ? "" : description;
    this.AssignDate = new Date();
    if (estimatedHours == null || estimatedHours.trim().isEmpty()) {
      throw new NumberFormatException("Estimated hours are required");
    }
    this.EstimatedETC = Float.parseFloat(estimatedHours.trim());
    this.ActualETC = 0f;
    if (estimatedDate == null || estimatedDate.trim().isEmpty()) {
      throw new ParseException("Estimated date is required", 0);
    }
    this.EstimatedDate = Dates.parse(estimatedDate.trim());
    this.CompletedDate = new Date();
  }

  public Integer getID() {
    return ID;
  }

  public void setID(Integer iD) {
    ID = iD;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public Float getEstimatedETC() {
    return EstimatedETC;
  }

  public void setEstimatedETC(Float estimatedETC) {
    EstimatedETC = estimatedETC;
  }

  public Float getActualETC() {
    return ActualETC;
  }

  public void setActualETC(Float actualETC) {
    ActualETC = actualETC;
  }

  public Date getAssignDate() {
    return AssignDate;
  }

  public void setAssignDate(Date assignDate) {
    AssignDate = assignDate;
  }

  public Date getEstimatedDate() {
    return EstimatedDate;
  }

  public void setEstimatedDate(Date estimatedDate) {
    EstimatedDate = estimatedDate;
  }

  public Date getCompletedDate() {
    return CompletedDate;
  }

  public void setCompletedDate(Date completedDate) {
    CompletedDate = completedDate;
  }

  @Override
  public String toString() {
    return ID
        + ","
        + Name
        + ","
        + Description
        + ","
        + Dates.format(AssignDate)
        + ","
        + Dates.format(CompletedDate)
        + ","
        + Dates.format(EstimatedDate)
        + ","
        + EstimatedETC
        + ","
        + ActualETC;
  }
}
