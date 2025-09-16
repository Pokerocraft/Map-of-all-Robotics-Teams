package org.example;


//Yeah, for whatever reason, jxmapviewer2 doesn't have any documentation in code, so you'll have to likely check
// https://javadoc.io/doc/org.jxmapviewer/jxmapviewer2/latest/index.html
import org.jxmapviewer.*;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The primary map viewer, called Main due to this being the main class
 */
@SuppressWarnings("ALL")
public class Main {
    public static void main(String[] args) {
        JXMapViewer mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);


        List<GeoPosition> holtPolygonPoints = Arrays.asList(
                new GeoPosition(42.618476, -84.542689),
                new GeoPosition(42.618399, -84.523146),
                new GeoPosition(42.654777, -84.543040),
                new GeoPosition(42.654653, -84.512546)
        );

        PolygonalPainter polygonalPainter = new PolygonalPainter(
                holtPolygonPoints,
                new Color(132, 94, 57, )
        )

        //Options for the map
        String[] choices = {"Open Street Map","Virtual Earth","Hybrid Between Virtual Earth and Satellite","Satellite",};
        JComboBox comboBox = new JComboBox(choices);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TileFactoryInfo info;
                int index = comboBox.getSelectedIndex();
                if (index == 0) {
                    info = new  OSMTileFactoryInfo();
                } else if (index == 1) {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                } else if (index == 2) {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                } else {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                }
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                mapViewer.setTileFactory(tileFactory);
                tileFactory.setThreadPoolSize(8);
            }
        });

        GeoPosition wilsonTalentCenter = new GeoPosition(0, 0);
        //Setting the Zoom and address to WTC
        mapViewer.setZoom(15);
        mapViewer.setAddressLocation(wilsonTalentCenter);
        //Frame
        JFrame frame = new JFrame("A Map");
        frame.getContentPane().add(mapViewer, BorderLayout.CENTER);
        frame.getContentPane().add(comboBox, BorderLayout.NORTH);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Mouse Input for making the map work
        MouseInputListener mouseListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mouseListener);
        mapViewer.addMouseMotionListener(mouseListener);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
    }
}

/*import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.wms.WMSTileFactoryInfo;
import javax.swing.JFrame;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class JXMapViewerPolygonExample {
    public static void main(String[] args) {
        // Create the map viewer
        JXMapViewer mapViewer = new JXMapViewer();

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new WMSTileFactoryInfo(
                "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
                null,
                "OpenStreetMap",
                "1.1.1");
        mapViewer.setTileFactory(new DefaultTileFactory(info));

        // Define the vertices of your polygon using GeoPosition
        List<GeoPosition> polygonPoints = Arrays.asList(
            new GeoPosition(48.8584, 2.2945), // Eiffel Tower
            new GeoPosition(48.8606, 2.3376), // The Louvre
            new GeoPosition(48.8738, 2.2950), // Arc de Triomphe
            new GeoPosition(48.8500, 2.3800)  // A point near Père Lachaise
        );

        // Create the custom polygon painter
        PolygonPainter polygonPainter = new PolygonPainter(
            polygonPoints,
            new Color(150, 200, 255, 100), // Semi-transparent blue fill
            new Color(50, 100, 200, 255)  // Opaque blue border
        );

        // Create a compound painter and add the polygon painter
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>();
        compoundPainter.setPainters(polygonPainter);

        // Set the compound painter to the map viewer
        mapViewer.setOverlayPainter(compoundPainter);

        // Set the map center and zoom level to display the polygon
        mapViewer.setCenterPosition(new GeoPosition(48.865, 2.33));
        mapViewer.setZoom(5);

        // Set up the Swing frame
        JFrame frame = new JFrame("JXMapViewer2 Polygon Example");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}*/