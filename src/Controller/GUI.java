package Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-07.
 *
 * Graphical user interface for the application. Creates the controller to keep track of the content of the GUI and
 * user input.
 */
public class GUI extends JFrame {

    public GUI(String title) {
        super(title);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        /*
         * Set the system style to the user interface.
         * This is mostly needed for linux systems.
         */

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            /*
             * If this fails the Java Runtime will automatically use the default style.
             *
             * Move along, nothing to see here...
             */
        }


        ViewController controller = new ViewController(this);

        //Add contents to the window.


        //Set min window size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setMinimumSize(new Dimension((int)Math.round(width/2.0),(int)Math.round(height/2.0)));

        //Display the window.
        this.pack();
        this.setVisible(true);
    }
}
