package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2/8/17.
 */
public class AboutView extends JPanel {

    public AboutView() {
        super(new BorderLayout());


        JPanel stackPanel = new JPanel();
        stackPanel.setLayout(new BoxLayout(stackPanel, BoxLayout.Y_AXIS));
        this.add(stackPanel, BorderLayout.NORTH);


        stackPanel.add(ViewHelper.withPadding(
                ViewHelper.toLabel(
                        "About",
                        getFont(),
                        Font.BOLD,
                        1.5),
                20,10));

        stackPanel.add(ViewHelper.withPadding(
                ViewHelper.toLabel(
                        "RadioInfo, Sveriges Radio API Reader",
                        getFont(),
                        Font.BOLD),
                10,10));


        stackPanel.add(ViewHelper.withPadding(
                ViewHelper.toLabel(
                        "Built with java",
                        getFont()),
                0,10));
        stackPanel.add(ViewHelper.withPadding(
                ViewHelper.toLabel(
                        "Author: Mattias Edin",
                        getFont()),
                0,10));
        stackPanel.add(ViewHelper.withPadding(
                ViewHelper.toLabel(
                        "Date: 2017/02/08",
                        getFont()),
                0,10,10,10));
    }


}
