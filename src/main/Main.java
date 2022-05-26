package main;

import javax.print.Doc;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String []args){
        /*
        Patient patient = new Patient("hamza",1,077,0,0,"",null);
        Week ramziDocWeek=new  WeekBuilder(LocalTime.of(8,30),LocalTime.of(17,0))
                .setSun(true).setTue(true).setThu(true).build();
        Doctor ramzi= new Doctor("ramzi",ramziDocWeek);
        ramzi.addAppointment(new Appointment(1,LocalTime.of(11,0),
                LocalTime.of(12,0), LocalDate.of(2022,5,26)));
        ramzi.addAppointment(new Appointment(1,LocalTime.of(11,0),
                LocalTime.of(12,0), LocalDate.of(2022,5,28)));

        Week mohaDocWeek=new  WeekBuilder(LocalTime.of(8,30),LocalTime.of(17,0))
                .setSun(true).setTue(true).setThu(true).build();
        Doctor moha= new Doctor("moha",mohaDocWeek);
        moha.addAppointment(new Appointment(1,LocalTime.of(11,0),
                LocalTime.of(12,0), LocalDate.of(2022,5,26)));
        moha.addAppointment(new Appointment(1,LocalTime.of(11,0),
                LocalTime.of(12,0), LocalDate.of(2022,5,28)));

        List<Doctor> doctors= new ArrayList<>();
        doctors.add(ramzi);
        doctors.add(moha);
        String doctorsInfo="";
        for(var doc:doctors){
            doctorsInfo+=doc.toString();
        }
        ramzi.removeAppointmentsAtDay(DayOfWeek.FRIDAY);*/
        List<Doctor> doctors= new ArrayList<Doctor>();
        List<Patient> patients=new ArrayList<Patient>();
        try{
            ServerSocket server = new ServerSocket(5000);
            Socket receptions= server.accept();
            DataOutputStream outputStream = new DataOutputStream(receptions.getOutputStream());
            DataInputStream inputStream = new DataInputStream(receptions.getInputStream());
            String yesOrNo="";
            do{
                String docName=inputStream.readUTF();
                String docDays=inputStream.readUTF();
                String docStartTime=inputStream.readUTF();
                String docEndTime=inputStream.readUTF();

                String []start  = docStartTime.split(":");
                String []end  = docEndTime.split(":");
                Week week= new WeekBuilder(LocalTime.of(Integer.parseInt(start[0]),Integer.parseInt(start[1]))
                        ,LocalTime.of(Integer.parseInt(end[0]),Integer.parseInt(end[1])))
                        .setSun(docDays.contains("sun")).setMon(docDays.contains("mon"))
                        .setTue(docDays.contains("tue")).setWed(docDays.contains("wed"))
                        .setThu(docDays.contains("thu")).setFri(docDays.contains("fri"))
                        .setSat(docDays.contains("sat"))
                        .build();

                doctors.add(new Doctor(docName,week));
                yesOrNo=inputStream.readUTF();
            }while(yesOrNo.equalsIgnoreCase("Y"));

            do{
                patients.add(new Patient(inputStream.readUTF(),inputStream.readInt(),inputStream.readInt(),inputStream.readUTF()));
                 yesOrNo= inputStream.readUTF();

            }while (yesOrNo.equalsIgnoreCase("Y"));


            System.out.println(doctors.get(0).toString());
            System.out.println(patients.get(0));



        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }



}
