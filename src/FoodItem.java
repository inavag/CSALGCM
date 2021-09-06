/** 
 * This class is responsible the creation of the object FoodItem, which
 * will be used later on as type of Arraylist that stores the information 
 * about an item. 
 * 
 */


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
    /**
     * This function gets the name of a food portion. 
     * @return the string variable.  
     */
    public String getFoodName() {
        return foodName;
    }
    /**
     * This function gets the number of grams of a food portion. 
     * @return the integer variable.  
     */
    public int getGrams() {
        return grams;
    }
     /**
     * This function gets the number of calories of a food portion. 
     * @return the integer variable. 
     */
    public int getCalories() {
        return calories;
    }
    /**
     * This function gets the ratio (Grams / calories) of a food portion.
     * @return the float variable. 
     */
    public float getRatio() {
        return ratio;
    }
}