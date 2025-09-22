package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class CustomMouseListener extends PanMouseInputListener {
    TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
    DefaultTileFactory tileFactory = new DefaultTileFactory(info);
    GeoPosition holtMichiganUsa = new  GeoPosition(42.640172, -84.523452);
    GeoPosition masonMichiganUsa = new GeoPosition(42.579717, -84.442281);
    JFrame frame = new JFrame("HiddenLogic");
    final List<GeoPosition> track = Arrays.asList(holtMichiganUsa, masonMichiganUsa);
    public CustomMouseListener(JXMapViewer viewer) {
        super(viewer);
    }

    @Override
    public void mouseClicked(MouseEvent event){
        super.mouseClicked(event);
        Point2D geoPoint_point = null;
        JXMapViewer mapViewer = new   JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        frame.getContentPane().add(mapViewer, BorderLayout.CENTER);
        frame.setSize(800,600);
        //frame.setVisible(true);

//        for (GeoPosition waypoint: track){
//            geoPoint_point = mapViewer.getTileFactory().geoToPixel(waypoint, mapViewer.getZoom());
//        }
//[-84.4627662,42.5965663]
        GeoPosition geoPoint = new GeoPosition( 42.5965,-84.4627);
        geoPoint_point = mapViewer.getTileFactory().geoToPixel(geoPoint, mapViewer.getZoom());
        System.out.println(geoPoint_point);
        System.out.println(mapViewer.getZoom());
        Rectangle rect = mapViewer.getViewportBounds();
        Point converted_gp_pt = new Point((int) geoPoint_point.getX() - rect.x,
                (int) geoPoint_point.getY() - rect.y);
        System.out.println(converted_gp_pt.distance(event.getPoint()));
        if(converted_gp_pt.distance(event.getPoint()) < 100){
            System.out.println(converted_gp_pt.distance(event.getPoint()));
        }

    }

}
