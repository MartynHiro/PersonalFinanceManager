import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989)) {

            System.out.println("Сервер запущен");

            Storage storage = new Storage(); //создаем объект хранилища

            while (true) { //постоянная работа сервера

                try (Socket clientSocket = serverSocket.accept(); // ждем подключения клиента
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
                {
                    writer.println("Вы подключены!");

                    System.out.println(reader.readLine());

                    String jsonFromClient = reader.readLine();//получили строку в формате json от клиента
                    storage.selectCategory(jsonFromClient);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
