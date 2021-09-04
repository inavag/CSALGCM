import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MealPlan {
    private Gui gui;
    private ArrayList<FoodItem> items;
    private int W; //Calories
    public MealPlan() {
        items = new ArrayList<>();
        this.W = 0;
        initGUI();
    }
    /**
     * Initializes the frontend elements of the app. 
     */
    private void initGUI() {
        gui = new Gui();
        JTextField weight = gui.getInput(0);
        JTextField foodInput = gui.getInput(1);
        JTextField gramsInput = gui.getInput(2);
        JTextField calInput = gui.getInput(3);
        JTextArea log = gui.getTextArea();
        JButton confirm = gui.getConfirm();
        JButton add = gui.getAdd();
        JButton set = gui.getSet();
        JButton clear = gui.getClear();

        
        set.addActionListener(e -> {
            W = Integer.parseInt(weight.getText());
            log.append("Target Calories: " + W +" set!\n");
        });

        add.addActionListener(e -> {
            String foodName = foodInput.getText();
            int grams = Integer.parseInt(gramsInput.getText());
            int calories = Integer.parseInt(calInput.getText());
            FoodItem item = new FoodItem(foodName, grams, calories);
            addItem(item, log);
        });

        confirm.addActionListener(e -> {
            GreedyAlgo(log);
        });

        clear.addActionListener(e -> {
            clear();
        });
        
    }
    /**
     * Adds the item entered by the user into the list.
     */
    private void addItem(FoodItem item, JTextArea log) {
        String text = item.getFoodName() + " " + item.getGrams() + "g " + item.getCalories() + " calories added to the list!\n";
        items.add(item);
        log.append(text);
        clearField();
    }

    /**
     * Clears arraylist and text area 
     */

    private void clear() {
        items.clear(); 
        gui.getTextArea().setText("");
    }

    /**
     * Clears the input fields once the add button is pressed. 
     */
    private void clearField() {
        for(int i = 1; i < 4; i++) {
            gui.getInput(i).setText("");
        }
    }
    private void sortList(ArrayList<FoodItem> items) {
        Collections.sort(items, new Comparator<FoodItem>(){
            @Override
            public int compare(FoodItem f1, FoodItem f2) {
                return Float.compare(f2.getRatio(), f1.getRatio());
            }
        });
    }
    public void GreedyAlgo(JTextArea log) {
        float weight = 0;
        sortList(items);

        int[] v = new int[items.size()];
        int[] w = new int[items.size()];
        float[] x = new float[items.size()];

        String[] foodNames = new String[items.size()];
        int n = items.size();
        int i = 0;
        for (FoodItem f : items) {
            v[i] = f.getGrams();
            w[i] = f.getCalories();
            foodNames[i] = new String(f.getFoodName());
            i++;
        }
        i = 0;
        while (i < n && weight < W) {     
            if (weight + w[i] <= W) {
                x[i] = 1;
            } else {
                x[i] = ((W - weight) /(float) w[i]);  
            }
            weight = weight + (x[i] * w[i]);
            i++;
        }

        i = 0;
        log.append("\n\nOutput: \n");
        float totalGrams = 0;
        float totalCalories = 0;
        String text;
        for (float r : x) {
            if (r != 0) {
                text = String.format("%s: %.2f g | %.2f calories\n", foodNames[i], (v[i]*r), (w[i]*r));
                log.append(text);
                totalGrams+=(v[i]*r);
                totalCalories+=(w[i]*r);
            }
            i++;
        }
        text = String.format("Total Grams: %.2f g |\n", totalGrams);
        log.append(text);
        text = String.format("Total Calories: %.2f calories |\n", totalCalories);
        log.append(text);
    }

    public JTextArea getTextArea() {
        return gui.getTextArea();
    }
    public static void main(String[] args) {
        
        new MealPlan();

        //remove comment to test clear button 
        //bring back comment after testing clear button
        // MealPlan app = new MealPlan();
        // FoodItem one = new FoodItem("Chicken", 100, 250);
        // FoodItem two = new FoodItem("Pork", 250, 300);
        // FoodItem three = new FoodItem("Pasta", 50, 125);
        // FoodItem four = new FoodItem("Apple", 25, 100);

        // app.addItem(one, app.getTextArea());
        // app.addItem(two, app.getTextArea());
        // app.addItem(three, app.getTextArea());
        // app.addItem(four, app.getTextArea());
    }

}
