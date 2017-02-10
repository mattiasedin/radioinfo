package Controller;

import Endpoint.*;
import Models.Channel;
import Models.Program;
import Models.Scheduledepisode;
import Views.*;
import Views.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * Created by mattias on 2016-01-08.
 * <p>
 * Controller for the user interface. Handles user interaction and input and changes its content depending on the current
 * state. This class also handles the api calls by running background processes and updating accordingly.
 */
public class ViewController {

    private Timer timer;
    private SplitView contentManager;
    private final Preferences pref = Preferences.userNodeForPackage(ViewController.class);

    private static final String NUM_UPDATE_INTERVAL_MINUTES_KEY = "update_interval";
    private static final int UPDATE_INTERVAL_MINUTES_DEFAULT = 5;


    /**
     * Action called when program download is complete.
     */
    private ActionListener onProgramDownloaded = actionEvent -> {
        Object source = actionEvent.getSource();
        if (source instanceof String) {
            contentManager.changeViewTo(2, new ErrorView((String) source));
        } else {
            contentManager.changeViewTo(2,new ProgramInfoView((Program) actionEvent.getSource()));
        }
    };

    /**
     * Action called when schedule is clicked on by user.
     */
    private ActionListener onScheduleClicked = actionEvent -> {
        Scheduledepisode source = (Scheduledepisode) actionEvent.getSource();
        contentManager.changeViewTo(2, new LoadingView());
        Program p = source.getProgram();
        new GetDataBackgroundWorker<Program>(onProgramDownloaded, String.format(EndpointAPI.PROGRAM, p.getId()), Program.class).execute();
    };

    /**
     * Action called when schedules download is complete.
     */
    private ActionListener onScheduleDownloadComplete = actionEvent -> {
        Object source = actionEvent.getSource();
        if (source instanceof String) {
            contentManager.changeViewTo(1, new ErrorView((String) source));
        } else {
            ArrayList<Scheduledepisode> data = (ArrayList<Scheduledepisode>) actionEvent.getSource();
            TableIconView<Scheduledepisode> view = new TableScheduleView(data);
            view.setOnListItemClickListener(onScheduleClicked);
            contentManager.changeViewTo(1, view);

            Date currentTime = new Date();
            int currentScheduleIndex = 0;
            for (int i = 0; i < data.size(); i++) {
                boolean hasStarted = data.get(i).getStarttimeutc().after(currentTime);
                boolean hasEnded = data.get(i).getEndtimeutc().before(currentTime);
                if (hasStarted && !hasEnded) {
                    currentScheduleIndex = i;
                    break;
                }
            }
            view.scrollTo(currentScheduleIndex);
        }
    };

    /**
     * Action called when user clicks on a Channel.
     */
    private ActionListener onChannelClicked = actionEvent -> {
        contentManager.changeViewTo(1, new LoadingView());
        Channel c = (Channel) actionEvent.getSource();
        new GetSchedulesBackgroundWorker(onScheduleDownloadComplete, c.getId()).execute();
        //new GetDataListBackgroundWorker<Scheduledepisode, ArrayList<Scheduledepisode>>(onScheduleDownloadComplete, Scheduledepisode.class, String.format(EndpointAPI.SCHEDULE, c.getId())).execute();
    };


    /**
     * Action called when Channels download is complete.
     */
    private ActionListener onChannelDownloadComplete = actionEvent -> {
        if (actionEvent.getSource() instanceof String) {
            contentManager.changeViewTo(0, new ErrorView((String) actionEvent.getSource()));
        } else {
            TableIconView<Channel> view = new TableIconView<Channel>(Channel.class, (ArrayList<Channel>) actionEvent.getSource());
            view.setOnListItemClickListener(onChannelClicked);
            contentManager.changeViewTo(0,view);
        }
    };

    /**
     * Action called when settings changed update interval value
     */
    private ActionListener onSettingsChanged = actionEvent -> {
        if (actionEvent.getSource() instanceof Integer) {
            int interval = (int) actionEvent.getSource();
            pref.putInt(NUM_UPDATE_INTERVAL_MINUTES_KEY, interval);
            timer.setDelay(interval*60000);
            timer.restart();
        }

    };

    /**
     * Action called when a menu item is clicked
     */
    private ActionListener onMenuClicked = actionEvent -> {
        int opt = (int) actionEvent.getSource();

        switch (opt) {
            case Menu.OPTIONS.update:
                timer.restart();
                break;
            case Menu.OPTIONS.preferences:
                createPopup("Preferences", new PreferencesView(onSettingsChanged, pref.getInt(NUM_UPDATE_INTERVAL_MINUTES_KEY, 5)));
                break;
            case Menu.OPTIONS.about:
                createPopup("About", new AboutView());
                break;
        }
    };


    /**
     * Constructor for view controller. Handles interaction changes and changing views acording to user input.
     * @param frame the frame to draw the views on.
     */
    public ViewController(final JFrame frame) {

        frame.setJMenuBar(new Menu(onMenuClicked));

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        contentManager = new SplitView(3);
        mainPanel.add(contentManager, BorderLayout.CENTER);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> timer.restart());
        frame.add(btnUpdate, BorderLayout.PAGE_END);


        timer = new Timer(pref.getInt(NUM_UPDATE_INTERVAL_MINUTES_KEY, UPDATE_INTERVAL_MINUTES_DEFAULT)*60000, actionEvent -> doUpdateChannels());
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Initiates new download for Channels and sets the view to loading state.
     */
    private void doUpdateChannels () {
        contentManager.changeViewTo(0, new LoadingView());
        contentManager.clear(1);
        contentManager.clear(2);
        new GetDataListBackgroundWorker<Channel, ArrayList<Channel>>(onChannelDownloadComplete, EndpointAPI.CHANNELS, Channel.class).execute();
    }

    /**
     * Creates a popup window with corresponding view as content.
     * @param title the title for the view
     * @param view the content of the popup
     * @return the created popup frame
     */
    private JFrame createPopup(String title, JPanel view) {
        JFrame frame = new JFrame (title);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible (true);
        return frame;
    }
}
