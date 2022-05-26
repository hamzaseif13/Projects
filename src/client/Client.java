package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",5000);
            Scanner scanner = new Scanner(System.in);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            String yesOrNo="";

            do{
                System.out.println("Enter a doctor name");
                String doctorName= scanner.nextLine();

                System.out.println("Enter a doctor week days. Ex. sun mon thu :");
                String doctorDays= scanner.nextLine();

                System.out.println("Enter a doctor start time :Ex 11:30");
                String doctorStartTime= scanner.nextLine();

                System.out.println("Enter a doctor end time :Ex 17:50");
                String doctorEndTime= scanner.nextLine();

                //sending name
                outputStream.writeUTF(doctorName);
                //sending days
                outputStream.writeUTF(doctorDays);
                //sending start time
                outputStream.writeUTF(doctorStartTime);
                //sending end time
                outputStream.writeUTF(doctorEndTime);
                System.out.println("Do you want to add another Doctor ? Y/N");
                yesOrNo=scanner.next();
                //sending yes or no
                outputStream.writeUTF(yesOrNo);
            }
            while (yesOrNo.equalsIgnoreCase("Y"));

            do {
                System.out.println("Enter patient's name : ");
                scanner.nextLine();
                String patientName=scanner.nextLine();

                System.out.println("Enter patient's ID : ");
                int patientID=scanner.nextInt();

                System.out.println("Enter patient's phone number : ");
                int patientPhoneNumber=scanner.nextInt();

                System.out.println("Enter patient's email : ");
                String patientEmail=scanner.next();
                //Name
                outputStream.writeUTF(patientName);
                //ID
                outputStream.writeInt(patientID);
                //Number
                outputStream.writeInt(patientPhoneNumber);
                //Email
                outputStream.writeUTF(patientEmail);


                System.out.println("Enter patient's start time appointment : ex. 11:30");
                String startTimeAppointment= scanner.nextLine();

                System.out.println("Enter patient's end time appointment : ex. 11:30");
                String endTimeAppointment= scanner.nextLine();

                System.out.println("enter patient's date appointment : ");


                //IDApp
                outputStream.writeInt(patientID);
                //StartTimeApp
                outputStream.writeUTF(startTimeAppointment);
                //EndTimeApp
                outputStream.writeUTF(endTimeAppointment);




                System.out.println("Do you want to add another Patient ? Y/N");
                yesOrNo=scanner.next();
                //sending yes or no
                outputStream.writeUTF(yesOrNo);


            }while(yesOrNo.equalsIgnoreCase("Y"));

            do{

            }while()






        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
