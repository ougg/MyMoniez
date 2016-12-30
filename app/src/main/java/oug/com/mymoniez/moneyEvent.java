package oug.com.mymoniez;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoneyEvent {
    private int eventId;
    private double value;
    private long dateMillis;
    private Category category;
    private String description;

    public enum Category{ALCOHOL,FOOD,CLOTHES,BOOKS,GROCERIES,ENTERTAINMENT,INCOME,OTHER}

    public MoneyEvent(int eventId, double value, long dateMillis, Category category, String description) {
        this.eventId = eventId;
        this.value = value;
        this.dateMillis = dateMillis;
        this.category = category;
        this.description = description;
    }

    public static int getCategoryDrawable(Category category){
        switch(category){
            case ALCOHOL:
                return R.drawable.alcohol;
            case FOOD:
                return R.drawable.food;
            case CLOTHES:
                return R.drawable.clothes;
            case BOOKS:
                return R.drawable.books;
            case GROCERIES:
                return R.drawable.groceries;
            case ENTERTAINMENT:
                return R.drawable.entertainment;
            case INCOME:
                return R.drawable.income;
            case OTHER:
                return R.drawable.other;
            default:
                return R.drawable.other;
        }
    }
    public static String getCategoryName(Category category){
        switch(category){
            case ALCOHOL:
                return "Alcohol";
            case FOOD:
                return "Food";
            case CLOTHES:
                return "Clothes";
            case BOOKS:
                return "Books";
            case GROCERIES:
                return "Groceries";
            case ENTERTAINMENT:
                return "Entertainment";
            case INCOME:
                return "Income";
            case OTHER:
                return "Other";
            default:
                return "Other";
        }
    }
    public static String getValueString(double value){
        return String.format(Locale.US,"%.2f",value);
    }
    public static String getDateString(long dateMillis){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyy hh:mm");
        Date date = new Date();
        date.setTime(dateMillis);
        return format.format(date);
    }
    public int getEventId() {return eventId;}
    public void setEventId(int eventId) {this.eventId = eventId;}
    public double getValue() {return value;}
    public void setValue(double value) {this.value = value;}
    public Category getCategory() {return category;}
    public void setCategory(Category category) {this.category = category;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public long getDateMillis() {return dateMillis;}
    public void setDateMillis(long dateMillis) {this.dateMillis = dateMillis;}
}
