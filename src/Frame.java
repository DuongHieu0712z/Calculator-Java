import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame extends JFrame implements ActionListener {
    private final static int WIDTH = 400;
    private final static int HEIGHT = 400;

    private final static int numOfMenu = 3;
    private final static int numOfButton = 20;
    
    private JPanel workPanel = new JPanel(new BorderLayout());
    private JPanel buttonPanel = new JPanel(new GridLayout(4, 5));

    private JMenuBar menuBar = new JMenuBar();
    private JMenu[] menus = new JMenu[numOfMenu];

    private JTextField textField = new JTextField();

    private JButton[] buttons = new JButton[numOfButton];

    public Frame() {
        super("Calculator");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());

        String[] nameMenus = {"Edit", "View", "Help"};
        for (int index = 0; index < numOfMenu; ++index) {
            menus[index] = new JMenu(nameMenus[index]);
            menuBar.add(menus[index]);
        }

        workPanel.add(textField, BorderLayout.NORTH);
        workPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(menuBar, BorderLayout.NORTH);
        add(workPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
