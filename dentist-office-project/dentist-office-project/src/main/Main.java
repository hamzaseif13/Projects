package main;

import client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    static ServerSocket server;
    static Socket receptions;
    static DataOutputStream outputStream;
    static List<Integer> deletedPatientsIds= new ArrayList<>();
    static DataInputStream inputStream;
    static String yesOrNo;
    static List<Doctor> doctors = new ArrayList<Doctor>();
    static List<Patient> patients = new ArrayList<Patient>();
    static List<Appointment> appointments = new ArrayList<Appointment>();
    public static void addFakeData(){
        Week week = new WeekBuilder(LocalTime.of(8,30),LocalTime.of(16,30)).setSun(true).setMon(true).setTue(true).build();
        Doctor doctor = new Doctor("D.Hala",week,1);
        doctors.add(doctor);
        Patient patient = new Patient("patient:hamza",1,"432","dsfdsf");
        patients.add(patient);
    }
    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            receptions = server.accept();
            outputStream = new DataOutputStream(receptions.getOutputStream());
            inputStream = new DataInputStream(receptions.getInputStream());
            yesOrNo = "";
            addFakeData();
            outputStream.writeUTF(printAllDatabase());
            int choice=-1;
            while (choice != 7) {
                 choice = inputStream.readInt();
                switch (choice) {
                    case 1:
                        receiveDoctor();
                        break;
                    case 2:
                        receivePatient();
                        break;
                    case 3:
                        receiveAppointments();
                        break;
                    case 4:
                        DeleteAppointment();
                        break;
                    case 5:
                        outputStream.writeUTF(printAllDatabase());
                        break;
                    case 6:
                        AddVisitInformation();
                        break;
                    default:
                        break;
                }
            }
            //sending all info and deleted appointments info after the program finishes
            outputStream.writeUTF(printAllDatabase());
            outputStream.writeUTF(callDeletedPatients());
            outputStream.writeUTF("all patients info was saved in a text file");
            System.out.println("all patients info was saved in a text file");
            file();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
    public static void file(){
        try {
            File His = new File("His.txt");
            if(!His.exists()) {
                His.createNewFile();
            }
            PrintWriter pw = new PrintWriter(His);
            for (Patient P : patients) {
                pw.println("-----------------------------------------------------");
                pw.println("Patient Name is : " + P.getName());
                pw.println("Patient ID is : " + P.getID());
                pw.println("Patient Phone number Is : " + P.getPhoneNumber());
                pw.println("Patient E-Mail Is : " + P.getEmail());
                for (Visit visit : P.getPreviousVisits()) {
                    pw.printf("Patient visit description : %s\nPatient visit cost is : %.02f\n",visit.getDescription(),visit.getCost());
                }
                pw.println("Patient total owd is : "+P.getOwedBalance());
                pw.println("Patient total paid is : "+P.getTotalPaid());
                pw.println("-----------------------------------------------------");
            }
            pw.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String callDeletedPatients() {
        String x="";
        for(int id : deletedPatientsIds){
            for(Patient patient:patients){
                if(patient.getID()==id){
                    x+="calling : "+patient.getName()+" to tell him that his appointment was canceled\n";
                }
            }
        }

        System.out.println(x);
        return x;
    }
    public static void receiveDoctor() throws IOException {
        String doctorId = inputStream.readUTF();
        String docName = inputStream.readUTF();
        String docDays = inputStream.readUTF();
        String docStartTime = inputStream.readUTF();
        String docEndTime = inputStream.readUTF();
        String[] start = docStartTime.split(":");
        String[] end = docEndTime.split(":");
        LocalTime startTime = LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(end[1]));
        LocalTime endTime = LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));

    if(checkDoctorId(doctorId) || !isTimeValid(docStartTime) || !isTimeValid(docEndTime) || startTime.isAfter(endTime) ){
        outputStream.writeUTF(Client.RED+"Incorrect input(doctor id or start time or end time), please try again!"+Client.RESET);
    }
    else{
        outputStream.writeUTF(Client.GREEN+"doctor added"+Client.RESET);
        Week week = new WeekBuilder(startTime, endTime).setSun(docDays.contains("sun")).setMon(docDays.contains("mon")).setTue(docDays.contains("tue")).
        setWed(docDays.contains("wed")).setThu(docDays.contains("thu")).setFri(docDays.contains("fri")).setSat(docDays.contains("sat")).build();
        doctors.add(new Doctor(docName, week,Integer.parseInt(doctorId)));
    }
    }
    public static void receivePatient() throws IOException {
        String patientName = inputStream.readUTF();
        String patientId = inputStream.readUTF();
        String phoneNumber = inputStream.readUTF();
        String email = inputStream.readUTF();

        if(checkPatientId(patientId)){
            outputStream.writeUTF(Client.RED+"Patient id already exits"+Client.RESET);
        }
        else {
            outputStream.writeUTF(Client.GREEN+"patient added"+Client.RESET);
            patients.add(new Patient(patientName, Integer.parseInt(patientId), phoneNumber, email));
        }
    }
    public static void receiveAppointments() throws IOException {
        int doctorId = inputStream.readInt();
        int patientId = inputStream.readInt();
        String startTime = inputStream.readUTF();
        String endTime = inputStream.readUTF();
        String appointmentDay = inputStream.readUTF();
        LocalTime start = LocalTime.of(Integer.parseInt(startTime.split(":")[0]), Integer.parseInt(startTime.split(":")[1]));
        LocalTime end = LocalTime.of(Integer.parseInt(endTime.split(":")[0]), Integer.parseInt(endTime.split(":")[1]));
        DayOfWeek day;
        switch (appointmentDay) {
            case "sun":
                day = DayOfWeek.SUNDAY;
                break;
            case "mon":
                day = DayOfWeek.MONDAY;
                break;
            case "tue":
                day = DayOfWeek.TUESDAY;
                break;
            case "wed":
                day = DayOfWeek.WEDNESDAY;
                break;
            case "thu":
                day = DayOfWeek.THURSDAY;
                break;
            case "fri":
                day = DayOfWeek.FRIDAY;
                break;
            default:
                day = DayOfWeek.SATURDAY;
                break;
        }
        Appointment appointment = new Appointment(patientId, start, end, day);
        appointments.add(appointment);
        for (Doctor doc:doctors){
            if(doc.getId()==doctorId){
                if(doc.validateAndAdd(appointment)==2){
                    outputStream.writeInt(2);
                    doc.addAppointment(appointment);
                }
                else if(doc.validateAndAdd(appointment)==1)
                    outputStream.writeInt(1);
                else if(doc.validateAndAdd(appointment)==0)
                    outputStream.writeInt(0);
                else if(doc.validateAndAdd(appointment)==3)
                    outputStream.writeInt(3);
                else if(doc.validateAndAdd(appointment)==4){
                    outputStream.writeInt(4);
                }
            }
        }
    }
    public static void DeleteAppointment() throws IOException {
        List<DayOfWeek> list = new ArrayList<DayOfWeek>();
        int DocID = inputStream.readInt();
        String Day = inputStream.readUTF();
        if(Day=="0"){
            list.add(DayOfWeek.SATURDAY);
            list.add(DayOfWeek.SUNDAY);
            list.add(DayOfWeek.MONDAY);
            list.add(DayOfWeek.TUESDAY);
            list.add(DayOfWeek.THURSDAY);
            list.add(DayOfWeek.WEDNESDAY);
            list.add(DayOfWeek.FRIDAY);
        }
        else{
            String []arr = Day.split(" ");
            String Sun = "Sun";
            for (int i = 0; i < arr.length; i++) {
                switch(arr[i].toUpperCase()){
                    case "SUN":
                        list.add(DayOfWeek.SUNDAY);
                        break;
                    case "MON":
                        list.add(DayOfWeek.MONDAY);
                        break;
                    case "TUE":
                        list.add(DayOfWeek.TUESDAY);
                        break;
                    case "WED":
                        list.add(DayOfWeek.WEDNESDAY);
                        break;
                    case "THU":
                        list.add(DayOfWeek.THURSDAY);
                        break;
                    case "FRI":
                        list.add(DayOfWeek.FRIDAY);
                        break;
                    case "SAT":
                        list.add(DayOfWeek.SATURDAY);
                        break;
                }
                //list.set(i, DayOfWeek.valueOf(arr[i]));
                //list.add(DayOfWeek.valueOf(arr[i]));
            }
        }

        for (Doctor Doc : doctors) {
            if(DocID==0){
                Doc.DeleteDay(list);
            }
            else if(Doc.getId() == DocID)
                Doc.DeleteDay(list);
        }
    }
    public static String printAllDatabase() {
        String x="";
        for(Doctor doc : doctors){
            x+=doc.toString();
            System.out.println(doc);
        }
        for(Patient pat : patients){
            x+=pat.toString();
            System.out.println(pat);
        }
        for(Patient patient : patients){
            for(Visit visit : patient.getPreviousVisits()){
                x+=visit;
                System.out.println(visit);
            }
        }
       return x;

    }
    public static void AddVisitInformation(){
        try {
            String patientID = inputStream.readUTF();
            String visitDesc = inputStream.readUTF();
            float visitCost = inputStream.readFloat();
            float visitPaid = inputStream.readFloat();
            if(!checkPatientId(patientID)){
                outputStream.writeUTF(Client.RED+"patient ID is Invalid"+Client.RESET);
            }
            else {
                outputStream.writeUTF(Client.GREEN+"visit added!"+Client.RESET);
                for (Patient patient:patients) {
                    if(patient.getID() == Integer.parseInt(patientID)){
                        Visit visit = new Visit(visitDesc, visitCost);
                        patient.getPreviousVisits().add(visit);
                        patient.setOwedBalance(visitCost);
                        patient.setTotalPaid(visitPaid);
                    break;
                        }
                    }
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static boolean checkPatientId(String patientID){
        try {
            Integer.parseInt(patientID);
        }catch (NumberFormatException ex){
            return true;
        }
        for(Patient patient:patients){
            if(patient.getID()==Integer.parseInt(patientID)){
                return true;
            }
        }
        return false;
    }
    public static boolean checkDoctorId(String doctorID){
        try {
            Integer.parseInt(doctorID);
        }catch (NumberFormatException ex){
            return true;
        }
        for(Doctor doctor:doctors){
            if(doctor.getId()==Integer.parseInt(doctorID)){
                return true;
            }
        }
        return false;
    }
    public static boolean isTimeValid(String time){
        Pattern pattern=Pattern.compile("\\d{1,2}:\\d{1,2}");
        if(!time.contains(":") || !pattern.matcher(time).find()){
            return false;
        }

        String []timeParts=time.split(":");
        int TimeHours=Integer.parseInt(timeParts[0]);
        int TimeMinutes=Integer.parseInt(timeParts[1]);

        return (TimeHours>=0 && TimeHours<24 && TimeMinutes>=0 && TimeMinutes<=59);
    }

}
