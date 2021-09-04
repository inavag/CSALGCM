import javax.swing.*;
import java.awt.*;
import java.text.*;

public class Gui extends JFrame{
    private SpringLayout spring;
    private JTextArea log;
    private JScrollPane scroll;
    private JPanel root, textPanel;
    private JLabel[] nLabels;
    private JLabel weight;
    private JTextField[] nInputs;
    private JButton add, set, confirm, clear;
    public Gui () {
        initGui();
    }
    /**
     * This function initializes the GUI of the app.
     */
    private void initGui() {
        initTextArea();
        initFields();
        setLayout();

        setPreferredSize(new Dimension(500, 670));
        setTitle("Meal Planner");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false); // set to false so that the user cannot change the size
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * This function initializes the textarea where the information
     * will be printed.
     */
    private void initTextArea() {
        textPanel = new JPanel(new BorderLayout());
        log = new JTextArea(20, 45);
        log.setBorder(BorderFactory.createLineBorder(Color.black));
        log.setLineWrap(true);
        log.setEditable(false);
        log.setVisible(true);
        scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textPanel.add(scroll, BorderLayout.CENTER);
    }

    /**
     * This function initializes the input fields to be used to
     * gather the user input.
     */
    private void initFields() {
        nLabels = new JLabel[4];
        nInputs = new JTextField[4];
        
        nLabels[0] = new JLabel("Target Calories: ");
        nInputs[0] = new JTextField(5);

        nLabels[1] = new JLabel("Food: ");
        nInputs[1] = new JTextField(7);

        nLabels[2] = new JLabel("Grams (g): ");
        nInputs[2] = new JTextField(4);

        nLabels[3] = new JLabel("Calories: ");
        nInputs[3] = new JTextField(5);

        set = new JButton("SET");
        set.setPreferredSize(new Dimension(60, 20));

        add = new JButton("ADD");
        add.setPreferredSize(new Dimension(60, 20));

        confirm = new JButton("CONFIRM");
        confirm.setPreferredSize(new Dimension(100, 20));

        clear = new JButton("CLEAR");
        clear.setPreferredSize(new Dimension(100, 20));
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
        spring.putConstraint(SpringLayout.WEST, nLabels[0], 10, SpringLayout.WEST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[0], 20, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[0], 110, SpringLayout.WEST, root);

        //Position the Food input
        spring.putConstraint(SpringLayout.NORTH, nLabels[1], 60, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nLabels[1], 10, SpringLayout.WEST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[1], 60, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[1], 50, SpringLayout.WEST, root);
        //Position the Grams input
        spring.putConstraint(SpringLayout.NORTH, nLabels[2], 60, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nLabels[2], 140, SpringLayout.WEST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[2], 60, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[2], 205, SpringLayout.WEST, root);
        //Position the calories input
        spring.putConstraint(SpringLayout.NORTH, nLabels[3], 60, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nLabels[3], 260, SpringLayout.WEST, root);
        spring.putConstraint(SpringLayout.NORTH, nInputs[3], 60, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, nInputs[3], 315, SpringLayout.WEST, root);
        //Position the SET button
        spring.putConstraint(SpringLayout.NORTH, set, 20, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, set, 200, SpringLayout.WEST, root);
        //Position the ADD button
        spring.putConstraint(SpringLayout.NORTH, add, 60, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, add, 400, SpringLayout.WEST, root);
        //Position the CONFIRM button
        spring.putConstraint(SpringLayout.NORTH, confirm, 120, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, confirm, 200, SpringLayout.WEST, root);
        //Position the CLEAR button
        spring.putConstraint(SpringLayout.NORTH, clear, 600, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, clear, 200, SpringLayout.WEST, root);

        spring.putConstraint(SpringLayout.NORTH, textPanel, 270, SpringLayout.NORTH, root);
        spring.putConstraint(SpringLayout.WEST, textPanel, 15, SpringLayout.WEST, root);

        for (int i = 0; i < 4; i++) {
            root.add(nLabels[i]);
            root.add(nInputs[i]);
        }
        root.add(set);
        root.add(add);
        root.add(confirm);
        root.add(clear);
        root.add(textPanel);
        add(root);
    }

    public JTextField getInput(int index) {
        return nInputs[index];
    }
    public JTextArea getTextArea() {
        return log;
    }
    public JButton getSet() {
        return set;
    }
    public JButton getAdd() {
        return add;
    }
    public JButton getConfirm() {
        return confirm;
    }

    public JButton getClear() {
        return clear; 
    }
    // public static void main(String[] args) {
    //     Gui test = new Gui();
    // }
}
