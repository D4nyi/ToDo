package todo.miskolc.uni.iit.hu.todo.models;

import java.util.Date;

public class ToDoItem {
    private String title;
    private String description;
    private String place;
    private Date startDate;
    private Date endDate;
    private int availability;

    public ToDoItem() {
    }

    public ToDoItem(String title, String description, String place) {
        this.title = title;
        this.description = description;
        this.place = place;
    }

    public ToDoItem(String title, String description, String place, int availability) {
        this.title = title;
        this.description = description;
        this.place = place;
        this.availability = availability;
    }

    public ToDoItem(String title, String description, String place, Date startDate, Date endDate, int availability) {
        this.title = title;
        this.description = description;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availability = availability;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        if (availability > 0 && availability < 4) {
            this.availability = availability;
        } else {
            throw new IllegalArgumentException("Availability must be either of these: 1, 2, 3!");
        }
    }
}
