package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class ButtonWaypointRenderer extends WaypointPainter<ButtonWaypoint> {
    private final JXMapViewer map;

    public ButtonWaypointRenderer(JXMapViewer map) {
        this.map = map;
    }

    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int w, int h){
        Rectangle viewportBounds = map.getViewportBounds();

        for (Waypoint waypoint: getWaypoints()){
            ButtonWaypoint buttonWaypoint = (ButtonWaypoint) waypoint;
            JButton button = buttonWaypoint.getButton();

            Point2D p = map.getTileFactory().geoToPixel(buttonWaypoint.getPosition(), map.getZoom());

            int x = (int) (p.getX() - viewportBounds.getX());
            int  y = (int) (p.getY() - viewportBounds.getY());

            button.setLocation(x - button.getWidth() / 2,y - button.getHeight() / 2);
            button.setSize(button.getPreferredSize());

            if (button.getParent() == null){
                map.add(button);
            }
        }
    }
}
