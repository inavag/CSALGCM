import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
  * Team 4 
  * CRUZADA, CARLA JEANNINE ORTEGA 
  * EVANGELISTA, INA PATRICIA DE LIMA 
  * FRANCISCO, PIOLO JOSE TAN 
  * LOYOLA, RAINIER MAGNO
  * 
  * Stand-alone application that calculates the recommended amount of grams, together with its associated calories, that a certain food portion should only have 
  * to limit the number of calories of a certain meal. This will then help in achieving the target calories set by the user for a planned meal. 
  * 
  * The problem is reduced as a Knapsack Problem, and it was solved through dynamic programming and using a greedy algorithm.
  * 
  * Greedy Algorithm is based from what has been taught in class. 
  * 
  * Reference for the dynamic programming solution: https://www.codesdope.com/course/algorithms-knapsack-problem/
  * The functions mentioned in the comments for the said algorithm are also from this reference. 
  */

public class MealPlan {
    private Gui gui;
    private ArrayList<FoodItem> items;
    private int W; //Target Calories

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

        //The weight is set once the SET button is pressed
        set.addActionListener(e -> {
            //Displays an error prompt if the input for the Weight is invalid
            try {
                W = Integer.parseInt(weight.getText());
                log.append("Target Calories: " + W +" set!\n");
                gui.getInput(0).setText("");
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Invalid Input.", "Inane error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Adds the item into the list when the ADD button is pressed
        add.addActionListener(e -> {
            String foodName = foodInput.getText();
            //Displays an error prompt if the input for the grams or calories is invalid
            try {
                int grams = Integer.parseInt(gramsInput.getText());
                int calories = Integer.parseInt(calInput.getText());
                FoodItem item = new FoodItem(foodName, grams, calories);
                addItem(item, log);
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Invalid Input.", "Inane error", JOptionPane.ERROR_MESSAGE);
            }

        });

        //Performs the operation needed based on the chosen algorithm
        confirm.addActionListener(e -> {
            if (gui.getGreedy().isSelected()) {
                GreedyAlgo(log);
            } else if (gui.getDynamic().isSelected()) {
                dynamicAlgo(log);
            }
        });

        //Clears the textarea and the list
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
        //whle the number of items and the maximum weight has not been reached
        while (i < n && weight < W) {     
            if (weight + w[i] <= W) { // if adding the current weight of the item to the current total weight would not exceed the maximum weight
                x[i] = 1; //include the whole item 
            } else {
                x[i] = ((W - weight) /(float) w[i]);  // get the fractional part of the item that would accomodate the maximum weight. 
            }
            weight = weight + (x[i] * w[i]); //update total weight 
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
      * Solves the 0-1 knapsack problem using dynamic programming.
      * @param log is the area where the text will be displayed in the GUI
      */
    private void dynamicAlgo(JTextArea log) {
        int n = items.size();
        int[] v = new int[n];
        int[] w = new int[n];
        int[][] cost = new int[n + 1][W+1]; //would contain the optimal values for the first i tems and weight limit of j 

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

         //iterate through the number of items  
        for (i = 1; i <= n; i++) {
            //iterate through the weights 
            for (int k = 1; k <= W; k++) {
                if(w[i-1] > k) { // if the current weight of the item is greater than k, we cannot add it to the knapsack 
                    cost[i][k] = cost[i-1][k]; // only left with the i - 1 items and the weight limit W, leave the item
                } else { // we can add the item to the knapsack
                    
                    /**
                     * Making the decision to add or leave the item depends on whichever has the maximum value between the function F(i-1, W) and F(i-1, W-wi) + vi.
                     * if the function F(i-1, W-wi) + vi is greater, the item will be picked, making the sum the optimal value of that item. 
                     * Otherwise, the item will not be included. 
                     * */
                    if (v[i-1]+cost[i-1][k-w[i-1]] > cost[i-1][k]) {
                        cost[i][k] = v[i-1] + cost[i-1][k-w[i-1]]; //pick the item 
                    } else {
                        cost[i][k] = cost[i-1][k]; // only left with the i - 1 items and the weight limit W, leave the item
                    }
                }
            }
        }
        dynamicItems(log, cost, n, w, v); //finalize the output of the matrix cost 
    }

   /**
     * This method determines the items included in the result of the dynamic programming. It does not include the
     * duplicate optimal value between two rows since the duplicate optimal value has no effect in the total optimized value. 
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
            if (cost[i][j] != cost[i-1][j]) {  //if the optimal values are not the same, print
                FoodItem f = items.get(i-1);
                String text = String.format("%s: %6.2f g | %6.2f calories\n", f.getFoodName(), (float)f.getGrams(), (float)f.getCalories());
                totalCal += f.getCalories();
                totalGram += f.getGrams();
                log.append(text);
                j = j-w[i-1]; // reduce the weight limit 
            }
            i = i-1; //move one row upward
        }
        
        String total = String.format("\nTotal Grams: %5d g\n", totalGram);
        log.append(total);
        total = String.format("Total Calories: %5d calories\n", totalCal);
        log.append(total);
    }
     /**
       * This function gets the text area used in the application from the Gui object. 
       * @return the JTextArea element. 
       */
    public JTextArea getTextArea() {
        return gui.getTextArea();
    }
    
    /**
     * This is the main driver of the application. 
     */
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
