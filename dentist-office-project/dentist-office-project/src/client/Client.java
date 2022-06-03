package client;

import org.w3c.dom.ls.LSOutput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Client {
    public static Socket socket;
    public static Scanner scanner;
    public static DataInputStream inputStream;
    public static DataOutputStream outputStream;
    static public String yesOrNo = "";
    static public String RED="\033[0;101m";
    static public String GREEN="\033[0;102m";
    static public String RESET="\033[0m";
    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 5000);
            scanner = new Scanner(System.in);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            yesOrNo = "";
            System.out.println(inputStream.readUTF());
            int choice = -1;
            while (choice != 7) {
                System.out.println("what do you wanna do now ?");
                System.out.println("1- add more doctors " +
                        "\n2- add more patients\n3- add appointments" +
                        "\n4- Delete an appointment\n5- print all system information" +
                        "\n6- Add visit information\n"+
                        "7- leave the system");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        outputStream.writeInt(1);
                        addDoctors();
                        break;
                    case 2:
                        outputStream.writeInt(2);
                        addPatients();
                        break;
                    case 3:
                        outputStream.writeInt(3);
                        addAppointments();
                        break;
                    case 4:
                        outputStream.writeInt(4);
                        DeleteAppointment();
                        break;
                    case 5:
                        outputStream.writeInt(5);
                        print();
                        break;
                    case 6 :
                        outputStream.writeInt(6);
                        AddVisit();
                        break;
                    default:
                        outputStream.writeInt(7);
                }
            }
            //receiving all info and deleted info after the program finishes
            System.out.println(inputStream.readUTF());
            System.out.println(inputStream.readUTF());
            System.out.println(inputStream.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void print() throws IOException{
        System.out.println(inputStream.readUTF());
    }
    public static void addDoctors() {
        try {
            System.out.println("Enter a doctor Id");
            String doctorId = scanner.nextLine();
            System.out.println("Enter a doctor name");
            String doctorName = scanner.nextLine();

            System.out.println("Enter a doctor week days. Ex. sun mon thu :");
            String doctorDays = scanner.nextLine();

            System.out.println("Enter a doctor start time :Ex 11:30");
            String doctorStartTime = scanner.nextLine();

            System.out.println("Enter a doctor end time :Ex 17:50");
            String doctorEndTime = scanner.nextLine();

            //sending name
            outputStream.writeUTF(doctorId);
            outputStream.writeUTF(doctorName);
            //sending days
            outputStream.writeUTF(doctorDays);
            //sending start time
            outputStream.writeUTF(doctorStartTime);
            //sending end time
            outputStream.writeUTF(doctorEndTime);

            System.out.println(inputStream.readUTF());

        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
    public static void addPatients() {
        try {
            System.out.println("Enter patient's name : ");
            String patientName = scanner.nextLine();

            System.out.println("Enter patient's ID : ");
            String patientID = scanner.nextLine();

            System.out.println("Enter patient's phone number : ");
            String patientPhoneNumber = scanner.nextLine();

            System.out.println("Enter patient's email : ");
            String patientEmail = scanner.next();
            //Name
            outputStream.writeUTF(patientName);
            //ID
            outputStream.writeUTF(patientID);
            //Number
            outputStream.writeUTF(patientPhoneNumber);
            //Email
            outputStream.writeUTF(patientEmail);
            System.out.println(inputStream.readUTF());
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
    public static void addAppointments() {
        try {
            System.out.println("Enter Doctors id :");
            int doctorId = scanner.nextInt();
            System.out.println("Enter patient's id :");
            int patientId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter patient's start time appointment : ex. 11:30");
            String startTimeAppointment = scanner.nextLine();

            System.out.println("Enter patient's end time appointment : ex. 11:30");
            String endTimeAppointment = scanner.nextLine();
            System.out.println("enter patient's day appointment : ");
            String appointmentDay = scanner.nextLine();
            //IDApp
            outputStream.writeInt(doctorId);
            outputStream.writeInt(patientId);
            //StartTimeApp
            outputStream.writeUTF(startTimeAppointment);
            //EndTimeApp
            outputStream.writeUTF(endTimeAppointment);
            //sending day
            outputStream.writeUTF(appointmentDay);
            int result = inputStream.readInt();
            switch (result){
                case 0:
                    System.out.println(RED+"the doctor doesn't have a work on this day"+RESET);break;
                case 1:
                    System.out.println(RED+"this time is already taken"+RESET);break;
                case 2:
                    System.out.println(GREEN+"appointment added successfully"+RESET);break;
                case 3:
                    System.out.println(RED+"appointment time is not in the doctor's schedule"+RESET);break;
                case 4:
                    System.out.println(RED+"appointment time is less than 30 minutes"+RESET);break;
            }
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
    public static void DeleteAppointment() {
        try {
            System.out.println("Enter Doctors id that you want to delete his appointments:, enter 0 to delete for all doctors ");
            int doctorId = scanner.nextInt();
            scanner.nextLine();
            outputStream.writeInt(doctorId);
            System.out.println("enter patient's day appointment : enter a 0 to delete for all days ");
            String appointmentDay = scanner.nextLine();
            outputStream.writeUTF(appointmentDay);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void AddVisit(){
        try {
            System.out.println("Enter Patient ID");
            String patientId = scanner.nextLine();
            System.out.println("Enter visit description");
            String desc = scanner.nextLine();
            System.out.println("Enter visit cost");
            float cost = scanner.nextFloat();
            System.out.println("Enter visit paid price");
            float paid=scanner.nextFloat();

            outputStream.writeUTF(patientId);
            outputStream.writeUTF(desc);
            outputStream.writeFloat(cost);
            outputStream.writeFloat(paid);
            System.out.println(inputStream.readUTF());
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
