import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            ServerSocket serverSocket = new ServerSocket(6780);
            System.out.println("Klar til at modtage chat klient...");
            Socket socket = serverSocket.accept();

            // Serveren modtager besked fra klienten her.
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());



            Thread readMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {

                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            System.out.println("Chat klient har skrevet: " + bufferedReader.readLine());

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


            // Server sender til klienten
            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String message = scanner.nextLine();

                        try {

                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println(message);


                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


            readMessage.start();
            sendMessage.start();





        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
