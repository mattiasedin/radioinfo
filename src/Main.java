import Controller.GUI;

/**
 * Created by mattias on 2016-01-07.
 */
public class Main {

    public static void main(String[] args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI("RadioInfo");
            }
        });



        /*
        DataReader dr = new DataReader();
        try {
            dr.getDataListFromUri(DATA_URL);
        } catch (Exceptions.DataDoesNotMatchModelException e) {
            e.printStackTrace();
        } catch (Exceptions.InvalidUrlException e) {
            e.printStackTrace();
        }
        */

    }
}
