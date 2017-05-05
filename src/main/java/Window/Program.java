package Window;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Created by Michael on 5/4/2017.
 */
public class Program {

    private JFrame mainWindow;
    private JMenuBar menuBar;
    private JPanel console;
    private int height = 600;
    private int width = 1200;

    public Program() {
        mainWindow = new JFrame("New window");
        mainWindow.setSize(width, height);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setResizable(true);
        mainWindow.setVisible(true);
        //System.out.println(mainWindow.getHeight() + "" + mainWindow.getWidth());

        menuBar = new JMenuBar();
        JMenu menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
        mainWindow.setJMenuBar(menuBar);

        console = new JPanel();
        console.setBackground(Color.darkGray);
        console.setSize(250, mainWindow.getHeight());
        console.setBorder(BorderFactory.createLineBorder(Color.black));
        console.setVisible(true);

        mainWindow.getContentPane().add(console, BorderLayout.WEST);

        Application app = new Application();
        app.init();
        mainWindow.getContentPane().add(app, BorderLayout.CENTER);
        mainWindow.pack();


    }

    public static void main(String[] args) {
        Program p = new Program();
    }

}
