package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.AbstractPainter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class PolygonalPainter extends AbstractPainter<JXMapViewer>{
    private final List<GeoPosition> polygonPoints;
    private final Color fillColor;
    private final Color strokeColor;

    public PolygonalPainter(List<GeoPosition> polygonPoints, Color fillColor, Color strokeColor){
        this.polygonPoints = polygonPoints;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;

        setAntialiasing(true);
        setCacheable(false);
    }

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
        g.setColor(strokeColor);
        g.setStroke(new BasicStroke(4));

        drawRoute(g, map);

        // do the drawing again
        g.setColor(fillColor);
        g.setStroke(new BasicStroke(2));

        drawRoute(g, map);
    }

    private void drawRoute(Graphics2D g, JXMapViewer map)
    {
        int lastX = 0;
        int lastY = 0;

        boolean first = true;

        for (GeoPosition gp : polygonPoints)
        {
            // convert geo-coordinate to world bitmap pixel
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());

            if (first)
            {
                first = false;
            }
            else
            {
                g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
            }

            lastX = (int) pt.getX();
            lastY = (int) pt.getY();
        }
    }
}
