package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by mattias on 2/8/17.
 * <p>
 * User Interface builder that helps the views to build their components. This class contains static methods for common
 * used function throughout the views.
 */
public class ViewHelper {

    /**
     * Wraps the component in a padding component.
     * @param c component to wrap
     * @param top padding
     * @param right padding
     * @param bottom padding
     * @param left padding
     * @return the padding wrapped component
     */
    public static Component withPadding(Component c, int top, int right, int bottom, int left) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(top, right, bottom, left));
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    /**
     * Wraps the component with padding
     * @param c component to wrap
     * @param topBottom top and bottom padding
     * @param leftRight left and right padding
     * @return the padding wrapped component
     */
    public static Component withPadding(Component c, int topBottom, int leftRight) {
        return withPadding(c, topBottom, leftRight, topBottom, leftRight);
    }

    /**
     * Wraps arbitrary string within a JLabel with specified settings.
     * @param text text to wrap
     * @param font style of text
     * @param fontTransform the transformation of the text. See Font interface.
     * @param fontScale scaling of the font, default 1 with no scaling.
     * @return wrapped string.
     */
    public static JLabel toLabel(String text, Font font, int fontTransform, double fontScale) {
        JLabel label = new JLabel("<html>"+text+"</html>");
        label.setFont(new Font(font.getName(), fontTransform, (int) (font.getSize() * fontScale)));
        return label;
    }

    /**
     * Wraps arbitrary string within a JLabel with specified settings.
     * @param text text to wrap
     * @param font style of text
     * @param fontScale scaling of the font, default 1 with no scaling.
     * @return wrapped string.
     */
    public static JLabel toLabel(String text, Font font, double fontScale) {
        return toLabel(text, font, Font.PLAIN, fontScale);
    }

    /**
     * Wraps arbitrary string within a JLabel with specified settings.
     * @param text text to wrap
     * @param font style of text
     * @param fontTransform the transformation of the text. See Font interface.
     * @return wrapped string.
     */
    public static JLabel toLabel(String text, Font font, int fontTransform) {
        return toLabel(text, font, fontTransform, 1);
    }

    /**
     * Wraps arbitrary string within a JLabel with specified settings.
     * @param text text to wrap
     * @param font style of text
     * @return wrapped string.
     */
    public static JLabel toLabel(String text, Font font) {
        return toLabel(text, font, Font.PLAIN, 1);
    }
}
