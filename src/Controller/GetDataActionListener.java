package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 2016-01-08.
 */
public class GetDataActionListener implements ActionListener {

    public ActionListener nextActionListener;

    public GetDataActionListener(ActionListener nextActionListener) {
        this.nextActionListener = nextActionListener;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    }
}
