package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CityHighlighter implements Painter<JXMapViewer> {
    private Color color = Color.RED;
    private boolean antiAlias = true;

    private java.util.List<GeoPosition> track;

    public CityHighlighter(List<GeoPosition> track){
        this.track = new ArrayList<GeoPosition>(track);
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
        g = (Graphics2D) g.create();
        Rectangle rect = map.getViewportBounds();
    }
}
