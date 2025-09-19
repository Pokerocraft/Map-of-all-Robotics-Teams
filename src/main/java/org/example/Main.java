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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.*;

/**
 * The primary map viewer, called Main due to this being the main class
 */
@SuppressWarnings("ALL")
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        JXMapViewer mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        Color deepGreen = new Color(0,105,30);


        List<GeoPosition> holtMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/holt.geojson"));
        List<GeoPosition> masonMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/mason.geojson"));
        List<GeoPosition> lansingMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/lansing.geojson"));
        List<GeoPosition> pontiacMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/pontiac.geojson"));
        List<GeoPosition> clarkstonMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/clarkston.geojson"));
        List<GeoPosition> bloomfieldHillsMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/bloomfieldHills.geojson"));
        List<GeoPosition> ypsilantiMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/ypsilanti.geojson"));
        List<GeoPosition> highlandTownshipMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/highlandTownship.geojson"));
        List<GeoPosition> ortonvilleMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/ortonville.geojson"));
        List<GeoPosition> goodrichMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/goodrich.geojson"));
        List<GeoPosition> hollandMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/holland.geojson"));
        List<GeoPosition> zeelandMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/zeeland.geojson"));
        List<GeoPosition> southfieldMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/southfield.geojson"));
        List<GeoPosition> hamtramckMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/hamtramck.geojson"));
        List<GeoPosition> rochesterMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/rochester.geojson"));
        List<GeoPosition> grandvilleMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/grandville.geojson"));




        PolygonalPainter holtMichiganUsaPPainter = new PolygonalPainter(
                holtMichiganUsaPPoints, new Color(132, 94, 57, 255), new Color(132, 94, 58, 150)
        );
        PolygonalPainter masonMichiganUsaPPainter = new PolygonalPainter(
                masonMichiganUsaPPoints, new Color(0,179,2, 255), new Color(0,179,2, 255)
        );
        PolygonalPainter lansingMichiganUsaPPainter = new PolygonalPainter(
                lansingMichiganUsaPPoints, new Color(17, 144, 186, 255), new Color(17, 144, 186, 255)
        );
        PolygonalPainter pontiacMichiganUsaPPainter = new PolygonalPainter(
                pontiacMichiganUsaPPoints, Color.YELLOW, Color.YELLOW
        );
        PolygonalPainter clarkstonMichiganUsaPPainter = new PolygonalPainter(
                clarkstonMichiganUsaPPoints, Color.RED, Color.RED
        );
        PolygonalPainter bloomfieldHillsMichiganUsaPPainter = new PolygonalPainter(
                bloomfieldHillsMichiganUsaPPoints, Color.YELLOW, Color.YELLOW
        );
        PolygonalPainter ypsilantiMichiganUsaPPainter = new PolygonalPainter(
                ypsilantiMichiganUsaPPoints, new Color(0,105,30), new  Color(0,105,30)
        );
        PolygonalPainter highlandTownshipMichiganUsaPPainter = new PolygonalPainter(
                highlandTownshipMichiganUsaPPoints, Color.red, Color.red
        );
        PolygonalPainter ortonvilleMichiganUsaPPainter = new PolygonalPainter(
                ortonvilleMichiganUsaPPoints, Color.orange, Color.orange
        );
        PolygonalPainter goodrichMichiganUsaPPainter = new PolygonalPainter(
                goodrichMichiganUsaPPoints, deepGreen, deepGreen
        );
        PolygonalPainter hollandMichiganUsaPPainter = new PolygonalPainter(
                hollandMichiganUsaPPoints, Color.red, Color.red
        );
        PolygonalPainter zeelandMichiganUsaPPainter = new PolygonalPainter(
                zeelandMichiganUsaPPoints, Color.yellow, Color.yellow
        );
        PolygonalPainter southfieldMichiganUsaPPainter = new PolygonalPainter(
                southfieldMichiganUsaPPoints, Color.cyan, Color.cyan
        );
        PolygonalPainter hamtramckMichiganUsaPPainter = new PolygonalPainter(
                hamtramckMichiganUsaPPoints, Color.gray, Color.gray
        );
        PolygonalPainter rochesterMichiganUsaPPainter = new PolygonalPainter(
                rochesterMichiganUsaPPoints, Color.red, Color.red
        );
        PolygonalPainter grandvilleMichiganUsaPPainter = new PolygonalPainter(
                grandvilleMichiganUsaPPoints, Color.red, Color.red
        );

        //Creates a Compound Painter that utilizes JXMapViewer
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<JXMapViewer>();

        //Oh boy, this'll get long very quickly
        compoundPainter.setPainters(holtMichiganUsaPPainter,  masonMichiganUsaPPainter,  lansingMichiganUsaPPainter, pontiacMichiganUsaPPainter,
        clarkstonMichiganUsaPPainter,  bloomfieldHillsMichiganUsaPPainter, ypsilantiMichiganUsaPPainter,  highlandTownshipMichiganUsaPPainter,
        ortonvilleMichiganUsaPPainter, goodrichMichiganUsaPPainter, hollandMichiganUsaPPainter, zeelandMichiganUsaPPainter, southfieldMichiganUsaPPainter,
        hamtramckMichiganUsaPPainter, rochesterMichiganUsaPPainter, grandvilleMichiganUsaPPainter);



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

    /**
     * Parses the GeoJSON files so that the map highlights the location, make sure to make these [[[[ ]]]] into these [[ ]]
     * @param file the location of the file, primarily src/main/geojsons/cityState.geojson
     * @throws FileNotFoundException In the event there is no file there
     */
    static List<GeoPosition> parseGeoJsonFile(File file) throws FileNotFoundException {
        ArrayList<GeoPosition> list = new ArrayList<GeoPosition>();
        Scanner scanner = new Scanner(file);
        String geoJsonString = scanner.nextLine();
        Gson gson = new Gson();
        try {
            GeoJSON geoJSON = gson.fromJson(geoJsonString, GeoJSON.class);
            if (geoJSON != null) {
                List<List<Double>> coordinates = geoJSON.getCoordinates();
                //System.out.println("Coordinates");
                for (List<Double> coordinate : coordinates) {
                    //for (Double place:  coordinate) {
                    list.add(new GeoPosition(coordinate.get(1), coordinate.get(0)));
                    //System.out.println("Longitude: " + coordinate.get(0) + " Latitude: " + coordinate.get(1));
                    // }
                }
            }
        } catch (Exception e) {
            System.err.println("Welp, you likely forgot that the code has to have 2 of these [] not 4 at the beginning and end");
        }
        return list;
    }
}