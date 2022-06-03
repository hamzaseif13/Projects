package main;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Doctor {
    private String name;
    private Week workingSchedule;
    private List<Appointment> appointments;
    private int Id;
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public Doctor(String name, Week workingSchedule,int Id) {
        this.name = name;
        this.workingSchedule = workingSchedule;
        this.appointments = new ArrayList<>() ;
        this.Id=Id;
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
    public String getAppointmentsString(){
        String y="\nAppointments : \n";
        for(Appointment appointment : appointments){
            y+=appointment.toString()+"\n";
        }
        return y;
    }
    public void DeleteDay(List<DayOfWeek> list) {
       List <Appointment> newAppointments= new ArrayList<>();
        List<Integer> arr= new ArrayList<>();
        for (DayOfWeek Day : list) {
        for (int j=0;j<appointments.size();j++) {
            if(Day == appointments.get(j).getDay())
            {
                try {
                    Main.deletedPatientsIds.add(appointments.get(j).getPatientID());
                    arr.add(j);
                } catch (Throwable ex) {
                    Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        }

        for(int j=0;j< appointments.size();j++){
            for(var n:arr){
                if(j!=n){
                    newAppointments.add(appointments.get(j));
                }
            }
        }
        appointments= newAppointments;
    }
    private boolean checkIntersection(Appointment first,Appointment second){
        if(first.getDay()!=second.getDay()){
            return false;
        }
        if(first.getStartTime()==second.getStartTime())return true;
        if(first.getStartTime().isBefore(second.getStartTime())){
            if(first.getEndTime().isAfter(second.getStartTime()))return true;
            else return false;
        }
        else {
            if(second.getEndTime().isAfter(first.getStartTime()))return true;
            else return false;
        }
    }
    public int validateAndAdd(Appointment appointmentcheck) {
        DayOfWeek day;
        if(appointmentcheck.getDay()==DayOfWeek.SUNDAY&&!workingSchedule.isSun()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.MONDAY&&!workingSchedule.isMon()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.TUESDAY&&!workingSchedule.isTue()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.WEDNESDAY&&!workingSchedule.isWed()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.THURSDAY&&!workingSchedule.isThu()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.FRIDAY&&!workingSchedule.isFri()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.SATURDAY&&!workingSchedule.isSat()){
            return 0;
        }
        if(appointmentcheck.getStartTime().isBefore(workingSchedule.getStartTime())
                ||appointmentcheck.getEndTime().isAfter(workingSchedule.getEndTime())){
            return 3;
        }
        if(appointmentcheck.getEndTime().toSecondOfDay() - appointmentcheck.getStartTime().toSecondOfDay()< (60*30)){
            return 4;
        }
        for(Appointment appointment:appointments){
            if(checkIntersection(appointment,appointmentcheck))return 1;
        }
        return 2;
    }
    @Override
    public String toString() {
        return "\n------------------------------\n"+
                this.name+"\nDoctor Id "+this.getId()+"\nStart time "+workingSchedule.getStartTime()+" | End time "+
                workingSchedule.getEndTime()+" \n"+workingSchedule+getAppointmentsString()+"\n"+
                "------------------------------";
    }
}