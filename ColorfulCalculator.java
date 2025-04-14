import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorfulCalculator {

    public static void main(String[] args) {
        // Launch the calculator
        SwingUtilities.invokeLater(() -> CalculatorFrame.getInstance());
    }
}

class CalculatorFrame extends JFrame {

    // This variable holds the single instance of CalculatorFrame so that only one
    // calculator window exists.
    private static CalculatorFrame instance;

    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String lastOperator;

    // The constructor is private so many calculator windows dont open by accident.
    private CalculatorFrame() {
        setTitle("Colorful Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        setVisible(true);
    }

    // This method checks if the calculator instance exists and creates it if it
    // doesn't, ensuring a single instance is used throughout.
    public static CalculatorFrame getInstance() {
        if (instance == null) {
            instance = new CalculatorFrame();
        }
        return instance;
    }

    private void initUI() {
        // Sets up the calculator display area where inputs and results are shown.
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        currentInput = new StringBuilder();
        result = 0;
        lastOperator = "";

        // This will create the button panel.
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));

        buttonPanel.setBackground(Color.PINK);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "CLEAR", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(randomColor()); // This will make the buttons have random colors so it becomes more
                                                 // creative.

            button.setForeground(Color.WHITE);
            button.addActionListener(new ButtonClickListener()); // This makes the buttons clickable and do their jobs.
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private Color randomColor() {
        return new Color((int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256));
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();

            switch (command) {
                case "CLEAR":
                    currentInput.setLength(0);
                    result = 0;
                    lastOperator = "";
                    display.setText("");
                    break;
                case "=":
                    calculate();
                    display.setText(String.valueOf(result));
                    currentInput.setLength(0);
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    if (currentInput.length() > 0) {
                        calculate();
                        lastOperator = command;
                        currentInput.setLength(0);
                    }
                    break;
                default:
                    currentInput.append(command);
                    display.setText(currentInput.toString());
                    break;
            }
        }

        // This method performs the operations based on the inputs by the user.
        private void calculate() {
            double input = currentInput.length() > 0 ? Double.parseDouble(currentInput.toString()) : 0;

            switch (lastOperator) {
                case "+":
                    result += input;
                    break;
                case "-":
                    result -= input;
                    break;
                case "*":
                    result *= input;
                    break;
                case "/":
                    if (input != 0) {
                        result /= input;
                    } else {
                        JOptionPane.showMessageDialog(CalculatorFrame.this, "Cannot divide by zero!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        result = 0;
                    }
                    break;
                default:
                    result = input;
                    break;
            }
        }
    }
}
