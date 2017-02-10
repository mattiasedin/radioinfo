package Views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 1/11/17.
 * <p>
 * Renders a preferences view with user input for setting a slider value for update interval
 */
public class PreferencesView extends JPanel implements ChangeListener {
    private final JSlider slider;
    private boolean isCodeTriggered = false;
    private final ActionListener listener;

    /**
     * Constructor for the view.
     * @param listener the listener called when the update interval is changed, the new value is set as source of the
     *                 action event.
     * @param updateInterval the update interval to set as default value.
     */
    public PreferencesView(ActionListener listener, int updateInterval) {
        super(new BorderLayout());
        this.listener = listener;

        JPanel textContainer = new JPanel(new GridLayout(0,1));

        add(ViewHelper.withPadding(textContainer, 10, 10), BorderLayout.NORTH);

        textContainer.add(ViewHelper.toLabel("Preferences", getFont(), 1.5));

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

        add(ViewHelper.withPadding((slider),10,10), BorderLayout.CENTER);
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


}
