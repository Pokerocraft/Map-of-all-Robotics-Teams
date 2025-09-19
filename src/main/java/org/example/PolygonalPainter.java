package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.AbstractPainter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A Painting Class used to put in lots of spaces on the map
 */
public class PolygonalPainter extends AbstractPainter<JXMapViewer>{
    private final List<GeoPosition> polygonPoints;
    private final Color fillColor;
    private final Color strokeColor;

    /**
     * Constructor of the painter
     * @param polygonPoints Pretty much a list of points that get painted.
     * @param fillColor Was initially planned to be used to fill out the space in between line strokes. Now just used as pretty much another color for line strokes
     * @param strokeColor Pretty Much the color of line strokes
     */
    public PolygonalPainter(List<GeoPosition> polygonPoints, Color fillColor, Color strokeColor){
        this.polygonPoints = polygonPoints;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;

        setAntialiasing(true);
        setCacheable(false);
    }

    /**
     * Ensures the line painting works as intended
     * @param g A {@link Graphics2D}, used in here for making a rectangle, but also calling {@link #drawRoute(Graphics2D, JXMapViewer)}
     * @param map The map
     * @param width The width of a rectangle, used for uhh, I'm not entirely sure
     * @param height The height of a rectangle, used for uhh, I'm not entirely sure
     */
    @Override
    @SuppressWarnings("ALL")
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
        if (polygonPoints.isEmpty()) {
            return;
        }
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        if (true)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // do the drawing
        g.setColor(strokeColor); // Line Color
        g.setStroke(new BasicStroke(4));

        drawRoute(g, map);

        // do the drawing again
        g.setColor(fillColor);
        g.setStroke(new BasicStroke(2));

        drawRoute(g, map);
    }

    /**
     * The primary thing used to draw the lines seen on the map.
     * @param g A {@link Graphics2D} used for drawing
     * @param map The map
     */
    private void drawRoute(Graphics2D g, JXMapViewer map)
    {
        int lastX = 0;
        int lastY = 0;

        boolean first = true;
        List<GeoPosition> seenPolygonPoints = new ArrayList<GeoPosition>();
        for (GeoPosition gp : polygonPoints)
        {
            // convert geo-coordinate to world bitmap pixel
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());

            if (first)
            {
                first = false;
                seenPolygonPoints.add(gp);
            }
            else
            {
                if (pointInList(seenPolygonPoints, gp)){
                    //System.out.println("true");
                    first = true;
                }
                else {
                  // g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                    seenPolygonPoints.add(gp);
                }
                g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
            }

            lastX = (int) pt.getX();
            lastY = (int) pt.getY();

        }
    }

    /**
     * A Method used so that places like Lansing, Michigan Work as intended when being painted
     * @param seenPolygonPoints Pretty much a list of GeoPositions that are used to ensure that the coordinates
     * @param gp A {@link GeoPosition}
     * @return If a specific GeoPosition is equal to a point within seenPolygonPoints
     */
    public boolean pointInList(List<GeoPosition> seenPolygonPoints, GeoPosition gp){
        for (GeoPosition p : seenPolygonPoints){
            if (p.equals(gp))
                return true;
        }
        return false;
    }
}
