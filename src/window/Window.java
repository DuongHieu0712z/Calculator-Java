
package window;

import java.awt.*;
import javax.swing.*;

public class Window extends JFrame {

    // private final static int WIDTH = 400;
    // private final static int HEIGHT = 400;

    private JMenuBar menuBar = new JMenuBar();

    private JMenu editMenu = new JMenu("Edit");
    private JMenu viewMenu = new JMenu("View");
    private JMenu helpMenu = new JMenu("Help");

    private final static Font font = new Font("Arial", Font.PLAIN, 12);

    public Window() {
        super("Calculator");
        // setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("src/image/calculator.png");
        setIconImage(icon.getImage());

        createMenuBar();

        setContentPane(new Standard());

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

    public void run() {
        setVisible(true);
    }
}
