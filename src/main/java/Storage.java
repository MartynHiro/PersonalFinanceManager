import BoxOfThings.*;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Storage {
    public static final String TSV_FILE = "categories.tsv";
    private List<Food> food;
    private List<Cloth> cloth;
    private List<Life> life;
    private List<Finances> finances;
    private List<Other> other;
    private List<String[]> categoriesFromTsv;

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

        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("n");
        TsvParser parser = new TsvParser(settings);
        List<String[]> allRows = parser.parseAll(new File(TSV_FILE));

        return allRows;



//        ArrayList<String[]> data = new ArrayList<>();
//        try (BufferedReader TSVReader = new BufferedReader(new FileReader(TSV_FILE))) {
//            String line;
//
//            while ((line = TSVReader.readLine()) != null) {
//                String[] lineItems = line.split("\t");
//                data.add(lineItems);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return data;
    }

    public void selectCategory(String jsonFromClient) throws IOException {

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(jsonFromClient);
            JSONObject purchaseFromJson = (JSONObject) obj;

            //достаем название/дату/сумму предмета из json от клиента
            String titleFromJson = (String) purchaseFromJson.get("title");
            String dateFromJson = (String) purchaseFromJson.get("date");
            long sumFromJson = (long) purchaseFromJson.get("sum");

            Optional<String[]> optionalLineFromTsv = getCategoriesFromTsv().stream()
                    //смотрим в какую категорию можно положить покупку
                    .filter(item -> item[0].equals(titleFromJson))
                    .findAny();   //если есть хоть одно совпадение, он его достанет

            //Optional для проверки есть ли совпадение
            if (optionalLineFromTsv.isPresent()) { //если есть, то достаем нужную категорию
                String[] lineFromTsv = optionalLineFromTsv.get();
                String categoryFromTsv = lineFromTsv[1];

                createThingsObject(titleFromJson, dateFromJson, sumFromJson, categoryFromTsv);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void createThingsObject(String titleFromJson, String dateFromJson, long sumFromJson, String categoryFromTsv) {
        switch (categoryFromTsv) { //создаем подходящий объект
            case "еда" -> food.add(new Food(titleFromJson, dateFromJson, sumFromJson));

            case "одежда" -> cloth.add(new Cloth(titleFromJson, dateFromJson, sumFromJson));

            case "быт" -> life.add(new Life(titleFromJson, dateFromJson, sumFromJson));

            case "финансы" -> finances.add(new Finances(titleFromJson, dateFromJson, sumFromJson));

            default -> other.add(new Other(titleFromJson, dateFromJson, sumFromJson));
        }
    }
}
