package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ButtonWaypoint extends DefaultWaypoint {
    private final JButton button;
    URI uri;
    Desktop desktop = Desktop.getDesktop();
    public ButtonWaypoint(String text, GeoPosition coord, int teamNumber) throws URISyntaxException {
        super(coord);
        this.button = new JButton(text);
        this.uri = new URI("https://www.thebluealliance.com/team/" + teamNumber);

        this.button.addActionListener(e -> {
            try {
                desktop.browse(uri);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public JButton getButton() {
        return button;
    }
}
