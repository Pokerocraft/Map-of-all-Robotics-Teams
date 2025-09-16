package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.AbstractPainter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Path2D;
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
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
        if (polygonPoints.isEmpty()) {
            return;
        }

        Path2D polygonPath = new Path2D.Double();
        boolean first = true;
        for (GeoPosition gp: polygonPoints) {
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
            if (first) {
                polygonPath.moveTo(pt.getX(), pt.getY());
                first = false;
            } else  {
                polygonPath.lineTo(pt.getX(), pt.getY());
            }
        }
        polygonPath.closePath();

        g.setColor(fillColor);
        g.fill(polygonPath);

        g.setStroke(new BasicStroke(2));
        g.setColor(strokeColor);
        g.draw(polygonPath);
    }
}
