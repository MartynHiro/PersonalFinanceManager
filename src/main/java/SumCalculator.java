import org.json.simple.JSONObject;

import java.util.Map;

public class SumCalculator {

    int maxSum = 0;
    String maxCategory;

    private void findBiggestSum(Storage storage) {

        Map<String, Integer> listOfPurchases = storage.getListOfPurchases();

        for (Map.Entry<String, Integer> entry: listOfPurchases.entrySet()) {
            if (entry.getValue() > maxSum) {

                maxSum = entry.getValue();
                maxCategory = entry.getKey();
            }
        }
        storage.setMaxSum(maxSum);
        storage.setMaxCategory(maxCategory);
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
