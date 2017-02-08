package Controller;

import Endpoint.EndpointAPI;
import Endpoint.GetDataBackgroundWorker;
import Endpoint.GetDataListBackgroundWorker;
import Endpoint.GetSchedulesBackgroundWorker;
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
 */
public class ViewController {

    private Timer timer;
    private TripleSplitView contentManager;
    private final Preferences pref = Preferences.userNodeForPackage(ViewController.class);

    private static final String NUM_UPDATE_INTERVAL = "update_interval";

    /**
     * Action called when program download is complete.
     */
    private ActionListener onProgramDownloaded = actionEvent -> {
        Object source = actionEvent.getSource();
        if (source instanceof String) {
            contentManager.changeViewTo(2, new ErrorView((String) source));
        } else {
            contentManager.changeViewTo(2,new InfoView((Program) actionEvent.getSource()));
        }
    };

    /**
     * Action called when schedule is clicked on by user.
     */
    private ActionListener onScheduleClicked = actionEvent -> {
        Scheduledepisode source = (Scheduledepisode) actionEvent.getSource();
        contentManager.changeViewTo(2, new LoadingView());
        Program p = source.getProgram();
        new GetDataBackgroundWorker<Program>(onProgramDownloaded, Program.class, String.format(EndpointAPI.PROGRAM, p.getId())).execute();
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
            TableView<Scheduledepisode> view = new TableView<Scheduledepisode>(Scheduledepisode.class, data);
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
            TableView<Channel> view = new TableView<Channel>(Channel.class, (ArrayList<Channel>) actionEvent.getSource());
            view.setOnListItemClickListener(onChannelClicked);
            contentManager.changeViewTo(0,view);
        }
    };


    private ActionListener onSettingsChanged = actionEvent -> {
        if (actionEvent.getSource() instanceof Integer) {
            int interval = (int) actionEvent.getSource();
            pref.putInt(NUM_UPDATE_INTERVAL, interval);
            timer.setDelay(interval*60000);
            timer.restart();
        }

    };

    private ActionListener onMenuClicked = actionEvent -> {
        int opt = (int) actionEvent.getSource();

        switch (opt) {
            case Menu.OPTIONS.update:
                timer.restart();
                break;
            case Menu.OPTIONS.preferences:
                createPopup("Preferences", new PreferencesView(onSettingsChanged, pref.getInt(NUM_UPDATE_INTERVAL, 5)));
                break;
            case Menu.OPTIONS.about:
                createPopup("About", new AboutView());
                break;
        }
    };



    public ViewController(final JFrame frame) {

        frame.setJMenuBar(new Menu(onMenuClicked));

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);


        contentManager = new TripleSplitView();
        mainPanel.add(contentManager, BorderLayout.CENTER);

        //contentManager.changeViewTo(0, new LoadingView());
        //doUpdateChannels();

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.restart();
            }
        });
        frame.add(btnUpdate, BorderLayout.PAGE_END);


        timer = new Timer(pref.getInt(NUM_UPDATE_INTERVAL,5)*60000, actionEvent -> {
            doUpdateChannels();
        });
        timer.setInitialDelay(0);
        timer.start();
    }


    private void doUpdateChannels () {
        contentManager.changeViewTo(0, new LoadingView());
        contentManager.clear(1);
        contentManager.clear(2);
        new GetDataListBackgroundWorker<Channel, ArrayList<Channel>>(onChannelDownloadComplete, Channel.class, EndpointAPI.CHANNELS).execute();
    }

    private JFrame createPopup(String title, JPanel view) {
        JFrame frame = new JFrame (title);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible (true);
        return frame;
    }
}
