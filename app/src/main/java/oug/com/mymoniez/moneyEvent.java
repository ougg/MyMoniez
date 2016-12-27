package oug.com.mymoniez;


public class MoneyEvent {
    private int id;
    private double value;
    private long dateMillis;
    private Category category;
    private String description;

    public enum Category{ALCOHOL,FOOD,CLOTHES,BOOKS,GROCERIES,ENTERTAINMENT,INCOME}


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public double getValue() {return value;}
    public void setValue(double value) {this.value = value;}
    public Category getCategory() {return category;}
    public void setCategory(Category category) {this.category = category;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public long getDateMillis() {return dateMillis;}
    public void setDateMillis(long dateMillis) {this.dateMillis = dateMillis;}
}
