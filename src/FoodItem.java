public class FoodItem {

    private String foodName;
    private int grams;
    private int calories;
    private float ratio;
    public FoodItem(String foodname, int grams, int calories) {
        this.foodName = new String(foodname);
        this.grams = grams;
        this.calories = calories;
        this.ratio = (float) grams / calories;
    }
    
    public String getFoodName() {
        return foodName;
    }
    public int getGrams() {
        return grams;
    }
    public int getCalories() {
        return calories;
    }
    public float getRatio() {
        return ratio;
    }
}