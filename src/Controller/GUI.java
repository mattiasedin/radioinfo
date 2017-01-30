package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 2016-01-07.
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

    private void changeFonts(Font sf){
        UIManager.put("Button.font",sf);
        UIManager.put("ToggleButton.font",sf);
        UIManager.put("RadioButton.font",sf);
        UIManager.put("CheckBox.font",sf);
        UIManager.put("ColorChooser.font",sf);
        UIManager.put("ToggleButton.font",sf);
        UIManager.put("ComboBox.font",sf);
        UIManager.put("ComboBoxItem.font",sf);
        UIManager.put("InternalFrame.titleFont",sf);
        UIManager.put("Label.font",sf);
        UIManager.put("List.font",sf);
        UIManager.put("MenuBar.font",sf);
        UIManager.put("Views.Menu.font",sf);
        UIManager.put("MenuItem.font",sf);
        UIManager.put("RadioButtonMenuItem.font",sf);
        UIManager.put("CheckBoxMenuItem.font",sf);
        UIManager.put("PopupMenu.font",sf);
        UIManager.put("OptionPane.font",sf);
        UIManager.put("Panel.font",sf);
        UIManager.put("ProgressBar.font",sf);
        UIManager.put("ScrollPane.font",sf);
        UIManager.put("Viewport",sf);
        UIManager.put("TabbedPane.font",sf);
        UIManager.put("TableHeader.font",sf);
        UIManager.put("TextField.font",sf);
        UIManager.put("PasswordFiled.font",sf);
        UIManager.put("TextArea.font",sf);
        UIManager.put("TextPane.font",sf);
        UIManager.put("EditorPane.font",sf);
        UIManager.put("TitledBorder.font",sf);
        UIManager.put("ToolBar.font",sf);
        UIManager.put("ToolTip.font",sf);
        UIManager.put("Tree.font",sf);
    }
}
