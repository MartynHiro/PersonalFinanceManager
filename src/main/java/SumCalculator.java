import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SumCalculator {
    private void findBiggestSum(Storage storage) {

        Map<String, Integer> biggestCategory = new HashMap<>();

        biggestCategory.put("еда", (storage.getFood().stream()
                .map(x -> x.getSum())
                .mapToInt(y -> y)
                .sum()));
        biggestCategory.put("одежда", (storage.getCloth().stream()
                .map(x -> x.getSum())
                .mapToInt(y -> y)
                .sum()));
        biggestCategory.put("быт", (storage.getLife().stream()
                .map(x -> x.getSum())
                .mapToInt(y -> y)
                .sum()));
        biggestCategory.put("финансы", (storage.getFinances().stream()
                .map(x -> x.getSum())
                .mapToInt(y -> y)
                .sum()));
        biggestCategory.put("другое", (storage.getOther().stream()
                .map(x -> x.getSum())
                .mapToInt(y -> y)
                .sum()));

        for (Map.Entry<String, Integer> kv : biggestCategory.entrySet()) {

            if (kv.getValue() > storage.getMaxSum()) {
                storage.setMaxSum(kv.getValue());
                storage.setMaxCategory(kv.getKey());
            }
        }
    }

    public String formingJsonForAnswer(Storage storage) {

        findBiggestSum(storage);

        JSONObject answerTitle = new JSONObject();

        JSONObject answerBody = new JSONObject();
        answerBody.put("category", storage.getMaxCategory());
        answerBody.put("sum", storage.getMaxSum());

        answerTitle.put("maxCategory", answerBody);

        return answerTitle.toJSONString();
    }
}
