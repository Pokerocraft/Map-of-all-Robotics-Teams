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
     * @param filename Uhh, a thing that was meant to actually put an image onto the
     * @throws URISyntaxException
     */
    public ButtonWaypointAlt(String text, GeoPosition coord, URI link, String filename) throws URISyntaxException {
        super(coord);
        ImageIcon oldIcon = new ImageIcon(filename);
        Image scaling = oldIcon.getImage().getScaledInstance(178, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaling);
        this.button = new JButton(scaledIcon);
        this.button.setBackground(new Color(194, 178,128));
        this.button.setForeground(new Color(255, 223, 0));
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
     * A thing that is only used in {@link ButtonWaypointRendererAlt}
     * @return Returns the button inside this class
     */
    public JButton getButton() {
        return button;
    }
}
