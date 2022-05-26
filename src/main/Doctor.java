package main;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String name;
    private Week workingSchedule;
    private List<Appointment> appointments;

    public Doctor(String name, Week workingSchedule) {
        this.name = name;
        this.workingSchedule = workingSchedule;
        this.appointments = new ArrayList<>() ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Week getWorkingSchedule() {
        return workingSchedule;
    }
    public void setWorkingSchedule(Week workingSchedule) {
        this.workingSchedule = workingSchedule;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void addAppointment(Appointment appointment) {
        this.appointments.add( appointment);
    }
    public void cancelAppointment(){
        appointments.clear();
    }
    public Doctor cancelAppointment(int indexOfDay){
        for(Appointment appointment :appointments){
          //
        }
        return this;
    }
    public String getAppointmentsString(){
        String y="\nAppointments : \n";
        for(Appointment appointment : appointments){
            y+=appointment.toString()+"\n";
        }
        return y;
    }
    public  void removeEntireSchedule(Doctor doctor){
        this.getAppointments().clear();
    }
    /**
    * removes all appointments for a certain date
    * @params day day you want to cancel appointments on
    */
    public void removeAppointmentsAtDay(DayOfWeek day){
        for(Appointment appointment:getAppointments()){
            if(appointment.getDate().getDayOfWeek()== day){
                getAppointments().remove(appointment);
            }
        }
    }
    @Override
    public String toString() {
        return "\n------------------------------\n"+
                this.name+"\nstart time "+workingSchedule.getStartTime()+" | end time "+
                workingSchedule.getEndTime()+" \n"+workingSchedule+getAppointmentsString()+"\n"+
                "------------------------------";

    }
}