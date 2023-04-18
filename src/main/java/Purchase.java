import org.apache.commons.lang3.StringUtils;

public class Purchase {
    private String title;
    private String date;
    private int sum;

    public Purchase() {
        this.title = null;
        this.date = null;
        this.sum = 0;
    }

    public void setTitle(String title) {

        if (!StringUtils.isNumeric(title)) {

            this.title = title.toLowerCase(); //приравниваем к нижнему регистру как у нас записано в файле
        } else {
            System.out.println("Можно вводить только буквы, поле останется null");
        }
    }

    public void setDate(String date) {

        try {
            String checkNumbers = date.replace(".", "");
            int month = Integer.parseInt(date.substring(5, 7));

            int day = Integer.parseInt(date.substring(8));

            //проверяем корректность ввода формата даты
            if (date.charAt(4) == '.' &&
                    date.charAt(7) == '.' &&
                    StringUtils.isNumeric(checkNumbers) &&
                    month > 0 &&
                    month <= 12 &&
                    day > 0 &&
                    day <= 31) {

                this.date = date;
            } else {
                System.out.println("Некорректный ввод, поле останется null");
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неверная длина даты, поле останется null");
        }
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "\ntitle= " + title +
                "\n, date= " + date +
                "\n, sum= " + sum +
                "\n}";
    }
}
