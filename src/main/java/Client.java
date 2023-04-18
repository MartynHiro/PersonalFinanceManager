import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8989;

    public static final String USER_NAME = "Сергей";

    public static void main(String[] args) {

        Purchase purchase = new Purchase();

        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println(reader.readLine());

            writer.println(USER_NAME + " подключился");

            userInput(purchase); //пользовательский ввод

            //формируем json строку из введенных данных для отправки ее серверу
            String jsonLine = jsonFileGeneration(purchase);

            writer.println(jsonLine);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String jsonFileGeneration(Purchase purchase) throws IOException {
        JSONObject purchaseJsonFile = new JSONObject();
        purchaseJsonFile.put("title", purchase.getTitle());
        purchaseJsonFile.put("date", purchase.getDate());
        purchaseJsonFile.put("sum", purchase.getSum());

//        System.out.println(purchaseJsonFile.toJSONString());

        //нет необходимости в самом файле, так что формируем просто строку в формате json
        return purchaseJsonFile.toJSONString();
    }

    private static void userInput(Purchase purchase) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите title");
            purchase.setTitle(scanner.nextLine());

            System.out.println("Введите дату в формате yyyy.mm.dd");
            purchase.setDate(scanner.nextLine());

            System.out.println("Введите сумму траты");
            purchase.setSum(scanner.nextInt());

//            System.out.println(purchase);

        } catch (InputMismatchException e) {
            System.out.println("Нужно вводить цифры");
        }
    }
}