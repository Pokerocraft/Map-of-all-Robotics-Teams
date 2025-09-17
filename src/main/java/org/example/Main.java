package org.example;


//Yeah, for whatever reason, jxmapviewer2 doesn't have any documentation in code, so you'll have to likely check
// https://javadoc.io/doc/org.jxmapviewer/jxmapviewer2/latest/index.html
import org.jxmapviewer.*;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                new GeoPosition(42.618399, -84.523146),
                new GeoPosition(42.618476, -84.542689),
                new GeoPosition(42.654777, -84.543040),
                new GeoPosition(42.654653, -84.512546),
                new GeoPosition(42.644223, -84.506112),
                new GeoPosition(42.644444, -84.506061),
                new GeoPosition(42.643937, -84.505734),
                new GeoPosition(42.643874, -84.505827),
                new GeoPosition(42.640210, -84.503708),
                new GeoPosition(42.640204, - 84.504653),
                new GeoPosition(42.640003, -84.504620),
                new GeoPosition(42.639845, -84.504417),
                new GeoPosition(42.639715, -84.504071),
                new GeoPosition(42.638750, -84.503247),
                new GeoPosition(42.638623, -84.503067),
                new GeoPosition(42.638225, -84.502931),
                new GeoPosition(42.637933, -84.502971),
                new GeoPosition(42.637783, -84.503153),
                new GeoPosition(42.637584, -84.503084),
                new GeoPosition(42.637256, -84.502660),
                new GeoPosition(42.636492, -84.501989),
                new GeoPosition(42.635683, -84.501708),
                new GeoPosition(42.634864, -84.501654),
                new GeoPosition(42.634146, -84.502290),
                new GeoPosition(42.633629, -84.502912),
                new GeoPosition(42.633432, -84.503365),
                new GeoPosition(42.633284, -84.503521),
                new GeoPosition(42.633323, -84.503623),
                new GeoPosition(42.633349, -84.504154),
                new GeoPosition(42.633122, -84.504465),
                new GeoPosition(42.633059, -84.504653),
                new GeoPosition(42.632652, -84.504835),
                new GeoPosition(42.632301, -84.504854),
                new GeoPosition(42.631881, -84.504677),
                new GeoPosition(42.631543, -84.504564),
                new GeoPosition(42.631125, -84.504341),
                new GeoPosition(42.630699, -84.504277),
                new GeoPosition(42.629996, -84.504309),
                new GeoPosition(42.629641, -84.504277),
                new GeoPosition(42.628988, -84.503845),
                new GeoPosition(42.628974, -84.503843),
                new GeoPosition(42.628745, -84.503797),
                new GeoPosition(42.626704, -84.505430),
                new GeoPosition(42.626207, -84.506061),
                new GeoPosition(42.625386, -84.508778),
                new GeoPosition(42.625029, -84.509518),
                new GeoPosition(42.624934, -84.510194),
                new GeoPosition(42.624753, -84.510712),
                new GeoPosition(42.624607, -84.511506),
                new GeoPosition(42.624437, -84.512037),
                new GeoPosition(42.624384, -84.512415),
                new GeoPosition(42.624374, -84.512482),
                new GeoPosition(42.624297, -84.512726),
                new GeoPosition(42.624245, -84.513252),
                new GeoPosition(42.624382, -84.514397),
                new GeoPosition(42.624530, -84.516585),
                new GeoPosition(42.624753, -84.518141),
                new GeoPosition(42.624774, -84.518485),
                new GeoPosition(42.624855, -84.519021),
                new GeoPosition(42.624999, -84.519579),
                new GeoPosition(42.625055, -84.520503),
                new GeoPosition(42.625013, -84.521352),
                new GeoPosition(42.625080, -84.523203),
                new GeoPosition(42.618399, -84.523146)
        );



        PolygonalPainter polygonalPainter = new PolygonalPainter(
                holtPolygonPoints, new Color(132, 94, 57, 255), new Color(132, 94, 58, 150));

        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<JXMapViewer>();
        compoundPainter.setPainters(polygonalPainter);



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


        mapViewer.setOverlayPainter(compoundPainter);
        GeoPosition wilsonTalentCenter = new GeoPosition(42.640123, -84.523664);
        //Setting the Zoom and address to WTC
        mapViewer.setZoom(5);
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