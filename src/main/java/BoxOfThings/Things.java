package BoxOfThings;

public abstract class Things {
    protected String title;
    protected String date;
    protected long sum;

    public Things(String title, String date, long sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }
}
