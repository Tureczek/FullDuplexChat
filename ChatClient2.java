import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        try {
            Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 6780);
            System.out.println("Har oprettet forbindelse til chat server...");

            // Modtager fra Server
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());


            //Skriver til server prøvet med Threads
            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String message = scanner.nextLine();

                        try {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println(message);
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                }
            });


            Thread readMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {

                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            System.out.println("Serveren svare: " + bufferedReader.readLine());


                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


            sendMessage.start();
            readMessage.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
