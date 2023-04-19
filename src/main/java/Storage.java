import BoxOfThings.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Storage {
    public static final String TSV_FILE = "categories.txt";
    private List<Food> food;
    private List<Cloth> cloth;
    private List<Life> life;
    private List<Finances> finances;
    private List<Other> other;
    private List<String[]> categoriesFromTsv;
    private int maxSum = 0;
    private String maxCategory = null;

    public List<String[]> getCategoriesFromTsv() {
        return categoriesFromTsv;
    }

    public Storage() {
        this.food = new ArrayList<>();
        this.cloth = new ArrayList<>();
        this.life = new ArrayList<>();
        this.finances = new ArrayList<>();
        this.other = new ArrayList<>();
        this.categoriesFromTsv = openTsvFile();
    }

    //раскрываем tsv файл
    public List<String[]> openTsvFile() {

        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(TSV_FILE))) {
            String line;

            while ((line = TSVReader.readLine()) != null) {

                String[] lineItems = line.split("\t");
                data.add(lineItems);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public void selectCategory(String jsonFromClient) throws IOException {

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(jsonFromClient);
            JSONObject purchaseFromJson = (JSONObject) obj;

            //достаем название/дату/сумму предмета из json от клиента
            String titleFromJson = (String) purchaseFromJson.get("title");
            String dateFromJson = (String) purchaseFromJson.get("date");
            Long sumFromJson = (Long) purchaseFromJson.get("sum");

            int sum = sumFromJson.intValue();

            Optional<String[]> optionalLineFromTsv = getCategoriesFromTsv().stream()
                    //смотрим в какую категорию можно положить покупку
                    .filter(item -> item[0].equals(titleFromJson))
                    .findAny();   //если есть хоть одно совпадение, он его достанет

            //Optional для проверки есть ли совпадение
            if (optionalLineFromTsv.isPresent()) { //если есть, то достаем нужную категорию
                String[] lineFromTsv = optionalLineFromTsv.get();

                String categoryFromTsv = lineFromTsv[1];

                createThingsObject(titleFromJson, dateFromJson, sum, categoryFromTsv);

            } else { //или присваиваем другое
                String categoryFromTsv = "другое";
                createThingsObject(titleFromJson, dateFromJson, sum, categoryFromTsv);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void createThingsObject(String titleFromJson, String dateFromJson, int sumFromJson, String categoryFromTsv) {
        switch (categoryFromTsv) { //создаем подходящий объект
            case "еда" -> food.add(new Food(titleFromJson, dateFromJson, sumFromJson));

            case "одежда" -> cloth.add(new Cloth(titleFromJson, dateFromJson, sumFromJson));

            case "быт" -> life.add(new Life(titleFromJson, dateFromJson, sumFromJson));

            case "финансы" -> finances.add(new Finances(titleFromJson, dateFromJson, sumFromJson));

            default -> other.add(new Other(titleFromJson, dateFromJson, sumFromJson));
        }
    }

    public void setMaxSum(int maxSum) {
        this.maxSum = maxSum;
    }

    public void setMaxCategory(String maxCategory) {
        this.maxCategory = maxCategory;
    }

    public List<Food> getFood() {
        return food;
    }

    public List<Cloth> getCloth() {
        return cloth;
    }

    public List<Life> getLife() {
        return life;
    }

    public List<Finances> getFinances() {
        return finances;
    }

    public List<Other> getOther() {
        return other;
    }

    public int getMaxSum() {
        return maxSum;
    }

    public String getMaxCategory() {
        return maxCategory;
    }

}
