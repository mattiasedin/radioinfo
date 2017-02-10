import Controller.GUI;

/**
 * Created by mattias on 2016-01-07.
 * <p>
 * Starts the application RadioInfo
 */
public class Main {

    public static void main(String[] args){

        javax.swing.SwingUtilities.invokeLater(() -> new GUI("RadioInfo"));
    }
}
