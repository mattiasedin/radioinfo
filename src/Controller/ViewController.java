package Controller;

import Endpoint.*;
import Models.Channel;
import Models.Program;
import Models.Scheduledepisode;
import Views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.prefs.Preferences;

/**
 * Created by mattias on 2016-01-08.
 * <p>
 * Controller for the user interface. Handles user interaction and input and changes its content depending on the current
 * state. This class also handles the api calls by running background processes and updating accordingly.
 */
public class ViewController {

    private final StatusBar statusPanel = new StatusBar();
    private Timer timer;
    private final SplitView contentManager = new SplitView(3);
    private final Preferences pref = Preferences.userNodeForPackage(ViewController.class);

    private static final String NUM_UPDATE_INTERVAL_MINUTES_KEY = "update_interval";
    private static final int UPDATE_INTERVAL_MINUTES_DEFAULT = 5;


    /**
     * Action called when program download is complete.
     */
    private final ActionListener onProgramDownloaded = actionEvent -> {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Object source = actionEvent.getSource();
            if (source instanceof Exception) {
                handleError((Exception) actionEvent.getSource(), 3);
            } else {
                statusPanel.setText("Program fethced.");
                contentManager.changeViewTo(2, new ProgramInfoView((Program) source));
            }
        });
    };

    /**
     * Action called when schedule is clicked on by user.
     */
    private final ActionListener onScheduleClicked = actionEvent -> {
        statusPanel.setText("Fetching schedules for channel...");
        Scheduledepisode source = (Scheduledepisode) actionEvent.getSource();
        contentManager.changeViewTo(2, new LoadingView());
        Program p = source.getProgram();
        new GetDataBackgroundWorker<Program>(onProgramDownloaded, String.format(EndpointAPI.PROGRAM, p.getId()), Program.class).execute();
    };

    /**
     * Action called when schedules download is complete.
     */
    private final ActionListener onScheduleDownloadComplete = actionEvent -> {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Object source = actionEvent.getSource();
            if (source instanceof Exception) {
                handleError((Exception) source, 1);
            } else {
                statusPanel.setText("Schedules fetched.");
                ArrayList<Scheduledepisode> data = (ArrayList<Scheduledepisode>) source;
                TableScheduleView view = new TableScheduleView(data);
                view.setOnListItemClickListener(onScheduleClicked);
                contentManager.changeViewTo(1, view);
                view.scrollToCurrent();
            }
        });
    };

    /**
     * Action called when user clicks on a Channel.
     */
    private final ActionListener onChannelClicked = actionEvent -> {
        statusPanel.setText("Fetching schedules for channel...");
        contentManager.changeViewTo(1, new LoadingView());
        Channel c = (Channel) actionEvent.getSource();
        new GetSchedulesBackgroundWorker(onScheduleDownloadComplete, c.getId()).execute();
    };


    /**
     * Action called when Channels download is complete.
     */
    private final ActionListener onChannelDownloadComplete = actionEvent -> {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Object source = actionEvent.getSource();
            if (source instanceof Exception) {
                handleError((Exception) source, 0);
            } else {
                statusPanel.setText("Channels fetched.");
                TableIconView<Channel> view = new TableIconView<Channel>(Channel.class, (ArrayList<Channel>) source);
                view.setOnListItemClickListener(onChannelClicked);
                contentManager.changeViewTo(0, view);
            }
        });
    };

    /**
     * Action called when settings changed update interval value
     */
    private final ActionListener onSettingsChanged = actionEvent -> {
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
    private final ActionListener onMenuClicked = actionEvent -> {
        int opt = (int) actionEvent.getSource();

        switch (opt) {
            case Views.Menu.OPTIONS.update:
                timer.restart();
                break;
            case Views.Menu.OPTIONS.preferences:
                createPopup("Preferences", new PreferencesView(onSettingsChanged, pref.getInt(NUM_UPDATE_INTERVAL_MINUTES_KEY, 5)));
                break;
            case Views.Menu.OPTIONS.about:
                createPopup("About", new AboutView());
                break;
        }
    };


    /**
     * Constructor for view controller. Handles interaction changes and changing views according to user input.
     * @param frame the frame to draw the views on.
     */
    public ViewController(final JFrame frame) {
        // Create menu
        frame.setJMenuBar(new Views.Menu(onMenuClicked));

        // Create main containing panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Add content manager to center
        mainPanel.add(contentManager, BorderLayout.CENTER);

        // Create bottom bar
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        mainPanel.add(bottomBar, BorderLayout.SOUTH);

        // Add status panel to bottom bar
        bottomBar.add(statusPanel);

        // Add update button to bottom bar
        JButton btnUpdate = new JButton("Update now");
        btnUpdate.addActionListener(e -> timer.restart());
        bottomBar.add(btnUpdate);

        // Start the update timer
        timer = new Timer(pref.getInt(NUM_UPDATE_INTERVAL_MINUTES_KEY, UPDATE_INTERVAL_MINUTES_DEFAULT)*60000, actionEvent -> doUpdateChannels());
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Initiates new download for Channels and sets the view to loading state.
     */
    private void doUpdateChannels () {
        javax.swing.SwingUtilities.invokeLater(() -> {
            statusPanel.setText("Fetching channels...");
            contentManager.changeViewTo(0, new LoadingView());
            contentManager.clear(1);
            contentManager.clear(2);
        });
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

    /**
     * Show error message to user.
     * @param e exception to show.
     */
    private void handleError(Exception e, int viewIndex) {
        statusPanel.setText("Failed to load data: "+e.getMessage());
        contentManager.changeViewTo(viewIndex, new ErrorView(e.getMessage()));
    }
}
