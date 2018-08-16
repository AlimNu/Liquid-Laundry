package id.liqu.laundry.liquid.ui;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    String dateP,timeP, location, notes,dateD, timeD;

    public Order() {
    }

    public Order(String dateP, String timeP, String location, String notes, String dateD, String timeD) {
        this.dateP = dateP;
        this.timeP = timeP;
        this.location = location;
        this.notes = notes;
        this.dateD = dateD;
        this.timeD = timeD;
    }

    public String getDateP() {
        return dateP;
    }

    public void setDateP(String dateP) {
        this.dateP = dateP;
    }

    public String getTimeP() {
        return timeP;
    }

    public void setTimeP(String timeP) {
        this.timeP = timeP;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public String getTimeD() {
        return timeD;
    }

    public void setTimeD(String timeD) {
        this.timeD = timeD;
    }
}
