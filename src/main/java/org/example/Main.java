package org.example;


import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;


public class Main{
    public static void main(String[] args){
        JXMapViewer mapViewer = new JXMapViewer();

        JFrame frame = new JFrame("Map Viewer");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        GeoPosition wtc = new  GeoPosition(42.599467,-84.446544);
        GeoPosition hhsnc = new  GeoPosition(42.641525,-84.573810);

        List<GeoPosition> track = Arrays.asList(wtc, hhsnc);
        CityHighlighter highlighter = new CityHighlighter(track);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

        Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
                new DefaultWaypoint(wtc),
                new DefaultWaypoint(hhsnc)
        ));

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        List<org.jxmapviewer.painter.Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(highlighter);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
    }
}