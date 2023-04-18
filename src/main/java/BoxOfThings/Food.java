package BoxOfThings;

public class Food extends Things{
    public Food(String title, String date, long sum) {
        super(title, date, sum);
    }

    @Override
    public String toString() {
        return "Food{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                '}';
    }
}
