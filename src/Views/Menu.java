package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by mattias on 2016-01-07.
 */
public class Menu extends JMenuBar {

    public interface OPTIONS {
        public static final int update = 1;
        public static final int preferences = 2;
        public static final int about = 3;
    }


    public Menu(ActionListener onClickListener) {
        super();

        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        this.add(menu);

        JMenuItem menuItem = new JMenuItem("Update now");
        menuItem.addActionListener(actionEvent -> {
            onClickListener.actionPerformed(new ActionEvent(OPTIONS.update, ActionEvent.ACTION_PERFORMED, "OK"));
        });
        menuItem.setMnemonic(KeyEvent.VK_U);
        menu.add(menuItem);

        menuItem = new JMenuItem("Preferences");
        menuItem.addActionListener(actionEvent -> {
            onClickListener.actionPerformed(new ActionEvent(OPTIONS.preferences, ActionEvent.ACTION_PERFORMED, "OK"));
        });
        menuItem.setMnemonic(KeyEvent.VK_P);
        menu.add(menuItem);


        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(actionEvent -> {
            onClickListener.actionPerformed(new ActionEvent(OPTIONS.about, ActionEvent.ACTION_PERFORMED, "OK"));
        });
        menuItem.setMnemonic(KeyEvent.VK_A);
        menu.add(menuItem);
        this.add(menu);

    }
}
