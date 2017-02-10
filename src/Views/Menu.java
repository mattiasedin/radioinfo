package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by mattias on 2016-01-07.
 * <p>
 * Renders menu with options.
 */
public class Menu extends JMenuBar {

    /**
     * The click options.
     */
    public interface OPTIONS {
        int update = 1;
        int preferences = 2;
        int about = 3;
    }

    /**
     * Constructor for the menu.
     * @param onClickListener listener for when a menu item is clicked. Options will be called as source of the listener
     */
    public Menu(ActionListener onClickListener) {
        super();

        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        this.add(menu);

        JMenuItem menuItem = new JMenuItem("Update now");
        menuItem.addActionListener(actionEvent -> onClickListener.actionPerformed(new ActionEvent(OPTIONS.update, ActionEvent.ACTION_PERFORMED, "OK")));
        menuItem.setMnemonic(KeyEvent.VK_U);
        menu.add(menuItem);

        menuItem = new JMenuItem("Preferences");
        menuItem.addActionListener(actionEvent -> onClickListener.actionPerformed(new ActionEvent(OPTIONS.preferences, ActionEvent.ACTION_PERFORMED, "OK")));
        menuItem.setMnemonic(KeyEvent.VK_P);
        menu.add(menuItem);


        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(actionEvent -> onClickListener.actionPerformed(new ActionEvent(OPTIONS.about, ActionEvent.ACTION_PERFORMED, "OK")));
        menuItem.setMnemonic(KeyEvent.VK_A);
        menu.add(menuItem);
        this.add(menu);
    }
}
