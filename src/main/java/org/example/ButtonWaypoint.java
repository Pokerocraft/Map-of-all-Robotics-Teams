package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Pretty much a Waypoint that also acts as a button to go to FRC teams, YAY!
 */
public class ButtonWaypoint extends DefaultWaypoint {
    private final JButton button;
    URI uri;
    Desktop desktop = Desktop.getDesktop();

    /**
     * Constructor for the button
     * @param text The name of the FRC Team
     * @param coord Pretty much the coordinates given in {@link Main} to act as central points
     * @param teamNumber The team number, it's used in the URI (Uniform Resource Identifier) to open up your browser to go to that team.
     * @throws URISyntaxException
     */
    public ButtonWaypoint(String text, GeoPosition coord, int teamNumber) throws URISyntaxException {
        super(coord);
        this.button = new JButton(text);
        this.uri = new URI("https://www.thebluealliance.com/team/" + teamNumber);

        String path1 = "src/main/resources/icons/" + teamNumber + ".png";
        String path2 = "src/main/resources/icons/frc" + teamNumber + ".png";
        File file1 = new File(path1);
        File file2 = new File(path2);

        if (file1.exists()) {
            this.button.setIcon(new ImageIcon(path1));
        }else if (file2.exists()) {
            this.button.setIcon(new ImageIcon(path2));
        }  else {
            this.button.setIcon(new ImageIcon("src/main/resources/icons/default.png"));
        }
        this.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.button.setOpaque(false);
        this.button.setContentAreaFilled(false);
        this.button.setBorderPainted(false);
        this.button.setHorizontalTextPosition(SwingConstants.CENTER);
        this.button.setVerticalTextPosition(SwingConstants.BOTTOM);

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

    public void setVisible(boolean aFlag){
        this.setVisible(aFlag);
    }
}
