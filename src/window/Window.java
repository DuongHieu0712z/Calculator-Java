
package window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.security.acl.Group;
import java.util.Hashtable;

import javax.swing.*;

import symbol.Symbol;

public class Window extends JFrame implements ActionListener, ItemListener {

    // private final static int WIDTH = 400;
    // private final static int HEIGHT = 400;

    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu editMenu = new JMenu("Edit");
    private final JMenu viewMenu = new JMenu("View");
    private final JMenu helpMenu = new JMenu("Help");
    
    //#region Edit Menu
    private final JMenuItem copyItem = new JMenuItem("Copy");
    private final JMenuItem pasteItem = new JMenuItem("Paste");
    //#endregion Edit Menu

    //#region View Menu
    private final ButtonGroup modeGroup = new ButtonGroup();
    private final JRadioButtonMenuItem standardItem = new JRadioButtonMenuItem("Standard", true);
    private final JRadioButtonMenuItem scientificItem = new JRadioButtonMenuItem("Scientific");

    private final ButtonGroup baseGroup = new ButtonGroup();
    private final JRadioButtonMenuItem hexItem = new JRadioButtonMenuItem("Hex");
    private final JRadioButtonMenuItem decItem = new JRadioButtonMenuItem("Decimal", true);
    private final JRadioButtonMenuItem octItem = new JRadioButtonMenuItem("Octal");
    private final JRadioButtonMenuItem binItem = new JRadioButtonMenuItem("Binary");

    private final ButtonGroup unitGroup = new ButtonGroup();
    private final JRadioButtonMenuItem degItem = new JRadioButtonMenuItem("Degrees", true);
    private final JRadioButtonMenuItem radItem = new JRadioButtonMenuItem("Radians");
    private final JRadioButtonMenuItem graItem = new JRadioButtonMenuItem("Grads");

    private final JCheckBoxMenuItem digitGroupItem = new JCheckBoxMenuItem("Digit grouping");
    //#endregion View Menu

    //#region Help Menu
    private final JMenuItem helpItem = new JMenuItem("Help");
    private final JMenuItem aboutItem = new JMenuItem("About");
    //#endregion Help Menu

    private final static Font font = new Font("Arial", Font.PLAIN, 12);

    private final Standard standardMode = new Standard();
    private final Scientific scientificMode = new Scientific();

    private boolean isDigitGroup = false;

    public Window() {
        super("Calculator");
        // setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("src/image/calculator.png");
        setIconImage(icon.getImage());

        createMenuBar();

        // getContentPane().setLayout(new CardLayout());
        // getContentPane().add(standardMode, "Standard");
        // getContentPane().add(scientificMode, "Scientific");

        setContentPane(standardMode);

        pack();
    }

    private void createEditMenu() {
        copyItem.setFont(font);
        pasteItem.setFont(font);

        copyItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));

        editMenu.add(copyItem);
        editMenu.add(pasteItem);
    }

    private void createModeGroup() {
        standardItem.setFont(font);
        scientificItem.setFont(font);

        standardItem.addItemListener(this);
        scientificItem.addItemListener(this);

        modeGroup.add(standardItem);
        modeGroup.add(scientificItem);

        viewMenu.add(standardItem);
        viewMenu.add(scientificItem);
        viewMenu.addSeparator();
    }

    private void createBaseGroup() {
        hexItem.setFont(font);
        decItem.setFont(font);
        octItem.setFont(font);
        binItem.setFont(font);

        hexItem.addItemListener(this);
        decItem.addItemListener(this);
        octItem.addItemListener(this);
        binItem.addItemListener(this);

        baseGroup.add(hexItem);
        baseGroup.add(decItem);
        baseGroup.add(octItem);
        baseGroup.add(binItem);

        viewMenu.add(hexItem);
        viewMenu.add(decItem);
        viewMenu.add(octItem);
        viewMenu.add(binItem);
        viewMenu.addSeparator();
    }

    private void createUnitGroup() {
        degItem.setFont(font);
        radItem.setFont(font);
        graItem.setFont(font);

        degItem.addItemListener(this);
        radItem.addItemListener(this);
        graItem.addItemListener(this);

        unitGroup.add(degItem);
        unitGroup.add(radItem);
        unitGroup.add(graItem);

        viewMenu.add(degItem);
        viewMenu.add(radItem);
        viewMenu.add(graItem);
        viewMenu.addSeparator();
    }

    private void createViewMenuModeStandard() {
        viewMenu.removeAll();

        createModeGroup();

        digitGroupItem.setFont(font);
        viewMenu.add(digitGroupItem);
    }

    private void createViewMenuModeScientific() {
        viewMenu.removeAll();

        createModeGroup();
        createBaseGroup();
        createUnitGroup();

        digitGroupItem.setFont(font);
        viewMenu.add(digitGroupItem);
    }
    
    private void createHelpMenu() {
        helpItem.setFont(font);
        aboutItem.setFont(font);

        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
    }

    private void createMenuBar() {
        createEditMenu();
        createViewMenuModeStandard();
        createHelpMenu();

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

    public void run() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String item = ((JRadioButtonMenuItem)e.getItem()).getText();

        // CardLayout cardLayout = (CardLayout)getContentPane().getLayout();
        // cardLayout.show(getContentPane(), item);

        switch (item) {
            case "Standard":
                setContentPane(standardMode);
                createViewMenuModeStandard();
                break;

            case "Scientific":
                setContentPane(scientificMode);
                createViewMenuModeScientific();
                pack();
                break;
        }
    }
    
    private class Standard extends JPanel implements ActionListener {

        private final JPanel textFieldPanel = new JPanel();
        private final JTextField textField = new JTextField(21);
        
        //#region Buttons
        private final JPanel buttonsPanel = new JPanel();
        private final GroupLayout groupLayout = new GroupLayout(buttonsPanel);
        
        private final JPanel memoryButtonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        private final JPanel clearButtonsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        private final JPanel numberButtonsPanel = new JPanel(new GridLayout(4, 5, 5, 5));
    
        private final Hashtable<String, JButton> memoryButtons = new Hashtable<>();
        private final Hashtable<String, JButton> clearButtons = new Hashtable<>();
        private final Hashtable<String, JButton> numberButtons = new Hashtable<>();
    
        private final String[] memoryBtnNames = {"MC", "MR", "MS", "M+"};
        private final String[] clearBtnNames = {"Backspace", "CE", "C"};
        private final String[] numberBtnNames = {"7", "8", "9", Symbol.divisor, Symbol.squareRootX,
                                                 "4", "5", "6", Symbol.multiply, "%",
                                                 "1", "2", "3", Symbol.minus, "1/x",
                                                 "0", Symbol.plusMinus, ".", Symbol.plus, "="};
        //#endregion Buttons
    
        private final Font font = new Font("Arial", Font.PLAIN, 12);
        
        public Standard() {
            createTextField();
            createButtons();
            
            setLayout(new BorderLayout());
            add(textFieldPanel, BorderLayout.NORTH);
            add(buttonsPanel, BorderLayout.CENTER);
        }
    
        private void createTextField() {
            textField.setFont(new Font("Arial", Font.PLAIN, 20));
            textField.setHorizontalAlignment(JTextField.RIGHT);
            textFieldPanel.add(textField);
        }
        
        //#region Create Buttons
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
        //#endregion Create Buttons

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

    private class Scientific extends JPanel implements ActionListener, ItemListener {

        private final JPanel textFieldPanel = new JPanel();
        private final JTextField textField = new JTextField(40);

        //#region Buttons
        private final JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        private final JPanel rightPanel = new JPanel();
        private final JPanel leftPanel = new JPanel();

        private final GroupLayout rightLayout = new GroupLayout(rightPanel);
        private final GroupLayout leftLayout = new GroupLayout(leftPanel);

        //#region Right Panel
        private final JPanel baseButtonsPanel = new JPanel(new GridLayout(1, 4));
        private final JPanel specButtonsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        private final JPanel funcButtonsPanel = new JPanel(new GridLayout(5, 3, 5, 5));
        private final JPanel memoryButtonsPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        private final ButtonGroup baseButtonsGroup = new ButtonGroup();
        private final JRadioButton hex = new JRadioButton("Hex");
        private final JRadioButton dec = new JRadioButton("Dec", true);
        private final JRadioButton oct = new JRadioButton("Oct");
        private final JRadioButton bin = new JRadioButton("Bin");
        //#endregion

        //#region Left Panel
        private final JPanel unitButtonsPanel = new JPanel(new GridLayout(1, 3));
        private final JPanel clearButtonsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        private final JPanel numberButtonsPanel = new JPanel(new GridLayout(5, 6, 5, 5));

        private final ButtonGroup unitGroup = new ButtonGroup();
        private final JRadioButton deg = new JRadioButton("Degrees", true);
        private final JRadioButton rad = new JRadioButton("Radians");
        private final JRadioButton gra = new JRadioButton("Grads");
    
        private final Hashtable<String, JButton> clearButtons = new Hashtable<>();
        private final Hashtable<String, JButton> numberButtons = new Hashtable<>();
    
        private final String[] clearBtnNames = {"Backspace", "CE", "C"};
        private final String[] numberBtnNames = {"7", "8", "9", Symbol.divisor, "Mod", "And",
                                                 "4", "5", "6", Symbol.multiply, "Or", "Xor",
                                                 "1", "2", "3", Symbol.minus, "Lsh", "Not",
                                                 "0", Symbol.plusMinus, ".", Symbol.plus, "=", "Int",
                                                 "A", "B", "C", "D", "E", "F"};
        //#endregion Left Panel
        //#endregion Buttons

        private final Font font = new Font("Arial", Font.PLAIN, 12);

        public Scientific() {
            createTextField();
            createButtonsPanel();

            setLayout(new BorderLayout());
            add(textFieldPanel, BorderLayout.NORTH);
            add(buttonsPanel, BorderLayout.CENTER);
        }
        
        private void createTextField() {
            textField.setFont(new Font("Arial", Font.PLAIN, 20));
            textField.setHorizontalAlignment(JTextField.RIGHT);
            textFieldPanel.add(textField);
        }

        private void createButtonsPanel() {
            createRightPanel();
            createLeftPanel();

            buttonsPanel.add(rightPanel);
            buttonsPanel.add(leftPanel);
        }

        //#region Create Right Panel
        private void createBaseGroup() {
            hex.setFont(font);
            dec.setFont(font);
            oct.setFont(font);
            bin.setFont(font);

            hex.addItemListener(this);
            dec.addItemListener(this);
            oct.addItemListener(this);
            bin.addItemListener(this);

            baseButtonsGroup.add(hex);
            baseButtonsGroup.add(dec);
            baseButtonsGroup.add(oct);
            baseButtonsGroup.add(bin);

            baseButtonsPanel.add(hex);
            baseButtonsPanel.add(dec);
            baseButtonsPanel.add(oct);
            baseButtonsPanel.add(bin);
        }

        private void createRightPanel() {
            createBaseGroup();
        }
        //#endregion Create Right Panel

        //#region Create Left Panel
        private void createUnitGroup() {
            deg.setFont(font);
            rad.setFont(font);
            gra.setFont(font);

            deg.addItemListener(this);
            rad.addItemListener(this);
            gra.addItemListener(this);

            unitGroup.add(deg);
            unitGroup.add(rad);
            unitGroup.add(gra);

            unitButtonsPanel.add(deg);
            unitButtonsPanel.add(rad);
            unitButtonsPanel.add(gra);
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

        private void groupLeftPanel() {
            leftLayout.setAutoCreateGaps(true);
            leftLayout.setAutoCreateContainerGaps(true);
    
            leftLayout.setHorizontalGroup(
                leftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(unitButtonsPanel)
                .addComponent(clearButtonsPanel)
                .addComponent(numberButtonsPanel)
            );
    
            leftLayout.setVerticalGroup(
                leftLayout.createSequentialGroup()
                .addComponent(unitButtonsPanel)
                .addComponent(clearButtonsPanel)
                .addComponent(numberButtonsPanel)
            );
    
            leftPanel.setLayout(leftLayout);
        }

        private void createLeftPanel() {
            createUnitGroup();
            createClearButtons();
            createNumberButtons();
            groupLeftPanel();
        }
        //#endregion Create Left Panel
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
        }
    }
}
