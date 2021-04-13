
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Hashtable;

/**
 *
 * @author DuongHieu
 */
public class Window extends JFrame implements ActionListener {

    // private final static int WIDTH = 400;
    // private final static int HEIGHT = 400;

    private JMenuBar menuBar = new JMenuBar();

    private JMenu editMenu = new JMenu("Edit");
    private JMenu viewMenu = new JMenu("View");
    private JMenu helpMenu = new JMenu("Help");

    private JPanel textFieldPanel = new JPanel();
    private JTextField textField = new JTextField(21);

    private JPanel buttonsPanel = new JPanel(new BorderLayout());
    private GroupLayout groupLayout = new GroupLayout(buttonsPanel);
    
    private JPanel memoryButtonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
    private JPanel clearButtonsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
    private JPanel numberButtonsPanel = new JPanel(new GridLayout(4, 5, 5, 5));

    private Hashtable<String, JButton> memoryButtons = new Hashtable<>();
    private Hashtable<String, JButton> clearButtons = new Hashtable<>();
    private Hashtable<String, JButton> numberButtons = new Hashtable<>();

    private final static String[] memoryBtnNames = {"MC", "MR", "MS", "M+"};
    private final static String[] clearBtnNames = {"Backspace", "CE", "C"};
    private final static String[] numberBtnNames = {"7", "8", "9", Symbol.divisor, Symbol.squareRootX,
                                                    "4", "5", "6", Symbol.multiply, "%",
                                                    "1", "2", "3", Symbol.minus, "1/x",
                                                    "0", Symbol.plusMinus, ".", Symbol.plus, "="};

    private final static Font font = new Font("Arial", Font.PLAIN, 12);

    private Calculator cal = new Calculator();
    
    public Window() {
        super("Calculator");
        // setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("src/cal.png");
        setIconImage(icon.getImage());

        createMenuBar();
        createTextField();
        createButtons();

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(textFieldPanel, BorderLayout.NORTH);
        getContentPane().add(buttonsPanel, BorderLayout.CENTER);

        pack();
    }

    private void createMenuBar() {
        editMenu.setFont(font);
        viewMenu.setFont(font);
        helpMenu.setFont(font);

        editMenu.setMnemonic('E');
        viewMenu.setMnemonic('V');
        helpMenu.setMnemonic('H');

        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void createTextField() {
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textFieldPanel.add(textField);
    }

    private void createMemoryButtons() {
        for (int index = 0; index < memoryBtnNames.length; ++index) {
            String name = memoryBtnNames[index];
            JButton button = new JButton(name);

            button.setFont(font);
            button.addActionListener(this);

            memoryButtons.put(name, button);
            memoryButtonsPanel.add(button);
        }
    }

    private void createClearButtons() {
        for (int index = 0; index < clearBtnNames.length; ++index) {
            String name = clearBtnNames[index];
            JButton button = new JButton(name);

            button.setFont(font);
            button.addActionListener(this);

            clearButtons.put(name, button);
            clearButtonsPanel.add(button);
        }
    }

    private void createNumberButtons() {
        for (int index = 0; index < numberBtnNames.length; ++index) {
            String name = numberBtnNames[index];
            JButton button = new JButton(name);

            button.setFont(font);
            button.setPreferredSize(new Dimension(40, 40));
            button.addActionListener(this);

            numberButtons.put(name, button);
            numberButtonsPanel.add(button);
        }
    }

    private void groupButtons() {
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(
            groupLayout.createSequentialGroup()
            .addComponent(memoryButtonsPanel)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(clearButtonsPanel)
            .addComponent(numberButtonsPanel)
            )
        );

        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
            .addComponent(clearButtonsPanel)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(memoryButtonsPanel)
            .addComponent(numberButtonsPanel)
            )
        );

        buttonsPanel.setLayout(groupLayout);
    }

    private void createButtons() {
        createMemoryButtons();
        createClearButtons();
        createNumberButtons();
        groupButtons();
    }

    public void run() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actionMemoryButtons(e);
        actionClearButtons(e);
        actionNumberButtons(e);
    }

    private void actionMemoryButtons(ActionEvent e) {
        Object button = e.getSource();
        // pass
    }

    private void actionClearButtons(ActionEvent e) {
        Object button = e.getSource();
        String text = textField.getText();
        
        if (button == clearButtons.get("Backspace")) {

        } else if (button == clearButtons.get("CE")) {

        } else if (button == clearButtons.get("C")) {
            text = "";
        }

        textField.setText(text);
    }

    private void actionNumberButtons(ActionEvent e) {
        Object button = e.getSource();
        String text = textField.getText();

        if (button == numberButtons.get("0")) {
            text += "0";
        } else if (button == numberButtons.get("1")) {
            text += "1";
        } else if (button == numberButtons.get("2")) {
            text += "2";
        } else if (button == numberButtons.get("3")) {
            text += "3";
        } else if (button == numberButtons.get("4")) {
            text += "4";
        } else if (button == numberButtons.get("5")) {
            text += "5";
        } else if (button == numberButtons.get("6")) {
            text += "6";
        } else if (button == numberButtons.get("7")) {
            text += "7";
        } else if (button == numberButtons.get("8")) {
            text += "8";
        } else if (button == numberButtons.get("9")) {
            text += "9";
        } else if (button == numberButtons.get(Symbol.plus)) {
            cal.setFirstNumber(text);
            cal.setOperator(Symbol.plus);
        } else if (button == numberButtons.get(Symbol.minus)) {
            cal.setFirstNumber(text);
            cal.setOperator(Symbol.minus);
        } else if (button == numberButtons.get(Symbol.multiply)) {
            cal.setFirstNumber(text);
            cal.setOperator(Symbol.multiply);
        } else if (button == numberButtons.get("/")) {
            cal.setFirstNumber(text);
            cal.setOperator("/");
        } else if (button == numberButtons.get(".")) {
            if (!text.contains(".")) {
                if (text.length() == 0) {
                    text += "0.";
                } else {
                    text += ".";
                }
            }
        } else if (button == numberButtons.get(Symbol.plusMinus)) {
            if (text.length() != 0) {
                char firstLetter = text.charAt(0);
                if (firstLetter == Symbol.minus.charAt(0)) {
                    text = text.substring(1);
                } else {
                    text = Symbol.minus + text;
                }
            }
        }

        textField.setText(text);
    }
}
