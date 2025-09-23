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
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

import com.google.gson.*;

/**
 * The primary map viewer, called Main due to this being the main class
 */
@SuppressWarnings("ALL")
public class Main {
    static JXMapViewer mapViewer = new JXMapViewer();
    static Set<ButtonWaypoint> buttonWaypoints = new HashSet<>();
    static ButtonWaypointRenderer renderer = new ButtonWaypointRenderer(mapViewer);
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        Color deepGreen = new Color(0,105,30);
        URI uri = new URI("https://www.thebluealliance.com/");
        Desktop desktop = Desktop.getDesktop();

        mapViewer.setLayout(null);
        //Centers
        GeoPosition holtMichiganUsa = new  GeoPosition(42.640176,-84.523455);
        GeoPosition masonMichiganUsa = new GeoPosition(42.579713,-84.442283);
        GeoPosition masonMichiganUsa2 = new GeoPosition(42.578682, -84.442278);
        GeoPosition newportMichiganUsa = new GeoPosition(42.0022672, -83.3085419);

        //Multitudes of ButtonWaypoints
        ButtonWaypoint holtMichiganUsaButton = new ButtonWaypoint("RoboRams", holtMichiganUsa, 6078);
        ButtonWaypoint masonMichiganUsaButton = new ButtonWaypoint("Tractor Technicians", masonMichiganUsa, 3655);
        ButtonWaypoint masonMichiganUsaButton2 = new ButtonWaypoint("Tractor Technicians Next Gen", masonMichiganUsa2, 8424);
        ButtonWaypoint newportMichiganUsaButton = new ButtonWaypoint("TEMPEST", newportMichiganUsa, 240);

        //This'll be even longer
        buttonWaypoints = Set.of(holtMichiganUsaButton,  masonMichiganUsaButton,   masonMichiganUsaButton2, newportMichiganUsaButton);

        renderer.setWaypoints(buttonWaypoints);

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
        List<GeoPosition> sterlingHeightsMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/sterlingHeights.geojson"));
        List<GeoPosition> troyMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/troy.geojson"));
        List<GeoPosition> rochesterHillsMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/rochesterHills.geojson"));
        List<GeoPosition> berkleyMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/berkley.geojson"));
        List<GeoPosition> taylorMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/taylor.geojson"));
        List<GeoPosition> lakeOrionMichiganUsaPPoints = parseGeoJsonFile(new File(("src/main/geojsons/USA/Michigan/lakeOrion.geojson")));
        List<GeoPosition> walledLakeMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/walledLake.geojson"));
        List<GeoPosition> flintMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/flint.geojson"));
        List<GeoPosition> clintonTownshipMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/clintonTownship.geojson"));
        List<GeoPosition> noviMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/novi.geojson"));
        List<GeoPosition> northvilleMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/northville.geojson"));
        List<GeoPosition> allenParkMichiganUsaPPoints = parseGeoJsonFile(new File(("src/main/geojsons/USA/Michigan/allenPark.geojson")));
        List<GeoPosition> warrenMichiganUsaPPoints = parseGeoJsonFile(new File("src/main/geojsons/USA/Michigan/warren.geojson"));

        //Copious Amounts of Polygon Painters
        PolygonalPainter holtMichiganUsaPPainter = new PolygonalPainter(holtMichiganUsaPPoints,Color.red, Color.red);
        PolygonalPainter masonMichiganUsaPPainter = new PolygonalPainter(masonMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter lansingMichiganUsaPPainter = new PolygonalPainter(lansingMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter pontiacMichiganUsaPPainter = new PolygonalPainter(pontiacMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter clarkstonMichiganUsaPPainter = new PolygonalPainter(clarkstonMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter bloomfieldHillsMichiganUsaPPainter = new PolygonalPainter(bloomfieldHillsMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter ypsilantiMichiganUsaPPainter = new PolygonalPainter(ypsilantiMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter highlandTownshipMichiganUsaPPainter = new PolygonalPainter(highlandTownshipMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter ortonvilleMichiganUsaPPainter = new PolygonalPainter(ortonvilleMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter goodrichMichiganUsaPPainter = new PolygonalPainter(goodrichMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter hollandMichiganUsaPPainter = new PolygonalPainter(hollandMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter zeelandMichiganUsaPPainter = new PolygonalPainter(zeelandMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter southfieldMichiganUsaPPainter = new PolygonalPainter(southfieldMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter hamtramckMichiganUsaPPainter = new PolygonalPainter(hamtramckMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter rochesterMichiganUsaPPainter = new PolygonalPainter(rochesterMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter grandvilleMichiganUsaPPainter = new PolygonalPainter(grandvilleMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter sterlingHeightsMichiganUsaPPainter = new PolygonalPainter(sterlingHeightsMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter troyMichiganUsaPPainter = new PolygonalPainter(troyMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter rochesterHillsMichiganUsaPPainter = new PolygonalPainter(rochesterHillsMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter berkleyMichiganUsaPPainter = new PolygonalPainter(berkleyMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter taylorMichiganUsaPPainter = new PolygonalPainter(taylorMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter lakeOrionMichiganUsaPPainter = new PolygonalPainter(lakeOrionMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter walledLakeMichiganUsaPPainter = new PolygonalPainter(walledLakeMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter flintMichiganUsaPPainter = new PolygonalPainter(flintMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter clintonTownshipMichiganUsaPPainter = new PolygonalPainter(clintonTownshipMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter noviMichiganUsaPPainter = new PolygonalPainter(noviMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter northvilleMichiganUsaPPainter = new PolygonalPainter(northvilleMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter allenParkMichiganUsaPPainter = new PolygonalPainter(allenParkMichiganUsaPPoints, Color.red, Color.red);
        PolygonalPainter warrenMichiganUsaPPainter = new PolygonalPainter(warrenMichiganUsaPPoints, Color.red, Color.red);


        //Creates a Compound Painter that utilizes JXMapViewer
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<JXMapViewer>();

        //Oh boy, this'll get long very quickly
        compoundPainter.setPainters(holtMichiganUsaPPainter,  masonMichiganUsaPPainter,  lansingMichiganUsaPPainter, pontiacMichiganUsaPPainter,
        clarkstonMichiganUsaPPainter,  bloomfieldHillsMichiganUsaPPainter, ypsilantiMichiganUsaPPainter,  highlandTownshipMichiganUsaPPainter,
        ortonvilleMichiganUsaPPainter, goodrichMichiganUsaPPainter, hollandMichiganUsaPPainter, zeelandMichiganUsaPPainter, southfieldMichiganUsaPPainter,
        hamtramckMichiganUsaPPainter, rochesterMichiganUsaPPainter, grandvilleMichiganUsaPPainter, renderer, sterlingHeightsMichiganUsaPPainter, troyMichiganUsaPPainter,
        rochesterHillsMichiganUsaPPainter, berkleyMichiganUsaPPainter, taylorMichiganUsaPPainter, lakeOrionMichiganUsaPPainter,
        walledLakeMichiganUsaPPainter, flintMichiganUsaPPainter, clintonTownshipMichiganUsaPPainter, noviMichiganUsaPPainter,
        northvilleMichiganUsaPPainter, allenParkMichiganUsaPPainter, warrenMichiganUsaPPainter);

        JTextPane textPane = new JTextPane();
        textPane.setText("It appears that AbstractTileFactory is experiencing some sort of bug, so the default until that is fixed is Virtual Earth instead of Open Street Map");
        textPane.setEditable(false);

        //Options for the map
        String[] choices = {"Virtual Earth","Open Street Map (Bugged)","Hybrid Between Virtual Earth and Satellite","Satellite",};
        JComboBox comboBox = new JComboBox(choices);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TileFactoryInfo info;
                int index = comboBox.getSelectedIndex();
                if (index == 0) {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                } else if (index == 1) {

                    info = new  OSMTileFactoryInfo();
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
        JButton linkedButton = new JButton("Go to The Blue Alliance");
        linkedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    desktop.browse(uri);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
        frame.getContentPane().add(textPane, BorderLayout.SOUTH);
        frame.getContentPane().add(linkedButton, BorderLayout.EAST);
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
            System.exit(-1);
        }
        return list;
    }
}