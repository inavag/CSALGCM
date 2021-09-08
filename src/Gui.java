import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
  * This class is responsible for GUI of this application. 
  */

public class Gui extends JFrame{
    private SpringLayout spring;
    private JTextArea log;
    private JScrollPane scroll;
    private JPanel root, textPanel;
    private JLabel[] nLabels;
    private JTextField[] nInputs;
    private JButton add, set, confirm, clear;
    private JRadioButton greedy, dynamic;
    public Gui () {
        initGui();
    }
    /**
     * This function initializes the GUI of the app.
     */
    private void initGui() {
        initTextArea();
        initFields();
        initChoice();
        setLayout();

        setPreferredSize(new Dimension(740, 475));
        setTitle("Meal Planner");
        this.pack();
        this.setLocationRelativeTo(null); //makes the window open on the center of the screen
        this.setResizable(false); // set to false so that the user cannot change the size
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //exit operation
        this.setVisible(true);
    }

    /**
     * This function initializes the textarea where the information
     * will be printed.
     */
    private void initTextArea() {
        textPanel = new JPanel(new BorderLayout());
        log = new JTextArea(20, 45);
        //log.setBorder(BorderFactory.createLineBorder(Color.black));
        log.setLineWrap(true);
        log.setEditable(false);
        log.setVisible(true);
        scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        Border border = new LineBorder(Color.BLACK, 3);

        textPanel.add(scroll, BorderLayout.CENTER);
        textPanel.setBorder(border);
        //textPanel.setPreferredSize(new Dimension(300,200));
    }

    /**
     * This function initializes the input fields to be used to
     * gather the user input.
     */
    private void initFields() {
        nLabels = new JLabel[4];
        nInputs = new JTextField[4];
        
        nLabels[0] = new JLabel("Target Calories: ");
        nInputs[0] = new JTextField();

        nLabels[1] = new JLabel("Food: ");
        nInputs[1] = new JTextField();

        nLabels[2] = new JLabel("Grams (g): ");
        nInputs[2] = new JTextField();

        nLabels[3] = new JLabel("Calories: ");
        nInputs[3] = new JTextField();

        //creation of buttons in the application
        
        Font font1 = new Font("Courier",Font.BOLD, 15);
        Border border = new LineBorder(Color.BLACK, 3); 

        nLabels[0].setFont(font1);
        nInputs[0].setBorder(border);
        nInputs[0].setPreferredSize(new Dimension(145,35));

        nLabels[1].setFont(font1);
        nInputs[1].setBorder(border);
        nInputs[1].setPreferredSize(new Dimension(65,35));

        nLabels[2].setFont(font1);
        nInputs[2].setBorder(border);
        nInputs[2].setPreferredSize(new Dimension(65,35));

        nLabels[3].setFont(font1);
        nInputs[3].setBorder(border);
        nInputs[3].setPreferredSize(new Dimension(65,35));

        set = new JButton("SET");
        set.setFont(font1);
        set.setBorder(border);
        set.setBackground(Color.WHITE);
        set.setPreferredSize(new Dimension(145, 35));

        add = new JButton("ADD");
        add.setFont(font1);
        add.setBorder(border);
        add.setBackground(Color.WHITE);
        add.setPreferredSize(new Dimension(145, 35));

        confirm = new JButton("CONFIRM");
        confirm.setFont(font1);
        confirm.setBorder(border);
        confirm.setBackground(Color.WHITE);
        confirm.setPreferredSize(new Dimension(145, 35));

        clear = new JButton("CLEAR");
        clear.setFont(font1);
        clear.setBorder(border);
        clear.setBackground(Color.WHITE);
        clear.setPreferredSize(new Dimension(145, 35));
    }

    /**
     * This function creates a radio button for the two approaches to be used 
     * to solve the knapsack problem, specifically through using a greedy 
     * algorithm and dynamic programming. 
     */

    private void initChoice() {
        greedy = new JRadioButton("Greedy Algorithm");
        dynamic = new JRadioButton("Dynamic Programming");
        ButtonGroup radioGrp = new ButtonGroup();
        greedy.setSelected(true);
        radioGrp.add(greedy);
        radioGrp.add(dynamic);

        Font font1 = new Font("Courier",Font.BOLD, 15);

        greedy.setBackground(Color.WHITE);
        greedy.setFont(font1);

        dynamic.setBackground(Color.WHITE);
        dynamic.setFont(font1);
    }
    /**
     * This function sets the position of the elements in 
     * the GUI frame
     */
    private void setLayout() {
        spring = new SpringLayout();
        root = new JPanel();
        root.setLayout(spring);

        //Position the Target Calorie input
        spring.putConstraint(SpringLayout.NORTH, nLabels[0], 20, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nLabels[0], -155, SpringLayout.EAST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[0], 50, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[0], -170, SpringLayout.EAST, root);

        //Position the Food input
        spring.putConstraint(SpringLayout.NORTH, nLabels[1], 184, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nLabels[1], -170, SpringLayout.EAST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[1], 179, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[1], -90, SpringLayout.EAST, root);
        //Position the Grams input
        spring.putConstraint(SpringLayout.NORTH, nLabels[2], 229, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nLabels[2], -170, SpringLayout.EAST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[2], 224, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[2], -90, SpringLayout.EAST, root);
        //Position the calories input
        spring.putConstraint(SpringLayout.NORTH, nLabels[3], 274, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nLabels[3], -170, SpringLayout.EAST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[3], 269, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[3], -90, SpringLayout.EAST, root);
        //Position the SET button
        spring.putConstraint(SpringLayout.NORTH, set, 95, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, set, -170, SpringLayout.EAST, root);
        //Position the ADD button
        spring.putConstraint(SpringLayout.NORTH, add, 314, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, add, -170, SpringLayout.EAST, root);
        //Position Radio Buttons
        spring.putConstraint(SpringLayout.NORTH, greedy, 375, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, greedy, 23, SpringLayout.WEST, root);
        spring.putConstraint(SpringLayout.NORTH, dynamic, 375, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, dynamic, 180, SpringLayout.WEST, root);
        //Position the CONFIRM button
        spring.putConstraint(SpringLayout.NORTH, confirm, 375, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, confirm, -170, SpringLayout.EAST, root);
        //Position the CLEAR button
        spring.putConstraint(SpringLayout.NORTH, clear, 375, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, clear, 380, SpringLayout.WEST, root);
        //Position the text panel
        spring.putConstraint(SpringLayout.NORTH, textPanel, 20, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, textPanel, 23, SpringLayout.WEST, root);

        //This loop adds all the labels and inputs on the main panel. 

        for (int i = 0; i < 4; i++) {
            root.add(nLabels[i]);
            root.add(nInputs[i]);
        }

        root.setBackground(Color.WHITE);

        // This adds the remaining elements on the main panel. 
        root.add(set);
        root.add(add);
        root.add(greedy);
        root.add(dynamic);
        root.add(confirm);
        root.add(clear);
        root.add(textPanel);
        add(root); //don't forget to add the panel itself 
    }

    /**
     * This function gets the input from the array of JTextField nInputs.
     * @param index is the index where the desired input is stored. 
     * @return the desired data
     */
    public JTextField getInput(int index) {
        return nInputs[index];
    }

    /**
     * This function gets the text area used in the application. 
     * @return the JTextArea element. 
     */
    public JTextArea getTextArea() {
        return log;
    }
    /**
     * This function gets the button 'set'
     * @return the JButton element. 
     */
    public JButton getSet() {
        return set;
    }
    /**
     * This function gets the button 'add'
     * @return the JButton element. 
     */
    public JButton getAdd() {
        return add;
    }
    /**
     * This function gets the button 'confirm'
     * @return the JButton element. 
     */
    public JButton getConfirm() {
        return confirm;
    }
    /**
     * This function gets the button 'clear'
     * @return the JButton element. 
     */
    public JButton getClear() {
        return clear; 
    }
    /**
     * This function gets the radio button for the 'greedy' approach 
     * @return the JRadioButton element. 
     */
    public JRadioButton getGreedy() {
        return greedy;
    }
     /**
     * This function gets the radio button for the 'dynamic' approach 
     * @return the JRadioButton element. 
     */
    public JRadioButton getDynamic() {
        return dynamic;
    }
}
