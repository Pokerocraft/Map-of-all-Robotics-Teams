package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * An extension of {@link WaypointPainter} to render {@link ButtonWaypoint buttons that act as waypoints}
 */
public class ButtonWaypointRendererAlt extends WaypointPainter<ButtonWaypointAlt> {
    private final JXMapViewer map;

    /**
     * Constructor for {@link ButtonWaypointRendererAlt}
     * @param map Pretty Much a thing for the map so that the buttons render correctly on screen
     */
    public ButtonWaypointRendererAlt(JXMapViewer map) {
        this.map = map;
    }

    /**
     * Paints out all the buttons so that they show up on the map with their text
     * @param g Some kind of {@link Graphics2D}, I believe it's used to make the buttons render
     * @param map The map, that's usually called from the constructor
     * @param w Width
     * @param h Height
     */
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int w, int h){
        Rectangle viewportBounds = map.getViewportBounds();

        for (Waypoint waypoint: getWaypoints()){
            ButtonWaypointAlt buttonWaypoint = (ButtonWaypointAlt) waypoint;
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
