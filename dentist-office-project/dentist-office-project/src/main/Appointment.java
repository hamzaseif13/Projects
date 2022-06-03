package main;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
public class Appointment {
    private int patientID;
    private LocalTime endTime;
    private LocalTime startTime;
    private DayOfWeek day;
    public Appointment(int patientID, LocalTime startTime, LocalTime endTime, DayOfWeek day) {
        this.patientID = patientID;
        this.endTime = endTime;
        this.startTime = startTime;
        this.day = day;
    }
    public int getPatientID() {
        return patientID;
    }
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public DayOfWeek getDay() {
        return day;
    }
    public void setDay(DayOfWeek day) {
        this.day = day;
    }
    public void DeleteApp() throws Throwable{
        this.finalize();
    }
    @Override
    public String toString() {
        return
                "patient id:"+patientID+" | date "+ day.toString()+" "+
                " | start time "+startTime.toString()+" | end time "+endTime.toString()+"";
    }
}
