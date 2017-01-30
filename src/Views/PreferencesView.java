package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 1/11/17.
 */
public class PreferencesView extends JPanel implements ChangeListener {
    private JSlider slider;
    private boolean isCodeTriggered = false;
    private ActionListener listener;

    public PreferencesView(ActionListener listener, int updateInterval) {
        super(new BorderLayout());
        this.listener = listener;

        JPanel textContainer = new JPanel(new GridLayout(0,1));

        add(withPadding(textContainer, 10, 10), BorderLayout.NORTH);


        JLabel title = new JLabel("<html>Preferences</html>");
        title.setFont(new Font(getFont().getName(), Font.PLAIN, (int) (getFont().getSize() * 1.5)));
        textContainer.add(title);

        JLabel sliderInfo = new JLabel("<html>" +
                "Set the update interval, the value is defined as minutes between each update."
                +"</html>");
        textContainer.add(sliderInfo);

        //Create the slider
        slider = new JSlider(JSlider.HORIZONTAL, 0, 30, updateInterval);
        slider.addChangeListener(this);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setPreferredSize(new Dimension(700, 150));

        add(withPadding((slider),10,10), BorderLayout.CENTER);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        if (!slider.getValueIsAdjusting() && !isCodeTriggered) {
            isCodeTriggered = true;
            if (slider.getValue() < 1) {
                slider.setValue(1);
            }

            if (listener != null) {
                listener.actionPerformed(new ActionEvent(slider.getValue(), ActionEvent.ACTION_PERFORMED, "Ok"));
            }

            isCodeTriggered = false;
        }
    }

    public Component withPadding(Component c, int topBottom, int leftRight) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(topBottom, leftRight, topBottom, leftRight));
        p.add(c, BorderLayout.CENTER);
        return p;
    }
}
