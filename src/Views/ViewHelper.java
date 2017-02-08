package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by mattias on 2/8/17.
 */
public class ViewHelper {

    public static Component withPadding(Component c, int top, int right, int bottom, int left) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(top, right, bottom, left));
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    public static Component withPadding(Component c, int topBottom, int leftRight) {
        return withPadding(c, topBottom, leftRight, topBottom, leftRight);
    }

    public static JLabel toLabel(String text, Font font, int fontTransform, double fontScale) {
        JLabel label = new JLabel("<html>"+text+"</html>");
        label.setFont(new Font(font.getName(), fontTransform, (int) (font.getSize() * fontScale)));
        return label;
    }

    public static JLabel toLabel(String text, Font font, double fontScale) {
        return toLabel(text, font, Font.PLAIN, fontScale);
    }

    public static JLabel toLabel(String text, Font font, int fontTransform) {
        return toLabel(text, font, fontTransform, 1);
    }

    public static JLabel toLabel(String text, Font font) {
        return toLabel(text, font, Font.PLAIN, 1);
    }
}
