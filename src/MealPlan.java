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
            if (gui.getGreedy().isSelected()) {
                GreedyAlgo(log);
            } else if (gui.getDynamic().isSelected()) {
                dynamicAlgo(log);
            }
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
        gui.getTextArea().setText("Cleared the items list!\n");
    }

    /**
     * Clears the input fields once the add button is pressed. 
     */
    private void clearField() {
        for(int i = 1; i < 4; i++) {
            gui.getInput(i).setText("");
        }
    }

    /**
     * Sorts the items list based on the ratio of the given FoodItem
     * @param items
     */
    private void sortList(ArrayList<FoodItem> items) {
        Collections.sort(items, new Comparator<FoodItem>(){
            @Override
            public int compare(FoodItem f1, FoodItem f2) {
                return Float.compare(f2.getRatio(), f1.getRatio());
            }
        });
    }
    /**
     * Solves the fractional knapsack problem using the greedy algorithm method
     * @param log is the area where the text will be displayed in the GUI
     */
    private void GreedyAlgo(JTextArea log) {
        float weight = 0;
        sortList(items);

        int[] v = new int[items.size()];
        int[] w = new int[items.size()];
        float[] x = new float[items.size()];

        String[] foodNames = new String[items.size()];
        int n = items.size();
        int i = 0;
        //Instantiate the v, w, and foodName arrays
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
        log.append("\n\nGreedy Algorithm Output: \n\n");
        float totalGrams = 0;
        float totalCalories = 0;
        String text;
        //Print the output in the log if the ratio is > 0
        for (float r : x) {
            if (r != 0) {
                text = String.format("%s: %6.2f g | %6.2f calories\n", foodNames[i], (v[i]*r), (w[i]*r));
                log.append(text);
                totalGrams+=(v[i]*r);
                totalCalories+=(w[i]*r);
            }
            i++;
        }
        text = String.format("\nTotal Grams: %5.2f g \n", totalGrams);
        log.append(text);
        text = String.format("Total Calories: %5.2f calories \n", totalCalories);
        log.append(text);
    }

    /**
     * This method utilizes the dynamic programming to solve the
     * knapsack problem.
     * @param log is the area where the text will be displayed in the GUI
     */
    private void dynamicAlgo(JTextArea log) {
        int n = items.size();
        int[] v = new int[n];
        int[] w = new int[n];
        int[][] cost = new int[n + 1][W+1];

        int i = 0;
        //Initialize the v and w arrays
        for(FoodItem f : items) {
            v[i] = f.getGrams();
            w[i] = f.getCalories();
            i++;
        }

        //Initializes the 1st row and column to 0
        for (i = 0; i <= W; i++) {
            cost[0][i] = 0;
        }
        for (i = 0; i <= n; i++) {
            cost[i][0] = 0;
        }

        for (i = 1; i <= n; i++) {
            for (int k = 1; k <= W; k++) {
                if(w[i-1] > k) {
                    cost[i][k] = cost[i-1][k];
                } else {
                    if (v[i-1]+cost[i-1][k-w[i-1]] > cost[i-1][k]) {
                        cost[i][k] = v[i-1] + cost[i-1][k-w[i-1]];
                    } else {
                        cost[i][k] = cost[i-1][k];
                    }
                }
            }
        }
        dynamicItems(log, cost, n, w, v);
    }

    /**
     * This method determines the items included in the result of the dynamic programming.
     * @param log is the area where the text will be displayed in the GUI.
     * @param cost the matrix where the different outputs are located.
     * @param n is the number of items in the list.
     * @param w is the weight array.
     * @param v is the value array.
     */
    private void dynamicItems(JTextArea log, int[][] cost, int n, int w[], int v[]) {
        int i = n;
        int j = W;
        int totalCal = 0;
        int totalGram = 0;
        log.append("\n\nDynamic Programming Output: \n\n");
        while (i > 0 && j > 0) {
            if (cost[i][j] != cost[i-1][j]) {
                FoodItem f = items.get(i-1);
                String text = String.format("%s: %6.2f g | %6.2f calories\n", f.getFoodName(), (float)f.getGrams(), (float)f.getCalories());
                totalCal += f.getCalories();
                totalGram += f.getGrams();
                log.append(text);
                j = j-w[i-1];
            }
            i = i-1;
        }
        
        String total = String.format("\nTotal Grams: %5d g\n", totalGram);
        log.append(total);
        total = String.format("Total Calories: %5d calories\n", totalCal);
        log.append(total);
    }
    public JTextArea getTextArea() {
        return gui.getTextArea();
    }
    public static void main(String[] args) {

        //remove comment to test clear button 
        //bring back comment after testing clear button
        MealPlan app = new MealPlan();
        FoodItem one = new FoodItem("Chicken", 100, 250);
        FoodItem two = new FoodItem("Pork", 250, 300);
        FoodItem three = new FoodItem("Pasta", 50, 125);
        FoodItem four = new FoodItem("Apple", 25, 100);

        app.addItem(one, app.getTextArea());
        app.addItem(two, app.getTextArea());
        app.addItem(three, app.getTextArea());
        app.addItem(four, app.getTextArea());
    }

}
