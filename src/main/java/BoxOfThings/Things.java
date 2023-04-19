package BoxOfThings;

public abstract class Things {
    protected String title;
    protected String date;
    protected int sum;

    public Things(String title, String date, int sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }
}
