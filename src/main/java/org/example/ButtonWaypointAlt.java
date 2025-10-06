package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Pretty much a Waypoint that also acts as a button to go to FRC teams, YAY!
 */
public class ButtonWaypointAlt extends DefaultWaypoint {
    private final JButton button;
    URI uri;
    Desktop desktop = Desktop.getDesktop();

    /**
     * Constructor for the button
     * @param text Text for what the event is.
     * @param coord Pretty much the coordinates given in {@link Main} to act as central points
     * @param link Basically what the link is
     * @throws URISyntaxException
     */
    public ButtonWaypointAlt(String text, GeoPosition coord, URI link) throws URISyntaxException {
        super(coord);
        this.button = new JButton(text);
        this.uri = new  URI(link.toString());

        this.button.addActionListener(e -> {
            try {
                desktop.browse(uri);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * A thing that is only used in {@link ButtonWaypointRenderer}
     * @return Returns the button inside this class
     */
    public JButton getButton() {
        return button;
    }
}
