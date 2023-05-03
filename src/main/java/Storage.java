import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Storage {

    public static final String TSV_FILE = "categories.tsv";

    private final List<String[]> linesFromTsv;

    private String maxCategory;

    private int maxSum;

    public Storage() {
        this.linesFromTsv = openTsvFile();
    }

    private List<String[]> getAllLinesFromTsv() {
        return linesFromTsv;
    }

    private Map<String, Integer> listOfPurchases = new HashMap<>();

    public Map<String, Integer> getListOfPurchases() {
        return listOfPurchases;
    }

    private List<String[]> openTsvFile() {

        ArrayList<String[]> data = new ArrayList<>();

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(TSV_FILE))) {
            String line;

            while ((line = TSVReader.readLine()) != null) {

                String[] lineItems = line.split("\t");
                data.add(lineItems);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void selectCategory(String jsonFromClient) {

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(jsonFromClient);
            JSONObject purchaseFromJson = (JSONObject) obj;

            //достаем название/дату/сумму предмета из json от клиента
            String titleFromJson = (String) purchaseFromJson.get("title");
            String dateFromJson = (String) purchaseFromJson.get("date");
            Long sumFromJson = (Long) purchaseFromJson.get("sum");

            int sum = sumFromJson.intValue();

            Optional<String[]> optionalLineFromTsv = getAllLinesFromTsv().stream()
                    //смотрим в какую категорию можно положить покупку
                    .filter(item -> item[0].equals(titleFromJson))
                    .findAny();   //если есть хоть одно совпадение, он его достанет

            //Optional для проверки есть ли совпадение
            if (optionalLineFromTsv.isPresent()) { //если есть, то достаем нужную категорию
                String[] lineFromTsv = optionalLineFromTsv.get();

                String categoryFromTsv = lineFromTsv[1];

//если такая категория уже есть в мапе, то достаем ее и добавляем сумму покупки
                if (listOfPurchases.containsKey(categoryFromTsv)) {
                    listOfPurchases.put(categoryFromTsv, listOfPurchases.get(categoryFromTsv) + sum);

//если такой категории еще не сохранено, то просто добавляем ее в мапу
                } else {
                    listOfPurchases.put(categoryFromTsv, sum);
                }

            } else { //или присваиваем "другое" и поступаем так же, как если бы нашли категорию в tsv
                String categoryFromTsv = "другое";

                if (listOfPurchases.containsKey(categoryFromTsv)) {
                    listOfPurchases.put(categoryFromTsv, listOfPurchases.get(categoryFromTsv) + sum);

                } else {
                    listOfPurchases.put(categoryFromTsv, sum);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getMaxCategory() {
        return maxCategory;
    }

    public int getMaxSum() {
        return maxSum;
    }

    public void setMaxCategory(String maxCategory) {
        this.maxCategory = maxCategory;
    }

    public void setMaxSum(int maxSum) {
        this.maxSum = maxSum;
    }
}
